/*
 * Create on 2017-6-8 下午5:49
 * FileName: SplashActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SharedPreferences;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tiansj on 15/7/29.
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_ad)
    ImageView ivAd;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initADPic();
        final boolean first_boot = SharedPreferences.getInstance().getBoolean("first_boot", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (first_boot) {
                    //第一次进入  到引导页面
                    startActivity(GuideActivity.class, null);

                } else {
                    startActivity(MainActivity.class, null);
                }
                finish();
            }
        }, 2000);

    }

    /**
     * 初始化splash页面的ad图片
     */
    private void initADPic() {
        String adPath= this.getFilesDir().getPath()+"ad.jpg";
        File file = new File(adPath);
        if (file.exists()){

        }else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
