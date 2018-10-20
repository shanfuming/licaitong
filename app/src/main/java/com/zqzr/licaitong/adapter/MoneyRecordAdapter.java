package com.zqzr.licaitong.adapter;

import android.text.NoCopySpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.utils.ActivityUtils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 20:54
 * <p/>
 * Description:
 */

public class MoneyRecordAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_moneyrecord,null);
        }else{

        }
        return convertView;
    }

    class ViewHolder{
        TextView recordTitle;
        TextView recordTime;
        TextView recordmoney;
    }
}
