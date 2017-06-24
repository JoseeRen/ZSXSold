package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.view.MyViewpager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.ryw.zsxs.base.BaseActivity;

/**
 * Created by Zhao on 2017/6/24.
 * 我的收藏页面
 */

public class MyCollect extends BaseActivity {


    @BindView(R.id.rb_shipin)
    RadioButton rbShipin;
    @BindView(R.id.rb_yinpin)
    RadioButton rbYinpin;
    @BindView(R.id.rb_dushu)
    RadioButton rbDushu;
    @BindView(R.id.rb_wenzhang)
    RadioButton rbWenzhang;
    @BindView(R.id.rg_mycollect)
    RadioGroup rgMycollect;
    @BindView(R.id.mycollect_vp)
    MyViewpager mycollectVp;
    private ArrayList<RadioButton> rb;






    public int getContentViewResId() {
        return R.layout.activity_mycollect;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
    }



    private void initData() {
        rb = new ArrayList<>();
        rb.add(rbShipin);
        rb.add(rbYinpin);
        rb.add(rbDushu);
        rb.add(rbWenzhang);

        mycollectVp.setAdapter(new MyPagerAdapter());
        mycollectVp.setOnPageChangeListener(new MyOnPageChangeListener());
    }



    //viewpager页面滑动的监听，用来实现上面按钮的选择
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            rb.get(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }




    //viewpager的填充
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return rb.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.mycollect_list_item, null);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }




}
