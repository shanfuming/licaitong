package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.SubscribeRecord;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.DateUtil;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/27 16:11
 * <p/>
 * Description:
 */

public class RecordAdapter extends BaseAdapter {

    private ArrayList<SubscribeRecord.Data.CList> records;

    public RecordAdapter(ArrayList<SubscribeRecord.Data.CList> records) {
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
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_record,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.record_name);
            viewHolder.num = (TextView) convertView.findViewById(R.id.record_num);
            viewHolder.time = (TextView) convertView.findViewById(R.id.record_time);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.num.setText(Utils.getDouble2(records.get(position).reservationAmount));
        viewHolder.name.setText(Utils.getEncodeStr(records.get(position).userName));
        viewHolder.time.setText(DateUtil.formatter(DateUtil.Format.SECOND,records.get(position).addTime));

        return convertView;
    }

    class ViewHolder{
        TextView name,num, time;
    }
}
