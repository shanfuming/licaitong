package com.zqzr.licaitong.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/11/1 10:35
 * <p/>
 * Description:
 */

public class ReturnPlan {
    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public int total;
        public int pageNum;
        public ArrayList<CList> cList;

        public class CList implements Serializable {

            public String borrowName;
            public int id;
            public int type;
            public double priAndInt;
            public long repTime;
        }
    }
}
