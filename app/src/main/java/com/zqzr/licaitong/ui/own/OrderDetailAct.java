package com.zqzr.licaitong.ui.own;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 15:11
 * <p/>
 * Description:
 */

public class OrderDetailAct extends BaseActivity {
    private TextView mTvName, mTvOrderId, mTvPredictIncome, mTvPlanMoney, mTvState, mTvUserName, mTvPhone, mTvPayCard, mTvReturnCard, mTvPayNum, mTvVoucher, mTvPaydate, mTvCancel;
    private LinearLayout mLlVoucher;
    private ImageView mIvArrow, mIvVoucher;

    @Override
    protected void initView() {
        setContentView(R.layout.act_order_detail);

        mTvName = (TextView) findViewById(R.id.tv_detail_name);
        mTvOrderId = (TextView) findViewById(R.id.tv_detail_orderId);
        mTvPredictIncome = (TextView) findViewById(R.id.tv_detail_predictIncome);
        mTvPlanMoney = (TextView) findViewById(R.id.tv_detail_planMoney);
        mTvState = (TextView) findViewById(R.id.tv_detail_state);
        mTvUserName = (TextView) findViewById(R.id.tv_detail_username);
        mTvPhone = (TextView) findViewById(R.id.tv_detail_phone);
        mTvPayCard = (TextView) findViewById(R.id.tv_detail_paybankcard);
        mTvReturnCard = (TextView) findViewById(R.id.tv_detail_returnbankcard);
        mTvPayNum = (TextView) findViewById(R.id.tv_detail_paynum);
        mTvPaydate = (TextView) findViewById(R.id.tv_detail_paydate);
        mLlVoucher = (LinearLayout) findViewById(R.id.ll_voucher);
        mTvVoucher = (TextView) findViewById(R.id.tv_detail_voucher);
        mIvArrow = (ImageView) findViewById(R.id.iv_detail_arrow);
        mIvVoucher = (ImageView) findViewById(R.id.iv_voucher);
        mTvCancel = (TextView) findViewById(R.id.tv_detail_cancel);


    }

    @Override
    protected void initData() {
        getDataFromServer();
    }

    public void getDataFromServer() {

    }
}
