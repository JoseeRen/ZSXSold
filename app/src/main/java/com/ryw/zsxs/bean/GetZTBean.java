package com.ryw.zsxs.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shishaoyou on 2017/6/25.
 * 2017Bean类
 */

public class GetZTBean implements Serializable {

    /**
     * page_all : 4
     * page_now : 1
     * types : 1
     * list : [{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png","ztid":112},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091695859585.jpg","ztid":110},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170223/20170223114681028102.jpg","ztid":108},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170223/20170223113815821582.jpg","ztid":107},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517092561946194.jpg","ztid":104},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20160216/20160216113634253425.png","ztid":39},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20161104/20161104095221012101.jpg","ztid":24},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20161007/20161007164324962496.jpg","ztid":109},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091256145614.jpg","ztid":106},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091091509150.png","ztid":103}]
     */

    private int page_all;
    private int page_now;
    private int types;
    private List<ListBean> list;

    public int getPage_all() {
        return page_all;
    }

    public void setPage_all(int page_all) {
        this.page_all = page_all;
    }

    public int getPage_now() {
        return page_now;
    }

    public void setPage_now(int page_now) {
        this.page_now = page_now;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * imgURL : http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png
         * ztid : 112
         */

        private String imgURL;
        private int ztid;

        public String getImgURL() {
            return imgURL;
        }

        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        public int getZtid() {
            return ztid;
        }

        public void setZtid(int ztid) {
            this.ztid = ztid;
        }
    }
}
