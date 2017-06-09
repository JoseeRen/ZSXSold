/*
 * Create on 2017-6-8 下午4:35
 * FileName: Constant.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.app;

/**
 * Created by Mr_Shadow on 2017/6/8.
 * 常量存放
 */

public class Constant {
    public static final String SAVE_AD_PATH = MyApplication.getInstance().getFilesDir().getPath() + "/app_hy1.jpg";
    //config   应用的一些配置


    //url   请求的url
    //接口根路径
    public static String HOSTNAME = "http://api.chinaplat.com/getval_2017?";
    //splash页面的adurl
    public static String ACTION_GETADPIC = "Action=getADpic";


    private Constant() {

    }
}
