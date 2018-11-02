package com.zqzr.licaitong.bean;

import java.io.Serializable;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/11/1 14:26
 * <p/>
 * Description:
 */

public class QiNiuToken {
    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {

        public String token;
        public String doman;
    }
}
