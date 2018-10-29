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
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SuccessAndFailDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:57
 * <p/>
 * Description:
 */

public class RegistSecondAct extends BaseActivity {
    private TextView mTvRegist;
    private EditText mEtPwdOne,mEtPwdTwo;
    private KeyDownLoadingDialog loadingDialog;
    private SuccessAndFailDialog successDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Constant.NUMBER_0:
                    successDialog.dismiss();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.register_title));
        setBackOption(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_register_second);

        mEtPwdOne = (EditText) findViewById(R.id.register_pwd_first);
        mEtPwdTwo= (EditText) findViewById(R.id.register_pwd_two);
        mTvRegist = (TextView) findViewById(R.id.register_regist);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("phone");
        final String fpName = intent.getStringExtra("fpName");

        loadingDialog = new KeyDownLoadingDialog(this);
        successDialog = new SuccessAndFailDialog(this);
        successDialog.setContent(Constant.Regist_Success,true);
        successDialog.setDes("即将跳转到登录页",true);
        successDialog.setImg(R.mipmap.success,true);

        mTvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist(phone,fpName);
            }
        });
    }

    /**
     * 注册
     * @param phone
     * @param fpName
     */
    private void regist(String phone,String fpName){
        //判断密码是否为空
        if (TextUtils.isEmpty(mEtPwdOne.getText().toString())){
            Utils.toast(Constant.Login_PassWord_Null);
            return;
        }

        if (TextUtils.isEmpty(mEtPwdTwo.getText().toString())){
            Utils.toast(Constant.Regist_PassWordTwo_Null);
            return;
        }

        if (!TextUtils.equals(mEtPwdTwo.getText().toString().trim(),mEtPwdOne.getText().toString().trim())){
            Utils.toast(Constant.Regist_PassWord_Dif);
            return;
        }

        loadingDialog.show();

        TreeMap<String,String> params = new TreeMap<>();
        params.put("phone",phone);
        params.put("fpName",fpName);
        params.put("password", MD5Util.getMD5Str(mEtPwdTwo.getText().toString().trim()));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Regist,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {

                        Intent intent = new Intent();
                        intent.setAction(Constant.Regist_Success);
                        sendBroadcast(intent);

                        successDialog.show();
                        handler.sendEmptyMessageDelayed(Constant.NUMBER_0,3000);

                    } else {
                        Utils.toast(getcode.message);
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
