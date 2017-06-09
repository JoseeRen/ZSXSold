/*
 * Create on 2017-6-8 下午2:18
 * FileName: MainActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.fragment.Home_Fragment;
import com.ryw.zsxs.fragment.MyClass_Fragment;
import com.ryw.zsxs.fragment.User_Fragment;
import com.ryw.zsxs.fragment.XuanKe_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @BindView(R.id.foot_bar_home)
    RadioButton rb_footBarHome;
    @BindView(R.id.foot_bar_xuanke)
    RadioButton rb_footBarXuanke;
    @BindView(R.id.foot_bar_myclass)
    RadioButton rb_footBarMyclass;
    @BindView(R.id.foot_bar_user)
    RadioButton rb_footBarUser;
    @BindView(R.id.group)
    RadioGroup rg_group;
    @BindView(R.id.layoutFooter)
    RelativeLayout layoutFooter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        rg_group.check(R.id.foot_bar_home);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_main,Home_Fragment.getInstance());
        ft.commit();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        rg_group.setOnCheckedChangeListener(new RadioGroupCheckedChangeListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class RadioGroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (checkedId) {
                case R.id.foot_bar_home:
                    ft.replace(R.id.rl_main, Home_Fragment.getInstance());
                    break;
                case R.id.foot_bar_xuanke:
                    ft.replace(R.id.rl_main, XuanKe_Fragment.getInstance());

                    break;
                case R.id.foot_bar_myclass:
                    ft.replace(R.id.rl_main, MyClass_Fragment.getInstance());


                    break;
                case R.id.foot_bar_user:
                    ft.replace(R.id.rl_main, User_Fragment.getInstance());


                    break;
            }
            ft.commit();
        }
    }
}
