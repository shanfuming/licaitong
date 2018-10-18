package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 16:26
 * <p/>
 * Description:
 */

public class FindItem {
    String find_img;
    String find_title;
    String find_time;

    public FindItem(String find_img, String find_title, String find_time) {
        this.find_img = find_img;
        this.find_title = find_title;
        this.find_time = find_time;
    }

    public String getFind_img() {
        return find_img;
    }

    public void setFind_img(String find_img) {
        this.find_img = find_img;
    }

    public String getFind_title() {
        return find_title;
    }

    public void setFind_title(String find_title) {
        this.find_title = find_title;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }
}
