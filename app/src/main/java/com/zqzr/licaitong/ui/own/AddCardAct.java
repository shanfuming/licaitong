package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Getcode;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SuccessAndFailDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 14:29
 * <p/>
 * Description:
 */

public class AddCardAct extends BaseActivity {
    private TextView mUsername, mAddBind;
    private EditText mEtCardNum;
    private KeyDownLoadingDialog loadingDialog;
    private String idNum;
    private String userRealName;
    private SuccessAndFailDialog successDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constant.NUMBER_0:
                    successDialog.dismiss();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.act_addcard);

        mUsername = (TextView) findViewById(R.id.tv_card_username);
        mEtCardNum = (EditText) findViewById(R.id.et_addCardNum);
        mAddBind = (TextView) findViewById(R.id.tv_add_bind);

    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.setText("正在提交");
        successDialog = new SuccessAndFailDialog(this);
        mUsername.setText(getIntent().getStringExtra("userRealName"));
        idNum = getIntent().getStringExtra("idNum");
        userRealName = getIntent().getStringExtra("userRealName");
        mAddBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBind();
            }
        });
    }

    /**
     * 绑定银行卡
     */
    private void cardBind() {
        if (TextUtils.isEmpty(mEtCardNum.getText().toString().trim())) {
            Utils.toast(Constant.BankCard_Tip);
            return;
        }

        loadingDialog.show();
        mAddBind.setClickable(false);
        TreeMap<String, String> params = new TreeMap<>();
        params.put("name", userRealName);
        params.put("idNum", idNum);
        params.put("mobile", SPUtil.getString("username", ""));
        params.put("cardNo", mEtCardNum.getText().toString().trim());
        params.put("userId", SPUtil.getString("userid", ""));
        params.put("username", SPUtil.getString("username", ""));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.BindBankCard, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        successDialog.setContent("绑卡成功", true);
                        successDialog.setDes("", true);
                        successDialog.setImg(R.mipmap.success, true);
                        successDialog.show();

                        Intent intent = new Intent();
                        intent.setAction(Constant.BankCard_Success);
                        sendBroadcast(intent);

                        handler.sendEmptyMessageDelayed(Constant.NUMBER_0, 2000);

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
                mAddBind.setClickable(true);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
                mAddBind.setClickable(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_card_addcard));
        setBackOption(true);
    }
}
