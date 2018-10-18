package com.zqzr.licaitong.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.HomeTouziType;
import com.zqzr.licaitong.ui.home.TenderListAct;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 15:25
 * <p/>
 * Description:
 */

public class HomeTouziListAdapter extends BaseAdapter {

    private ArrayList<HomeTouziType> touziList;

    public HomeTouziListAdapter(ArrayList<HomeTouziType> touziList) {
        this.touziList = touziList;
    }

    @Override
    public int getCount() {
        return touziList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_home_touzitype,null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.predictIncome = (TextView) convertView.findViewById(R.id.tv_predictIncome);
        viewHolder.predictIncomeName = (TextView) convertView.findViewById(R.id.tv_predictIncome_name);
        viewHolder.touziType = (TextView) convertView.findViewById(R.id.tv_touzi_type);
        viewHolder.touziMore = (TextView) convertView.findViewById(R.id.tv_touzi_more);
        viewHolder.tenderName = (TextView) convertView.findViewById(R.id.tv_tender_name);
        viewHolder.tenderDayNum = (TextView) convertView.findViewById(R.id.tv_tender_dayNum);
        viewHolder.tenderStartMark = (TextView) convertView.findViewById(R.id.tv_condition_startMark);

        viewHolder.predictIncome.setText(touziList.get(position).getPredictIncome());
        viewHolder.predictIncomeName.setText(touziList.get(position).getPredictIncomeName());
        viewHolder.touziType.setText(touziList.get(position).getTouziType());
        viewHolder.tenderName.setText(touziList.get(position).getTenderName());
        viewHolder.tenderDayNum.setText(touziList.get(position).getDayNum());
        viewHolder.tenderStartMark.setText(touziList.get(position).getStartMark());

        viewHolder.touziMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("type",touziList.get(position).getTypeId());
                ActivityUtils.push(TenderListAct.class,intent);
            }
        });


        return convertView;
    }

    class ViewHolder{
        TextView touziType,touziMore,predictIncome,predictIncomeName,tenderName,tenderDayNum,tenderStartMark;
    }
}
