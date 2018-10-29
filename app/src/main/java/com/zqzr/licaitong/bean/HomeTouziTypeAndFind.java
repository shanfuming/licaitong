package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 15:45
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeTouziTypeAndFind implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {
        public ArrayList<Moments> moments;

        public class Moments implements Serializable {

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

        public ArrayList<Inve> inve;

        public class Inve implements Serializable {

            public String name;
            public float expectedYield;
            public int type;
            public int projectDuration;
            public int purchaseAmount;
            public int status;
            public int id;
        }
    }
}
