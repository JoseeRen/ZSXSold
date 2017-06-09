/*
 * Create on 2017-6-9 上午10:32
 * FileName: Home_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseFragment;

/**
 * Created by Mr_Shadow on 2017/6/9.
 *
 * 首页的Fragment
 */

public class Home_Fragment extends BaseFragment {


    public static Home_Fragment instance = null;

    public static Home_Fragment getInstance() {
        if (instance == null) {
            instance = new Home_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }
}
