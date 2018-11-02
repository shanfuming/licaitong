package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.MainActivity;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.RegularUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:56
 * <p/>
 * Description:
 */

public class LoginAct extends BaseActivity implements View.OnClickListener {
    private TextView mTvForgetpwd, mLogin, mTvTurnRegist;
    private ImageView mIvIsRight, mIvIsSee, mIvIsWrong;
    private EditText mUserName, mPassword;
    private boolean isSee = false;
    private KeyDownLoadingDialog loadingDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.NUMBER_1:
                    loadingDialog.setText("登录成功");
                    handler.sendEmptyMessageDelayed(Constant.NUMBER_2,1000);
                    break;
                case Constant.NUMBER_2:
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        setBackOption(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("turn",-1) == 1){
                    Intent intent = new Intent();
                    intent.putExtra("tab", 0);
                    ActivityUtils.push(MainActivity.class, intent);
                    finish();
                }else{
                    ActivityUtils.pop();
                }
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_login);

        mUserName = (EditText) findViewById(R.id.login_username);
        mIvIsRight = (ImageView) findViewById(R.id.login_right);

        mPassword = (EditText) findViewById(R.id.login_pwd);
        mIvIsSee = (ImageView) findViewById(R.id.login_see);
        mIvIsWrong = (ImageView) findViewById(R.id.login_wrong);

        mTvForgetpwd = (TextView) findViewById(R.id.forgetpwd);
        mLogin = (TextView) findViewById(R.id.loginout);
        mTvTurnRegist = (TextView) findViewById(R.id.turn_regist);

        mIvIsSee.setOnClickListener(this);
        mIvIsWrong.setOnClickListener(this);
        mTvForgetpwd.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mTvTurnRegist.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        loadingDialog = new KeyDownLoadingDialog(this);

        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (RegularUtil.isPhone(s.toString())) {
                    mIvIsRight.setVisibility(View.VISIBLE);
                } else {
                    mIvIsRight.setVisibility(View.GONE);
                }
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mIvIsWrong.setVisibility(View.VISIBLE);
                } else {
                    mIvIsWrong.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_see:
                if (!isSee) { //如果是不能看到密码的情况下，
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isSee = true;
                } else { //如果是能看到密码的状态下
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isSee = false;
                }
                break;
            case R.id.login_wrong:
                mPassword.setText("");
                break;
            case R.id.forgetpwd:
                Intent forgetpwd = new Intent();
                if (RegularUtil.isPhone(mUserName.getText().toString())) {
                    forgetpwd.putExtra("phone", mUserName.getText().toString());
                }
                ActivityUtils.push(FindPwdFirstAct.class, forgetpwd);
                break;
            case R.id.loginout:
                login();
                break;
            case R.id.turn_regist:
                Intent regist = new Intent();
                if (RegularUtil.isPhone(mUserName.getText().toString())) {
                    regist.putExtra("phone", mUserName.getText().toString());
                }
                ActivityUtils.push(RegistFirstAct.class, regist);
                break;
        }
    }

    private void login() {
        loadingDialog.setText("正在登录");
        loadingDialog.show();
        //判断手机号是否符合
        if (!RegularUtil.isPhone(mUserName.getText().toString())) {
            Utils.toast(Constant.Login_UserName_Null);
            return;
        }
        //判断密码是否符合
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            Utils.toast(Constant.Login_PassWord_Null);
            return;
        }

        TreeMap<String, String> params = new TreeMap<>();
        params.put("phone", mUserName.getText().toString());
        params.put("password", MD5Util.getMD5Str(mPassword.getText().toString()));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Do_Lgoin, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Login login = JsonUtil.parseJsonToBean(response.body(), Login.class);
                        MyApplication.getInstance().updataLand(true);
                        SPUtil.setValue("token", login.data.token);
                        SPUtil.setValue("userid", login.data.id+"");
                        SPUtil.setValue("username", login.data.phone);
                        SPUtil.setValue("usericon", login.data.headPortraitUrl);

                        handler.sendEmptyMessageDelayed(Constant.NUMBER_1, 1000);

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                        loadingDialog.dismiss();
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
