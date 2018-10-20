package com.zqzr.licaitong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 16:57
 * <p/>
 * Description:
 */

public class ReturnPlanAdapter extends BaseAdapter {

    private ArrayList<String> returnlist;

    public ReturnPlanAdapter(ArrayList<String> returnlist) {
        this.returnlist = returnlist;
    }

    @Override
    public int getCount() {
        return returnlist.size();
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
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_returnplan,null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.returnTitle = (TextView) convertView.findViewById(R.id.tv_return_title);
        viewHolder.returnType = (TextView) convertView.findViewById(R.id.tv_return_type);
        viewHolder.returnAll = (TextView) convertView.findViewById(R.id.tv_return_all);
        viewHolder.returnDate = (TextView) convertView.findViewById(R.id.tv_return_date);

        return convertView;
    }

    class ViewHolder{
        TextView returnTitle,returnType,returnAll,returnDate;
    }
}
