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
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Getcode;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.InputCheck;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SuccessAndFailDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:58
 * <p/>
 * Description:
 */

public class FindPwdSecondAct extends BaseActivity {
    private EditText mEtPwdOne, mEtPwdTwo;
    private TextView mTvFindPwd;
    private KeyDownLoadingDialog loadingDialog;
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
        setContentView(R.layout.act_own_findpwd_second);

        mEtPwdOne = (EditText) findViewById(R.id.findpwd_pw1);
        mEtPwdTwo = (EditText) findViewById(R.id.findpwd_pw2);
        mTvFindPwd = (TextView) findViewById(R.id.find_confirm);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("phone");

        loadingDialog = new KeyDownLoadingDialog(this);
        successDialog = new SuccessAndFailDialog(this);
        successDialog.setContent(Constant.FindPwd_Success, true);
        successDialog.setDes("即将跳转到登录页", true);
        successDialog.setImg(R.mipmap.success, true);

        mTvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(phone);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getIntExtra("turn", -1) == 1) {//修改登录密码
            setTitle(getResources().getString(R.string.own_security_changeLoginPwd));
        } else {//找回密码
            setTitle(getResources().getString(R.string.findpwd_title));
        }
        setBackOption(true);
    }

    /**
     * 注册
     *
     * @param phone
     */
    private void find(String phone) {
        //判断密码是否为空
        if (TextUtils.isEmpty(mEtPwdOne.getText().toString())) {
            Utils.toast(Constant.Login_PassWord_Null);
            return;
        }

        //判断密码是否符合规则
        if (!InputCheck.checkPwdRule(mEtPwdOne.getText().toString().trim())) {
            Utils.toast(Constant.PassWord_Tip);
            return;
        }

        if (TextUtils.isEmpty(mEtPwdTwo.getText().toString())) {
            Utils.toast(Constant.Regist_PassWordTwo_Null);
            return;
        }

        if (!TextUtils.equals(mEtPwdTwo.getText().toString().trim(), mEtPwdOne.getText().toString().trim())) {
            Utils.toast(Constant.Regist_PassWord_Dif);
            return;
        }

        loadingDialog.show();

        TreeMap<String, String> params = new TreeMap<>();
        params.put("phone", phone);
        params.put("password", MD5Util.getMD5Str(mEtPwdTwo.getText().toString().trim()));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.FindPwd, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.FindPwd_Success);
                        sendBroadcast(intent);

                        successDialog.show();
                        handler.sendEmptyMessageDelayed(Constant.NUMBER_0, 3000);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeMessages(Constant.NUMBER_0);
        successDialog.dismiss();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(Constant.NUMBER_0);
    }
}
