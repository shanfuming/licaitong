package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/25 16:14
 * <p/>
 * Description:
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Version implements Serializable {

    public String ext;
    public String code;
    public String attach;
    public String message;
    public ArrayList<Data> data;

    public class Data implements Serializable {

        public String versionCode;
        public String loadUrl;
    }
}