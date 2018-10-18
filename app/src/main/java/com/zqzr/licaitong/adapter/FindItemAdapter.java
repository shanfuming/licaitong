package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 16:27
 * <p/>
 * Description:
 */

public class FindItemAdapter extends BaseAdapter {

    private ArrayList<FindItem> findItems;

    public FindItemAdapter(ArrayList<FindItem> findItems) {
        this.findItems = findItems;
    }

    @Override
    public int getCount() {
        return findItems.size();
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
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_find,null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.findItemImg = (ImageView) convertView.findViewById(R.id.iv_find_img);
        viewHolder.findItemTitle = (TextView) convertView.findViewById(R.id.tv_find_title);
        viewHolder.findItemTime = (TextView) convertView.findViewById(R.id.tv_find_time);

        viewHolder.findItemImg.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.mipmap.guide_01));
        viewHolder.findItemTitle.setText(findItems.get(position).getFind_title());
        viewHolder.findItemTime.setText(findItems.get(position).getFind_time());

        return convertView;
    }

    class ViewHolder{
        TextView findItemTitle,findItemTime;
        ImageView findItemImg;
    }
}
