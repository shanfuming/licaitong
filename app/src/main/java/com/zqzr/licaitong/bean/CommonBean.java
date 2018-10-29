package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/25 14:08
 * <p/>
 * Description:
 */

import java.io.Serializable;

public class CommonBean implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public int expectTotalIncome;
        public String code;
        public String gender;
        public String city;
        public String imageCode;
        public String suffix;
        public String idNo;
        public String isLock;
        public String realNameStatusTranslate;
        public String password;
        public String loginTime;
        public String lockTime;
        public String referralCode;
        public String startTime;
        public String contactAddress;
        public String id;
        public String keyword;
        public String orderByClause;
        public String headPortraitUrl;
        public String email;
        public String amountTotalInvestment;
        public String registerIp;
        public int corpId;
        public String registerTime;
        public String fpName;
        public int rows;
        public String realNameStatus;
        public String lockRemark;
        public String realNameTime;
        public String token;
        public String realName;
        public String lastLoginTime;
        public String emailStatus;
        public String phone;
        public int fpId;
        public Boolean manageQuery;
        public String isLockTranslate;
        public int page;
        public String endTime;
        public String userType;
        public String username;
        public String isInvested;
    }
}
