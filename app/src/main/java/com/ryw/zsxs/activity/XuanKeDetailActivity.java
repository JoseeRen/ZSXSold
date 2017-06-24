package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.bean.KCTypes;
import com.ryw.zsxs.utils.XutilsHttp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr_Shadow on 2017/6/21.
 * <p>
 * 选课分类详情
 * 此 tid  types需要传过来
 * http://api.chinaplat.com/getval_2017?Action=GetCourseList&types=0&tid=820
 */

public class XuanKeDetailActivity extends BaseActivity {

    @BindView(R.id.ib_xuankedetail_back)
    ImageButton ibXuankedetailBack;
    @BindView(R.id.tv_xuankedetail_title)
    TextView tvXuankedetailTitle;
    @BindView(R.id.ib_xuankedetail_search)
    ImageButton ibXuankedetailSearch;

    @BindView(R.id.rb_xuankedetail_top_left)
    RadioButton rbXuankedetailTopLeft;
    @BindView(R.id.rb_xuankedetail_top_right)
    RadioButton rbXuankedetailTopRight;
    @BindView(R.id.rg_xuankedetail_top)
    RadioGroup rgXuankedetailTop;
    @BindView(R.id.rv_xuankedetail)
    RecyclerView rvXuankedetail;
    @BindView(R.id.pull_xuankedetail_listivew)
    PullToRefreshListView pullXuankedetailListivew;
    private int types;
    private String tid;
    //左边的当前值
    private int left_position = 0;
    //右边的 当前值
    private int right_position = 0;
    private LvAdapter lvAdapter;
    private int pageNow = 1;
    private int pageCount = 1;
    CourseListBean courseListBean = null;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_xuankedetail;
    }

    private String[] right_String = {"综合", "热门", "最新", "评分", "价格递减", "价格递增"};

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        types = bundle.getInt("types", 0);
        switch (types) {
            case 0:
                tvXuankedetailTitle.setText("视频中心");

                break;

            case 1:
                tvXuankedetailTitle.setText("音频中心");

                break;
            case 2:
                tvXuankedetailTitle.setText("读书中心");

                break;
            case 3:
                tvXuankedetailTitle.setText("文章中心");

                break;
        }
        String s = bundle.getString("courseList");
        KCTypes kcTypes = new Gson().fromJson(s, KCTypes.class);
        tid = bundle.getString("tid");
        Log.e(TAG + "xuankeDetail", kcTypes.getKc_types());
        initLisview();


        //初始化弹出菜单
        //设置卡片布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        //设备layoutManager
        rvXuankedetail.setLayoutManager(mLayoutManager);
        //据说提高性能
        rvXuankedetail.setHasFixedSize(true);
        rvXuankedetail.setAdapter(new RVAdapter(kcTypes.getT_list()));

        initData();
    }

    //初始化列表
    private void initLisview() {
        initText();
        pullXuankedetailListivew.setMode(PullToRefreshBase.Mode.BOTH);
        // pullXuankedetailListivew.setRefreshing();
        pullXuankedetailListivew.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullXuankedetailListivew.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e(TAG, "onPullUpToRefresh: ");
                if (pageNow == pageCount) {
                    Toast.makeText(mContext, "没有更多了", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullXuankedetailListivew.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }
                initData();
            }
        });


    }

    private void initText() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = pullXuankedetailListivew
                .getLoadingLayoutProxy(true, false);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String str = format.format(new Date());
        pullXuankedetailListivew.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载中...");// 刷新时
        startLabels.setReleaseLabel("松手可刷新...");// 下来达到一定距离时，显示的提示
//     设置上拉刷新文本
        ILoadingLayout endLabels = pullXuankedetailListivew.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载中...");// 刷新时
        endLabels.setReleaseLabel("松手可刷新");// 下来达到一定距离时，显示的提示
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Action", Constant.ACTION_GETCOURSELIST);
        map.put("types", types + "");
        map.put("tid", tid);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                Log.e(TAG + "initData", result);
                //设置listview的数据源

                if (lvAdapter == null) {

                    courseListBean = gson.fromJson(result, CourseListBean.class);
                    pageCount = Integer.parseInt(courseListBean.getPage_all());
                    lvAdapter = new LvAdapter(courseListBean);
                    pullXuankedetailListivew.setAdapter(lvAdapter);

                } else {
                    pageNow++;
                    courseListBean.getCourse().addAll(gson.fromJson(result, CourseListBean.class).getCourse());
                    lvAdapter.notifyDataSetChanged();
                    pullXuankedetailListivew.onRefreshComplete();

                }


            }
        });
    }

    class LvAdapter extends BaseAdapter {

        private final CourseListBean courseListBean;

        public LvAdapter(CourseListBean courseListBean) {
            this.courseListBean = courseListBean;
        }

        @Override
        public int getCount() {
            return courseListBean.getCourse().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            LvAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_xuankedetail_pvlistview, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            XutilsHttp.getInstance().bindCommonImage(viewHolder.ivItemXuankedetailPv, courseListBean.getCourse().get(i).getImg(), true);
            viewHolder.tvTitleItemXuankedetailPv.setText(courseListBean.getCourse().get(i).getTitle());
            viewHolder.tvKeshiItemXuankedetailPv.setText(courseListBean.getCourse().get(i).getKeshi() + "课时");
            viewHolder.tvJifenItemXuankedetailPv.setText(courseListBean.getCourse().get(i).getMoney() + "积分");
            viewHolder.tvDianjiliangItemXuankedetailPv.setText(courseListBean.getCourse().get(i).getHot() + "");


            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_item_xuankedetail_pv)
            ImageView ivItemXuankedetailPv;
            @BindView(R.id.tv_title_item_xuankedetail_pv)
            TextView tvTitleItemXuankedetailPv;
            @BindView(R.id.tv_keshi_item_xuankedetail_pv)
            TextView tvKeshiItemXuankedetailPv;
            @BindView(R.id.tv_jifen_item_xuankedetail_pv)
            TextView tvJifenItemXuankedetailPv;
            @BindView(R.id.tv_dianjiliang_item_xuankedetail_pv)
            TextView tvDianjiliangItemXuankedetailPv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * recylerview的适配器
     *
     * @param
     */
    class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

        private final List<KCTypes.TListBean> t_list;

        public RVAdapter(List<KCTypes.TListBean> t_list) {
            this.t_list = t_list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("XuankeDetailActivity", viewType + ">>>>");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);

            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mButton.setText(t_list.get(position).getName());
        }


        @Override
        public int getItemCount() {
            return t_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public Button mButton;

            public ViewHolder(View itemView) {
                super(itemView);
                mButton = itemView.findViewById(R.id.btn_item);
            }
        }
    }

    @OnClick({R.id.ib_xuankedetail_back, R.id.ib_xuankedetail_search, R.id.rb_xuankedetail_top_left, R.id.rb_xuankedetail_top_right, R.id.rg_xuankedetail_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_xuankedetail_back:
                finish();
                break;
            case R.id.ib_xuankedetail_search:
                Toast.makeText(mContext, "跳转到搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_xuankedetail_top_left:
                break;
            case R.id.rb_xuankedetail_top_right:
                break;
            case R.id.rg_xuankedetail_top:
                break;
        }
    }
}
