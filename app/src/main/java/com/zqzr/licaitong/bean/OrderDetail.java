package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 13:44
 * <p/>
 * Description:
 */

import java.io.Serializable;

public class OrderDetail implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public int  id;
        public int  productId;
        public String productName;
        public double rateYear;
        public double subscribeAmount;
        public long addTime;
        public int status;
        public String realName;
        public String phone;
        public String bank;
        public double actualAmount;
        public long payTime;
        public String certificateUrl;
        public String url;
        public String remitBankNo;
        public String repaymentBankNo;
    }
}
