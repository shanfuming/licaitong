package com.zqzr.licaitong.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.HomeTouziTypeAndFind;
import com.zqzr.licaitong.ui.home.TenderDetailAct;
import com.zqzr.licaitong.ui.home.TenderListAct;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 15:25
 * <p/>
 * Description:
 */

public class HomeTouziListAdapter extends BaseAdapter {

    private ArrayList<HomeTouziTypeAndFind.Data.Inve> touziList;

    public HomeTouziListAdapter(ArrayList<HomeTouziTypeAndFind.Data.Inve> touziList) {
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
        viewHolder.touziDetail = (LinearLayout) convertView.findViewById(R.id.ll_touzi_detail);
        viewHolder.tenderState = (TextView) convertView.findViewById(R.id.tv_tender_state);

        viewHolder.predictIncome.setText(touziList.get(position).expectedYield+"%");
//        viewHolder.predictIncomeName.setText(touziList.get(position).getPredictIncomeName());
        viewHolder.touziType.setText(Utils.getType(touziList.get(position).type));
        viewHolder.tenderName.setText(touziList.get(position).name);
        viewHolder.tenderDayNum.setText(touziList.get(position).projectDuration+"天");
        viewHolder.tenderStartMark.setText(Utils.getWan(touziList.get(position).purchaseAmount)+"起投");
        if(touziList.get(position).status > 0){
            if (touziList.get(position).status == 1){
                viewHolder.tenderState.setText("发行中");
                viewHolder.tenderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_type_ing));
            }else{
                viewHolder.tenderState.setText("已募满");
                viewHolder.tenderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_type_ed));
            }
        }

        viewHolder.touziMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("type",touziList.get(position).type);
                ActivityUtils.push(TenderListAct.class,intent);
            }
        });

        viewHolder.touziDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("type",touziList.get(position).type);
                intent.putExtra("id",touziList.get(position).id);
                ActivityUtils.push(TenderDetailAct .class,intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView touziType,touziMore,predictIncome,predictIncomeName,tenderName,tenderDayNum,tenderStartMark,tenderState;
        LinearLayout touziDetail;
    }
}
