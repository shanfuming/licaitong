package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.view.TipDialog;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 13:26
 * <p/>
 * Description:
 */

public class PlannerAct extends BaseActivity {
    private TextView mTvName,mTvPhone, mTvCommoit,mTvCity;
    private String plannerName,plannerPhone,plannerCity;

    @Override
    protected void initView() {
        setContentView(R.layout.act_planner);

        mTvName = (TextView) findViewById(R.id.tv_planner_name);
        mTvPhone = (TextView) findViewById(R.id.tv_planner_phone);
        mTvCity = (TextView) findViewById(R.id.tv_planner_city);
        mTvCommoit = (TextView) findViewById(R.id.tv_change_commit);

        mTvCommoit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_planner));
        setBackOption(true);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        plannerName = intent.getStringExtra("plannerName");
        plannerPhone = intent.getStringExtra("plannerPhone");
        plannerCity = intent.getStringExtra("plannerCity");

        mTvName.setText(plannerName);
        mTvPhone.setText(plannerPhone);
        mTvCity.setText(plannerCity);
    }

    /**
     * 提示
     */
    private void tip(){
        final TipDialog tipDialog = new TipDialog(this,true);
        tipDialog.setTitle("温馨提示");
        tipDialog.setContent("电话联系理财师"+mTvName.getText().toString());
        tipDialog.setButtonDes("取消", "确定");
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                SettingAct.call(PlannerAct.this, mTvPhone.getText().toString());
                tipDialog.dismiss();
            }
        });
    }


}
