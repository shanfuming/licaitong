package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 13:44
 * <p/>
 * Description:
 */

import java.io.Serializable;
import java.util.List;
import java.io.Serializable;

public class OrderDetail implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public String subscribeAmount;
        public String repaymentBankNo;
        public String productId;
        public String addTime;
        public String payTime;
        public String actualAmount;
        public String investmentNo;
        public String url;
        public String productName;
        public String remitBankNo;
        public String rateYear;
        public String realName;
        public String bank;
        public String phone;
        public String id;
        public String status;
        public List<String> certificateUrl;

    }
}
