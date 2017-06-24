/*
 * Create on 2017-6-9 上午10:32
 * FileName: Home_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.SlideBean;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yuzhenwei on 2017/6/9.
 * <p>
 * 首页的Fragment
 */

public class Home_Fragment extends BaseFragment {


    public static Home_Fragment instance = null;
    @BindView(R.id.homefragment_vp)
    ViewPager homefragmentVp;
    Unbinder unbinder;
    private List<SlideBean.SlidesBean> slideslist;

    public static Home_Fragment getInstance() {
        if (instance == null) {
            instance = new Home_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //请求服务器的数据
        getdataFromNet();


    }

    private void getdataFromNet() {
        //接口地址
        String url = "http://api.chinaplat.com/getval_2017?Action=GetSlides";


        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: " + result);
                //解析数据
                parsedata(result);
            }
        });


    }

    private void parsedata(String result) {
        Gson gson = new Gson();
        SlideBean slideBean = gson.fromJson(result, SlideBean.class);
        //轮播图数据
        slideslist = slideBean.getSlides();
        //填充轮播图
        homefragmentVp.setAdapter(new MyviewpaterAdapter());
        //XutilsHttp.getInstance().bindCommonImage(,slideBean.getSlides().get(1).getPic());
    }
    class MyviewpaterAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getContext());
            //图片的接口
            String picURL = slideslist.get(position).getPic();
            XutilsHttp.getInstance().bindCommonImage(iv,picURL,false);
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return slideslist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }


}
