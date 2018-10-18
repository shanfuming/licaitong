package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 16:35
 * <p/>
 * Description:
 */

public class Menu {
    String name;
    boolean isSelect;

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
