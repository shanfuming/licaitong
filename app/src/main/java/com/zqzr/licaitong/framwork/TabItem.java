package com.zqzr.licaitong.framwork;

import android.content.Intent;

/**
 * <br/>PROJECT_NAME : HerVillage
 * <br/>PACKAGE_NAME : com.hervillage.bean
 * <br/>AUTHOR : Machao
 * <br/>DATA : 2016/guide_3/7 0007
 * <br/>TIME : 11:30
 * <br/>MSG :
 */
public class TabItem {
    private String title;
    private int icon;
    private Intent intent;
    public TabItem(){

    }

    public TabItem(String title, int icon, Intent intent){
        this.title = title;
        this.icon = icon;
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
