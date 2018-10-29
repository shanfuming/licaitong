package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/27 19:04
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public int total;
        public int pageNum;
        public List<CList> cList;

        public class CList implements Serializable {

            public String number;
            public String name;
            public Double expectedYield;
            public int id;
            public int type;
            public int purchaseAmount;
            public int projectDuration;
            public int status;
        }
    }
}