package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.KCTypes;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr_Shadow on 2017/6/21.
 * <p>
 * 选课分类详情
 * 此 tid  types需要传过来
 * http://api.chinaplat.com/getval_2017?Action=GetCourseList&types=0&tid=820
 */

public class XuanKeDetailActivity extends BaseActivity {

    private String types;
    private String tid;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_xuankedetail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        types = bundle.getString("types");
        String s = bundle.getString("courseList");

        KCTypes kcTypes = new Gson().fromJson(s, KCTypes.class);
        tid = bundle.getString("tid");
        Log.e(TAG+"xuankeDetail",kcTypes.getKc_types());
        initData();
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Action", Constant.ACTION_GETCOURSELIST);
        map.put("types", types);
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
}
