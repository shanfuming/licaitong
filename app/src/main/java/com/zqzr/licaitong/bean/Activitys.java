package com.zqzr.licaitong.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/22 10:49
 * <p/>
 * Description:
 */

public class Activitys implements Serializable {
    public String ext;
    public String code;
    public String attach;
    public String message;
    public ArrayList<Data> data;

    public class Data implements Serializable {

        public String numberDay;
        public String title;
        public String activityStatus;
        public String imageUrl;
        public String id;
        public String url;
        public String introduction;
    }
}
