package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.KCTypes;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
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
    private int types;
    private String tid;
    //左边的当前值
    private  int left_position=0;
    //右边的 当前值
    private int right_position=0;
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
        KCTypes kcTypes = new Gson().fromJson(s, KCTypes.class);
        tid = bundle.getString("tid");
        Log.e(TAG + "xuankeDetail", kcTypes.getKc_types());


        //初始化弹出菜单
        //设置卡片布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        //设备layoutManager
        rvXuankedetail.setLayoutManager(mLayoutManager);
        //据说提高性
        rvXuankedetail.setHasFixedSize(true);
        rvXuankedetail.setAdapter(new RVAdapter(kcTypes.getT_list()));

        initData();
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
                KCTypes kcType = gson.fromJson(result, KCTypes.class);
                Log.e(TAG + "initData", result);

            }
        });
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
         Log.e("XuankeDetailActivity",viewType+">>>>");
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
                break;
            case R.id.ib_xuankedetail_search:
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
