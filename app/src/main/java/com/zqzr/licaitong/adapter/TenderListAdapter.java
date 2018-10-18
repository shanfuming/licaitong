package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Tender;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 17:50
 * <p/>
 * Description:
 */

public class TenderListAdapter extends BaseAdapter {

    ArrayList<Tender> tenders;

    public TenderListAdapter(ArrayList<Tender> tenders) {
        this.tenders = tenders;
    }

    @Override
    public int getCount() {
        return tenders.size();
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
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_tenderlist,null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tenderTitle = (TextView) convertView.findViewById(R.id.tv_tender_title);
        viewHolder.predictIncome = (TextView) convertView.findViewById(R.id.tv_predictIncome);
        viewHolder.limit = (TextView) convertView.findViewById(R.id.tv_timeLimit);
        viewHolder.start = (TextView) convertView.findViewById(R.id.tv_tenderStart);

        viewHolder.tenderTitle.setText(tenders.get(position).getTenderTitle());
        viewHolder.predictIncome.setText(tenders.get(position).getPredictIncome());
        viewHolder.limit.setText(tenders.get(position).getLimit());
        viewHolder.start.setText(tenders.get(position).getStart());

        return convertView;
    }

    class ViewHolder{
        TextView tenderTitle;
        TextView predictIncome;
        TextView limit;
        TextView start;
    }
}
