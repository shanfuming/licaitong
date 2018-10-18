package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.RegularUtil;
import com.zqzr.licaitong.utils.TextUtil;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:56
 * <p/>
 * Description:
 */

public class LoginAct extends BaseActivity implements View.OnClickListener {
    private TextView mTvForgetpwd,mLogin,mTvTurnRegist;
    private ImageView mIvIsRight,mIvIsSee,mIvIsWrong;
    private EditText mUserName,mPassword;
    private boolean isSee = false;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_login);

        mUserName = (EditText) findViewById(R.id.login_username);
        mIvIsRight = (ImageView) findViewById(R.id.login_right);

        mPassword = (EditText) findViewById(R.id.login_pwd);
        mIvIsSee = (ImageView) findViewById(R.id.login_see);
        mIvIsWrong = (ImageView) findViewById(R.id.login_wrong);

        mTvForgetpwd = (TextView) findViewById(R.id.forgetpwd);
        mLogin = (TextView) findViewById(R.id.login);
        mTvTurnRegist = (TextView) findViewById(R.id.turn_regist);

        mIvIsSee.setOnClickListener(this);
        mIvIsWrong.setOnClickListener(this);
        mTvForgetpwd.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mTvTurnRegist.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (RegularUtil.isPhone(s.toString())){
                    mIvIsRight.setVisibility(View.VISIBLE);
                }else{
                    mIvIsRight.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_see:
                if (!isSee){ //如果是不能看到密码的情况下，
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isSee=true;
                }else { //如果是能看到密码的状态下
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isSee=false;
                }
                break;
            case R.id.login_wrong:
                mPassword.setText("");
                break;
            case R.id.forgetpwd:
                Intent forgetpwd = new Intent();
                if (RegularUtil.isPhone(mUserName.getText().toString())){
                    forgetpwd.putExtra("phone",mUserName.getText().toString());
                }
                ActivityUtils.push(FindPwdFirstAct.class,forgetpwd);
                break;
            case R.id.login:
                login();
                break;
            case R.id.turn_regist:
                Intent regist = new Intent();
                if (RegularUtil.isPhone(mUserName.getText().toString())){
                    regist.putExtra("phone",mUserName.getText().toString());
                }
                ActivityUtils.push(RegistFirstAct.class,regist);
                break;
        }
    }

    private void login() {

        //判断手机号是否符合
        if (!RegularUtil.isPhone(mUserName.getText().toString())){
            return;
        }
        //判断密码是否符合
        if (TextUtil.isEmpty(mUserName.getText().toString())){
            return;
        }

        TreeMap<String,String> params = new TreeMap<>();
        params.put("phone",mUserName.getText().toString());
        params.put("password",mPassword.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Do_Lgoin,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });


    }
}
