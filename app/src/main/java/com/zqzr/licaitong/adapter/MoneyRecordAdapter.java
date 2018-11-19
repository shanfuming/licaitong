package com.zqzr.licaitong.adapter;

import android.text.NoCopySpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.MoneyRecord;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.DateUtil;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 20:54
 * <p/>
 * Description:
 */

public class MoneyRecordAdapter extends BaseAdapter {

    private ArrayList<MoneyRecord.Data.CList> records;

    public MoneyRecordAdapter(ArrayList<MoneyRecord.Data.CList> records) {
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_moneyrecord, null);
            viewHolder.recordTitle = (TextView) convertView.findViewById(R.id.tv_record_title);
            viewHolder.recordTime = (TextView) convertView.findViewById(R.id.tv_record_time);
            viewHolder.recordmoney = (TextView) convertView.findViewById(R.id.tv_record_money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.recordTitle.setText(records.get(position).productName);
        viewHolder.recordTime.setText(DateUtil.formatter(DateUtil.Format.SECOND,records.get(position).addTime));
        viewHolder.recordmoney.setText(Utils.getDouble2(records.get(position).actualAmount));
        return convertView;
    }

    class ViewHolder {
        TextView recordTitle;
        TextView recordTime;
        TextView recordmoney;
    }
}
