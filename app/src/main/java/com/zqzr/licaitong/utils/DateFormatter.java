package com.zqzr.licaitong.utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/8 17:02
 * <p/>
 * Description:
 */
public enum DateFormatter {
    NORMAL("yyyy-MM-dd HH:mm"),
    DD("yyyy-MM-dd"),
    SS("yyyy-MM-dd HH:mm:ss");
    private String value;

    DateFormatter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}