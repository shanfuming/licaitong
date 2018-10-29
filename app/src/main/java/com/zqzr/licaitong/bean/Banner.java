package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/25 16:14
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Banner implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public ArrayList<Data> data;

    public class Data implements Serializable {

        public String imageUrl;
        public int id;
        public String title;
        public String url;
        public String content;
    }
}