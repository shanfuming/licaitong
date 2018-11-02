package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/27 18:06
 * <p/>
 * Description:
 */

import java.io.Serializable;

public class ProductDetail implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public double proTotalAmount;
        public int earningWay;
        public String name;
        public Double expectedYield;
        public double increasingAmount;
        public String increasingAmountStr;
        public double reservationAmount;
        public int projectDuration;
        public double purchaseAmount;
        public int status;

    }
}