package com.zqzr.licaitong.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Activitys;
import com.zqzr.licaitong.ui.CommonWebviewAct;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.CountDownView;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/22 10:48
 * <p/>
 * Description:
 */

public class ActivitysAdapter extends RecyclerView.Adapter<ActivitysAdapter.ActivityHolder> {

    private ArrayList<Activitys.Data> activityses;

    public ActivitysAdapter(ArrayList<Activitys.Data> activityses) {
        this.activityses = activityses;
    }

    @Override
    public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ActivityUtils.peek(), R.layout.item_actvity,null);
        return new ActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityHolder holder, final int position) {
        Utils.loadImg(holder.ivImg,activityses.get(position).imageUrl,null);
        if (!TextUtils.isEmpty(activityses.get(position).introduction)){
            holder.tvContent.setText(activityses.get(position).introduction);
        }
        holder.tvTitle.setText(activityses.get(position).title);
        if (Integer.valueOf(activityses.get(position).activityStatus) == 0){
            holder.llCoundown.setVisibility(View.VISIBLE);
        }else{
            holder.llCoundown.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(activityses.get(position).numberDay)&&Integer.valueOf(activityses.get(position).numberDay) > 0){
            holder.countdownView.setTime(Integer.valueOf(activityses.get(position).numberDay));
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(activityses.get(position).url)&&!TextUtils.isEmpty(activityses.get(position).title)){
                    Intent intent = new Intent();
                    intent.putExtra("title",activityses.get(position).title);
                    intent.putExtra("url",activityses.get(position).url);
                    ActivityUtils.push(CommonWebviewAct.class,intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return activityses.size();
    }

    class ActivityHolder extends RecyclerView.ViewHolder{

        private final ImageView ivImg,ivState;
        private final LinearLayout llCoundown;
        private final TextView tvContent,tvTitle;
        private final CountDownView countdownView;
        private final RelativeLayout rlItem;

        public ActivityHolder(View itemView) {
            super(itemView);

            ivImg = (ImageView) itemView.findViewById(R.id.iv_activity_img);
            ivState = (ImageView) itemView.findViewById(R.id.iv_activity_state);
            llCoundown = (LinearLayout) itemView.findViewById(R.id.ll_act_countdown);
            tvContent = (TextView) itemView.findViewById(R.id.tv_activity_content);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_activity_title);
            countdownView = (CountDownView) itemView.findViewById(R.id.countdownview);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_activity_item);
        }
    }
}
