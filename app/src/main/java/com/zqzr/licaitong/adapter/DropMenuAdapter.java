package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Menu;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 14:13
 * <p/>
 * Description:
 */

public class DropMenuAdapter extends BaseAdapter {

    private ArrayList<Menu> menuList = new ArrayList<>();

    @Override
    public int getCount() {
        return menuList.size();
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
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_dropmenu,null);
            viewHolder.menuListName = (TextView) convertView.findViewById(R.id.tv_menuList_name);
            viewHolder.select = (ImageView) convertView.findViewById(R.id.iv_menu_select);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.menuListName.setText(menuList.get(position).getName());
        if (menuList.get(position).isSelect()){
            viewHolder.select.setVisibility(View.VISIBLE);
            viewHolder.menuListName.setTextColor(ActivityUtils.peek().getResources().getColor(R.color.app_color_principal));
        }else{
            viewHolder.select.setVisibility(View.GONE);
            viewHolder.menuListName.setTextColor(ActivityUtils.peek().getResources().getColor(R.color.text_grey));
        }
        return convertView;
    }

    class ViewHolder{
        TextView menuListName;
        ImageView select;
    }

    public void setMenuList(ArrayList<Menu> list){
        menuList.clear();
        menuList.addAll(list);
        notifyDataSetChanged();
    }
}
