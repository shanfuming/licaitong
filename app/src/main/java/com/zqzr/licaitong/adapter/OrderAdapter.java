package com.zqzr.licaitong.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Order;
import com.zqzr.licaitong.ui.own.OrderDetailAct;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.DateUtil;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 14:58
 * <p/>
 * Description:
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private ArrayList<Order.Data.CList> orders;

    public OrderAdapter(ArrayList<Order.Data.CList> orders) {
        this.orders = orders;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = View.inflate(ActivityUtils.peek(), R.layout.item_order, null);
        return new OrderHolder(convertView);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, final int position) {
        holder.tvOrderId.setText(orders.get(position).investmentNo);
        holder.tvOrderName.setText(orders.get(position).productName);
        holder.tvOrderTime.setText(DateUtil.formatter(DateUtil.Format.SECOND,orders.get(position).addTime));
        holder.tvPlanMoney.setText(orders.get(position).subscribeAmount+"");
        holder.tvPredictIncome.setText(orders.get(position).rateYear+"%");

        if(orders.get(position).status >= 0){
            if (orders.get(position).status == 0){
                holder.tvOrderState.setText("待受理");
                holder.tvOrderState.setTextColor(Color.parseColor("#fe6b31"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_accept));
            }
            if (orders.get(position).status == 1){
                holder.tvOrderState.setText("待签约");
                holder.tvOrderState.setTextColor(Color.parseColor("#469ee8"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_sign));
            }
            if (orders.get(position).status == 2){
                holder.tvOrderState.setText("已签约");
                holder.tvOrderState.setTextColor(Color.parseColor("#5dc748"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signed));
            }
            if (orders.get(position).status == 3){
                holder.tvOrderState.setText("签约作废");
                holder.tvOrderState.setTextColor(Color.parseColor("#0c1997"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signinvalid));
            }
            if (orders.get(position).status == 4){
                holder.tvOrderState.setText("签约不成功");
                holder.tvOrderState.setTextColor(Color.parseColor("#dc1414"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signfail));
            }
            if (orders.get(position).status == 5){
                holder.tvOrderState.setText("已取消");
                holder.tvOrderState.setTextColor(Color.parseColor("#989898"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_cancel));
            }
            if (orders.get(position).status == 6){
                holder.tvOrderState.setText("已退款");
                holder.tvOrderState.setTextColor(Color.parseColor("#7200ff"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_refund));
            }
            if (orders.get(position).status == 7){
                holder.tvOrderState.setText("待赎回");
                holder.tvOrderState.setTextColor(Color.parseColor("#cead68"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_redemption));
            }
            if (orders.get(position).status == 8){
                holder.tvOrderState.setText("已赎回");
                holder.tvOrderState.setTextColor(Color.parseColor("#088205"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_redemptioned));
            }
            if (orders.get(position).status == 9){
                holder.tvOrderState.setText("已还款");
                holder.tvOrderState.setTextColor(Color.parseColor("#d32bcd"));
                holder.tvOrderState.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_returned));
            }

            holder.rlNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("id",orders.get(position).id+"");
                    ActivityUtils.push(OrderDetailAct.class,intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        private final TextView tvOrderId,tvOrderTime,tvOrderName,tvPlanMoney,tvPredictIncome,tvOrderState;
        private final RelativeLayout rlNext;

        public OrderHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tv_orderTime);
            tvOrderName = (TextView) itemView.findViewById(R.id.tv_orderName);
            tvOrderState = (TextView) itemView.findViewById(R.id.tv_orderState);
            tvPlanMoney = (TextView) itemView.findViewById(R.id.tv_planMoney);
            tvPredictIncome = (TextView) itemView.findViewById(R.id.tv_predictIncome);
            rlNext = (RelativeLayout) itemView.findViewById(R.id.rl_next);
        }
    }
}
