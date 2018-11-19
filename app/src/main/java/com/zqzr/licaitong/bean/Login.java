package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 13:44
 * <p/>
 * Description:
 */

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class Login implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public String realName;
        public String phone;
        public int  id;
        public String headPortraitUrl;
        public String token;
        public String idNo;
    }
}
