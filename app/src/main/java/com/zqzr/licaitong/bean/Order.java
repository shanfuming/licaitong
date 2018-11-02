package com.zqzr.licaitong.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 15:00
 * <p/>
 * Description:
 */

public class Order {
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

            public String productName;
            public String investmentNo;
            public double rateYear;
            public int id;
            public int status;
            public double subscribeAmount;
            public long addTime;
        }
    }
}
