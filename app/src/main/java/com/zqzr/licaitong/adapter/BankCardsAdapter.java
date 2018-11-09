package com.zqzr.licaitong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.BankCards;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.view.TipDialog;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/26 20:04
 * <p/>
 * Description:
 */

public class BankCardsAdapter extends BaseAdapter {

    private ArrayList<BankCards.Data> bankcards;

    public BankCardsAdapter(ArrayList<BankCards.Data> bankcards) {
        this.bankcards = bankcards;
    }

    @Override
    public int getCount() {
        return bankcards.size();
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(ActivityUtils.peek(), R.layout.item_card, null);
            viewHolder.cardIcon = (ImageView) convertView.findViewById(R.id.iv_bank_logo);
            viewHolder.cardName = (TextView) convertView.findViewById(R.id.tv_bank_name);
            viewHolder.cardNum = (TextView) convertView.findViewById(R.id.tv_bank_cardNum);
            viewHolder.cardBg = (LinearLayout) convertView.findViewById(R.id.ll_card);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cardNum.setText(bankcards.get(position).bankNo);
        viewHolder.cardName.setText(bankcards.get(position).bank);
        if (position % getCount() == 0 ){
            viewHolder.cardBg.setBackground(ActivityUtils.peek().getResources().getDrawable(R.mipmap.card_red));
        }

        if (position % getCount() == 1 ){
            viewHolder.cardBg.setBackground(ActivityUtils.peek().getResources().getDrawable(R.mipmap.card_blue));
        }

        if (position % getCount() == 2 ){
            viewHolder.cardBg.setBackground(ActivityUtils.peek().getResources().getDrawable(R.mipmap.card_green));
        }

        return convertView;
    }

    class ViewHolder {
        TextView cardName, cardNum;
        ImageView cardIcon;
        LinearLayout cardBg;
    }
}
