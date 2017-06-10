/*
 * Create on 2017-6-9 上午10:34
 * FileName: User_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/9.
 * 个人中心
 *
 * 注意有两个 方法 不要复写
 */

public class User_Fragment extends BaseFragment {


    public static User_Fragment instance = null;
    @BindView(R.id.layoutFooter)
    RelativeLayout layoutFooter;
    Unbinder unbinder;

    public static User_Fragment getInstance() {
        if (instance == null) {
            instance = new User_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user;
    }




}
