package com.zqzr.licaitong.ui.own;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.OrderDetail;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.DateUtil;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 15:11
 * <p/>
 * Description:
 */

public class OrderDetailAct extends BaseActivity {
    private TextView mTvName, mTvOrderId, mTvPredictIncome, mTvPlanMoney, mTvState, mTvState2, mTvUserName, mTvPhone, mTvPayCard, mTvReturnCard, mTvPayNum, mTvVoucher, mTvPaydate, mTvCancel;
    private LinearLayout mLlVoucher;
    private ImageView mIvArrow, mIvVoucher;
    private KeyDownLoadingDialog loadingDialog;
    private boolean isShow;
    private int productId,orderId;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constant.NUMBER_1:
                    loadingDialog.dismiss();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.act_order_detail);

        mTvName = (TextView) findViewById(R.id.tv_detail_name);
        mTvOrderId = (TextView) findViewById(R.id.tv_detail_orderId);
        mTvPredictIncome = (TextView) findViewById(R.id.tv_detail_predictIncome);
        mTvPlanMoney = (TextView) findViewById(R.id.tv_detail_planMoney);
        mTvState = (TextView) findViewById(R.id.tv_detail_state);
        mTvState2 = (TextView) findViewById(R.id.iv_detail_state);
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

        mTvVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShow) {
                    mIvArrow.setImageDrawable(getResources().getDrawable(R.mipmap.up_arrow));
                    mIvVoucher.setVisibility(View.VISIBLE);
                } else {
                    mIvArrow.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow));
                    mIvVoucher.setVisibility(View.GONE);
                }
                isShow = !isShow;
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.show();
        getDataFromServer(getIntent().getStringExtra("id"));
    }

    public void getDataFromServer(String id) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", id);

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.OrderDetail, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        OrderDetail orderDetail = JsonUtil.parseJsonToBean(response.body(), OrderDetail.class);

                        mTvOrderId.setText(orderDetail.data.id + "");
                        mTvName.setText(orderDetail.data.productName);
                        mTvPredictIncome.setText(orderDetail.data.rateYear + "%");
                        mTvPlanMoney.setText(orderDetail.data.subscribeAmount + "");
                        mTvPaydate.setText(DateUtil.formatter(DateUtil.Format.SECOND, orderDetail.data.addTime));
                        mTvUserName.setText(Utils.getEncodeName(orderDetail.data.realName));
                        mTvPhone.setText(Utils.getEncodeStr(orderDetail.data.phone));
                        mTvPayCard.setText(orderDetail.data.remitBankNo);
                        mTvReturnCard.setText(orderDetail.data.repaymentBankNo);
                        mTvPayNum.setText(orderDetail.data.actualAmount + "");
                        mTvPaydate.setText(DateUtil.formatter(DateUtil.Format.DATE, orderDetail.data.payTime));
                        Utils.loadImg(mIvVoucher, orderDetail.data.certificateUrl, null);
                        if (orderDetail.data.status == 0 || orderDetail.data.status == 1) {
                            mTvCancel.setVisibility(View.VISIBLE);
                        } else {
                            mTvCancel.setVisibility(View.GONE);
                        }

                        productId = orderDetail.data.productId;
                        orderId = orderDetail.data.id;

                        if (orderDetail.data.status > 0) {
                            if (orderDetail.data.status == 0) {
                                mTvState.setText("待受理");
                                mTvState2.setText("待受理");
                                mTvState2.setTextColor(0xfe6b31);
                                mTvState.setTextColor(0xfe6b31);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_accept));
                            }
                            if (orderDetail.data.status == 1) {
                                mTvState.setText("待签约");
                                mTvState2.setText("待签约");
                                mTvState2.setTextColor(0x469ee8);
                                mTvState.setTextColor(0x469ee8);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_sign));
                            }
                            if (orderDetail.data.status == 2) {
                                mTvState.setText("已签约");
                                mTvState2.setText("已签约");
                                mTvState2.setTextColor(0x5dc748);
                                mTvState.setTextColor(0x5dc748);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signed));
                            }
                            if (orderDetail.data.status == 3) {
                                mTvState.setText("签约作废");
                                mTvState2.setText("签约作废");
                                mTvState2.setTextColor(0x0c1997);
                                mTvState.setTextColor(0x0c1997);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signinvalid));
                            }
                            if (orderDetail.data.status == 4) {
                                mTvState.setText("签约不成功");
                                mTvState2.setText("签约不成功");
                                mTvState2.setTextColor(0xdc1414);
                                mTvState.setTextColor(0xdc1414);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_signfail));
                            }
                            if (orderDetail.data.status == 5) {
                                mTvState.setText("已取消");
                                mTvState2.setText("已取消");
                                mTvState2.setTextColor(0xfe6b31);
                                mTvState.setTextColor(0xfe6b31);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_type_ed));
                            }
                            if (orderDetail.data.status == 6) {
                                mTvState.setText("已退款");
                                mTvState2.setText("已退款");
                                mTvState2.setTextColor(0x7200ff);
                                mTvState.setTextColor(0x7200ff);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_refund));
                            }
                            if (orderDetail.data.status == 7) {
                                mTvState.setText("待赎回");
                                mTvState2.setText("待赎回");
                                mTvState2.setTextColor(0xcead68);
                                mTvState.setTextColor(0xcead68);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_redemption));
                            }
                            if (orderDetail.data.status == 8) {
                                mTvState.setText("已赎回");
                                mTvState2.setText("已赎回");
                                mTvState2.setTextColor(0x088205);
                                mTvState.setTextColor(0x088205);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_redemptioned));
                            }
                            if (orderDetail.data.status == 9) {
                                mTvState.setText("已还款");
                                mTvState2.setText("已还款");
                                mTvState2.setTextColor(0x000000);
                                mTvState.setTextColor(0x000000);
                                mTvState2.setBackground(ActivityUtils.peek().getResources().getDrawable(R.drawable.fillet_cancel));
                            }
                        }

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 取消订单
     */
    private void cancel() {
        loadingDialog.setText("已取消");
        loadingDialog.show();
        TreeMap<String,String> params = new TreeMap<>();
        params.put("id", orderId+"");
        params.put("productId", productId+"");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(OrderDetailAct.this, BaseParams.CancelDetails,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {

                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        handler.sendEmptyMessageDelayed(1, 2500);
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }
}
