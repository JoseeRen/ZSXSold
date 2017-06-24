package com.ryw.zsxs.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

    @BindView(R.id.pull_xuankedetail_listivew)
    PullToRefreshListView pullXuankedetailListivew;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    private int types;
    private String tid;
    //左边的当前值
    private int left_position = 0;
    //右边的 当前值默认右侧对话框的初始值
    private int right_position = 0;
    private LvAdapter lvAdapter;
    private int pageNow = 1;
    private int pageCount = 1;
    CourseListBean courseListBean = null;
    private KCTypes kcTypes;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_xuankedetail;
    }


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
        kcTypes = new Gson().fromJson(s, KCTypes.class);
        tid = bundle.getString("tid");
        Log.e(TAG + "xuankeDetail", kcTypes.getKc_types());
        initLisview();


        initData();
    }

    /**
     * 初始化左边弹出对话框
     *
     * @param kcTypes
     */
    private void showLeftPopupwindow(KCTypes kcTypes) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popupwindow_layout, null);

        RecyclerView rvXuankedetail = view.findViewById(R.id.rv_xuankedetail);


        //设置卡片布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        //设备layoutManager
        rvXuankedetail.setLayoutManager(mLayoutManager);
        //据说提高性能
        rvXuankedetail.setHasFixedSize(true);
        rvXuankedetail.setAdapter(new RVAdapter(kcTypes.getT_list()));
       showPopupwindow(view);
    }

    private void showPopupwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);


        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        popupWindow.showAsDropDown(rgXuankedetailTop, 0, 0);
        //实现ppopuwindow的消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv1.setImageResource(R.drawable.buttom_smart);
                iv2.setImageResource(R.drawable.buttom_smart);
            }
        });

    }

    /**
     * 显示右边对话框
     */
    private void showRightPopupwindow() {
        String[] right_String = {"综合", "热门", "最新", "评分", "价格递减", "价格递增"};
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popupwindow_right_layout, null);

        ListView lv = view.findViewById(R.id.rv_xuankedetail);

        lv.setAdapter(new MyArrayAdapter(mContext, R.layout.item_normal_xuankedetail_listview, right_String));
        showPopupwindow(view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class MyArrayAdapter extends ArrayAdapter {

        private final String[] datas;

        public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Object[] objects) {
            super(context, resource, objects);
            this.datas = (String[]) objects;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (position == right_position) {
                convertView = View.inflate(mContext, R.layout.item_selected_xuankedetail_listview, null);

            } else {
                convertView = View.inflate(mContext, R.layout.item_normal_xuankedetail_listview, null);

            }
            TextView tv = convertView.findViewById(R.id.tv_item_xuankedetail_lv);
            tv.setText(datas[position]);

            return tv;
        }
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
            ViewHolder viewHolder = null;
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
                showLeftPopupwindow(kcTypes);

                break;
            case R.id.rb_xuankedetail_top_right:
                showRightPopupwindow();
                break;
            case R.id.rg_xuankedetail_top:
                break;
        }
    }


}
