package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 13:44
 * <p/>
 * Description:
 */

import java.io.Serializable;
import java.util.ArrayList;

public class BankCards implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public ArrayList<Data> data;

    public class Data implements Serializable {

        public String bank;
        public int type;
        public String bankNo;
        public String id;
    }
}
