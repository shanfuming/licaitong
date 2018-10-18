package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 15:45
 * <p/>
 * Description:
 */

public class HomeTouziType {
    String touziType;
    String predictIncome;
    String predictIncomeName;
    String tenderName;
    String dayNum;
    String startMark;
    int typeId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public HomeTouziType(int typeId, String touziType, String predictIncome, String predictIncomeName, String tenderName, String dayNum, String startMark) {
        this.touziType = touziType;
        this.predictIncome = predictIncome;
        this.predictIncomeName = predictIncomeName;
        this.tenderName = tenderName;
        this.dayNum = dayNum;
        this.startMark = startMark;
        this.typeId = typeId;

    }

    public String getTouziType() {
        return touziType;
    }

    public void setTouziType(String touziType) {
        this.touziType = touziType;
    }

    public String getPredictIncome() {
        return predictIncome;
    }

    public void setPredictIncome(String predictIncome) {
        this.predictIncome = predictIncome;
    }

    public String getPredictIncomeName() {
        return predictIncomeName;
    }

    public void setPredictIncomeName(String predictIncomeName) {
        this.predictIncomeName = predictIncomeName;
    }

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getStartMark() {
        return startMark;
    }

    public void setStartMark(String startMark) {
        this.startMark = startMark;
    }
}
