package com.zqzr.licaitong.bean;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 17:51
 * <p/>
 * Description:
 */

public class Tender {

    String tenderTitle;
    String predictIncome;
    String limit;
    String start;

    public Tender(String tenderTitle, String predictIncome, String limit, String start) {
        this.tenderTitle = tenderTitle;
        this.predictIncome = predictIncome;
        this.limit = limit;
        this.start = start;
    }

    public String getTenderTitle() {
        return tenderTitle;
    }

    public void setTenderTitle(String tenderTitle) {
        this.tenderTitle = tenderTitle;
    }

    public String getPredictIncome() {
        return predictIncome;
    }

    public void setPredictIncome(String predictIncome) {
        this.predictIncome = predictIncome;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
