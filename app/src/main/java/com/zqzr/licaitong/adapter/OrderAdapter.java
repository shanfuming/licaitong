package com.zqzr.licaitong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.bean.Order;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 14:58
 * <p/>
 * Description:
 */

public class OrderAdapter extends RecyclerView.Adapter {

    private ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = View.inflate(ActivityUtils.peek(), R.layout.item_order, null);
        return new OrderHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        private final ImageView ivOrderState;
        private final TextView tvOrderId,tvOrderTime,tvOrderName,tvPlanMoney,tvPredictIncome;

        public OrderHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tv_orderTime);
            tvOrderName = (TextView) itemView.findViewById(R.id.tv_orderName);
            ivOrderState = (ImageView) itemView.findViewById(R.id.iv_orderState);
            tvPlanMoney = (TextView) itemView.findViewById(R.id.tv_planMoney);
            tvPredictIncome = (TextView) itemView.findViewById(R.id.tv_predictIncome);
        }
    }
}
