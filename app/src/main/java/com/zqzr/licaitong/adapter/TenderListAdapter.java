package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Product;
import com.zqzr.licaitong.bean.Tender;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 17:50
 * <p/>
 * Description:
 */

public class TenderListAdapter extends BaseAdapter {

    ArrayList<Product.Data.CList> tenders;

    public TenderListAdapter(ArrayList<Product.Data.CList> tenders) {
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
        viewHolder.status = (TextView) convertView.findViewById(R.id.tv_tender_state);

        viewHolder.tenderTitle.setText(tenders.get(position).name);
        viewHolder.predictIncome.setText(tenders.get(position).expectedYield+"%");
        viewHolder.limit.setText(tenders.get(position).projectDuration+"天");
        viewHolder.start.setText(Utils.getWan(tenders.get(position).purchaseAmount)+"起投");

        if(tenders.get(position).status > 0){
            if (tenders.get(position).status == 1){
                viewHolder.status.setText("发行中");
                viewHolder.status.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_type_ing));
            }else{
                viewHolder.status.setText("已募满");
                viewHolder.status.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_type_ed));
            }
        }

        return convertView;
    }

    class ViewHolder{
        TextView tenderTitle;
        TextView predictIncome;
        TextView limit;
        TextView start;
        TextView status;
    }
}
