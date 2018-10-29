package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 14:00
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OwnInfo implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public Double amountTotalInvestment  ;
        public String lcsCity  ;
        public String city  ;
        public String userName  ;
        public int realNameStatus  ;
        public Double incomeCollected  ;
        public String idNo  ;
        public String lcsPhone  ;
        public String realName  ;
        public Double incomeCollecting  ;
        public String province  ;
        public int bankStatus  ;
        public String phone  ;
        public String referralCode  ;
        public String lcsRealName  ;
        public String contactAddress  ;
        public String headPortraitUrl  ;

    }
}
