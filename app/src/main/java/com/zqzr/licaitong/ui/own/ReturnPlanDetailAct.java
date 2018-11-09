package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.ReturnDetail;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.CommonWebviewAct;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.DateUtil;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/11/5 16:35
 * <p/>
 * Description:
 */

public class ReturnPlanDetailAct extends BaseActivity {
    private TextView mTvName, mTvStatus, mTvExpectedYield, mTvStartDate, mTvEndDate, mTvIncome, mTvReturnAll, mTvActualAmount;
    private RelativeLayout mRlLook;
    private KeyDownLoadingDialog loadingDialog;
    private String url;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_returnplandetail);

        mTvName = (TextView) findViewById(R.id.tv_detail_name);
        mTvStatus = (TextView) findViewById(R.id.tv_detail_status);
        mTvExpectedYield = (TextView) findViewById(R.id.tv_detail_expectedYield);
        mTvStartDate = (TextView) findViewById(R.id.tv_detail_startDate);
        mTvEndDate = (TextView) findViewById(R.id.tv_detail_endStatus);
        mTvIncome = (TextView) findViewById(R.id.tv_detail_income);
        mTvReturnAll = (TextView) findViewById(R.id.tv_detail_returnAll);
        mRlLook = (RelativeLayout) findViewById(R.id.rl_look_rule);
        mTvActualAmount = (TextView) findViewById(R.id.tv_detail_actualAmount);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("计划详情");
        setBackOption(true);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.show();
        String id = getIntent().getStringExtra("id");
        getData(id);

        mRlLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url)){
                    Intent intent = new Intent();
                    intent.putExtra("title", "查看协议");
                    intent.putExtra("redirectUrl", url);
                    ActivityUtils.push(CommonWebviewAct.class,intent);
                }
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData(String id) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", id);

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.SchemesDetails, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        ReturnDetail returnDetail = JsonUtil.parseJsonToBean(response.body(), ReturnDetail.class);

                        mTvName.setText(returnDetail.data.borrowName);
                        mTvExpectedYield.setText(returnDetail.data.rateYear + "%");
                        mTvIncome.setText(Utils.getDouble2(returnDetail.data.repaidInterest));
                        mTvStartDate.setText(DateUtil.formatter(DateUtil.Format.DATE, returnDetail.data.addTime));
                        mTvEndDate.setText(DateUtil.formatter(DateUtil.Format.DATE, returnDetail.data.repaymentTime));
                        mTvReturnAll.setText(Utils.getDouble2(returnDetail.data.repaidCapital));
                        mTvActualAmount.setText(Utils.getDouble2(returnDetail.data.actualAmount));

                        if (returnDetail.data.status == 0) {
                            mTvStatus.setText("待回款");
                            mTvStatus.setTextColor(getResources().getColor(R.color.app_color_principal));
                        }
                        if (returnDetail.data.status == 1) {
                            mTvStatus.setText("已回款");
                            mTvStatus.setTextColor(getResources().getColor(R.color.app_color_principal));
                        }

                        url = returnDetail.data.url;

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
}
