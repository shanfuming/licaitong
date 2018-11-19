package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/11/5 17:47
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class ReturnDetail implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public Double rateYear;
        public String borrowName;
        public Double repaidCapital;
        public int status;
        public Double repaidInterest;
        public Long addTime;
        public Double actualAmount;
        public String contractId;
        public int id;
        public Long repaymentTime;
        public String url;
        public String token;

        public Double repaymentAmount;
        public Double interest;
    }
}