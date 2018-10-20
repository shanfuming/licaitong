package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.view.CircleImageView;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 13:07
 * <p/>
 * Description:
 */

public class OwnActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlPerson, mLlSetting, mLlMoneyRecord, mLlOrder, mLlReturnPlan, mLlInfo;
    private TextView mTvOwnMoney, mTvIncoming, mTvIncomed;
    private RelativeLayout mRlIdentify,mRlBankcard,mRlPlanner,mRlEvalution,mRlQualitied;
    private CircleImageView mIvIcon;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own);

        mLlPerson = (LinearLayout) findViewById(R.id.ll_person);
        mIvIcon = (CircleImageView) findViewById(R.id.iv_person_icon);
        mLlSetting = (LinearLayout) findViewById(R.id.ll_setting);
        mTvOwnMoney = (TextView) findViewById(R.id.tv_own_money);
        mTvIncoming = (TextView) findViewById(R.id.tv_incoming);
        mTvIncomed = (TextView) findViewById(R.id.tv_incomed);

        mLlMoneyRecord = (LinearLayout) findViewById(R.id.ll_moneyRecord);
        mLlOrder = (LinearLayout) findViewById(R.id.ll_order);
        mLlReturnPlan = (LinearLayout) findViewById(R.id.ll_returnPlan);
        mLlInfo = (LinearLayout) findViewById(R.id.ll_info);

        mRlIdentify = (RelativeLayout) findViewById(R.id.rl_own_identify);
        mRlBankcard = (RelativeLayout) findViewById(R.id.rl_own_bankcard);
        mRlPlanner = (RelativeLayout) findViewById(R.id.rl_own_planner);
        mRlEvalution = (RelativeLayout) findViewById(R.id.rl_own_evalution);
        mRlQualitied = (RelativeLayout) findViewById(R.id.rl_own_qualitied);

        mLlMoneyRecord.setOnClickListener(this);
        mLlOrder.setOnClickListener(this);
        mLlReturnPlan.setOnClickListener(this);
        mLlInfo.setOnClickListener(this);
        mRlIdentify.setOnClickListener(this);
        mRlBankcard.setOnClickListener(this);
        mRlPlanner.setOnClickListener(this);
        mRlEvalution.setOnClickListener(this);
        mRlQualitied.setOnClickListener(this);
        mLlPerson.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_moneyRecord:
                ActivityUtils.push(MoneyRecordAct.class);
                break;
            case R.id.ll_order:
                ActivityUtils.push(OrderAct.class);
                break;
            case R.id.ll_returnPlan:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(ReturnPlanAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_identify:
                if (MyApplication.getInstance().isLand()){
//                    if (){//判断是否实名
                    ActivityUtils.push(IdentifyAct.class);
//                        }
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_bankcard:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(BankCardAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_planner:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(PlannerAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_evalution:

                break;
            case R.id.rl_own_qualitied:

                break;
            case R.id.ll_person:

                break;
            case R.id.ll_setting:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(SettingAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.ll_info:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(InfoAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;

        }
    }
}
