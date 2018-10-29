package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 16:26
 * <p/>
 * Description:
 */


import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindItem implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public int total;
        public String bannerUrl;
        public int pageNum;
        public ArrayList<CList> cList;

        public class CList implements Serializable {

            public Long publishTime;
            public Long addTime;
            public String updateIp;
            public Long updateTime;
            public String updateOperator;
            public String source;
            public String title;
            public int type;
            public String addIp;
            public int isDisplay;
            public String content;
            public String urlHref;
            public String addOperator;
            public String subTitle;
            public int isTop;
            public String imageUrl;
            public int id;
            public String introduction;
        }
    }
}