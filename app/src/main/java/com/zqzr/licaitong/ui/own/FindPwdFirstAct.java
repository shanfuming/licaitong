package com.zqzr.licaitong.ui.own;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
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
import com.zqzr.licaitong.utils.RegularUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:57
 * <p/>
 * Description:
 */

public class FindPwdFirstAct extends BaseActivity implements View.OnClickListener {
    private EditText mEtPhone,mEtCode;
    private TextView mTvTimeBtn,mTvFindNext;
    private TimeCount time;
    private KeyDownLoadingDialog loadingDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_findpwd_first);

        mEtPhone = (EditText) findViewById(R.id.findpwd_phone);
        mEtCode = (EditText) findViewById(R.id.findpwd_code);
        mTvTimeBtn = (TextView) findViewById(R.id.findpwd_timebtn);
        mTvFindNext = (TextView) findViewById(R.id.find_next);

        mTvTimeBtn.setOnClickListener(this);
        mTvFindNext.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getIntExtra("turn",-1) == 1){//修改登录密码
            setTitle(getResources().getString(R.string.own_security_changeLoginPwd));
        }else{//找回密码
            setTitle(getResources().getString(R.string.findpwd_title));
        }
        setBackOption(true);
    }

    @Override
    protected void initData() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        loadingDialog = new KeyDownLoadingDialog(this);

        registerBoradcastReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findpwd_timebtn:
                getCode();
                break;
            case R.id.find_next:
                check();
                break;
        }
    }

    /**
     * 检查各参数是否符合
     */
    private void check() {
        //判断手机号是否符合
        if (!RegularUtil.isPhone(mEtPhone.getText().toString())) {
            Utils.toast(Constant.Login_UserName_Null);
            return;
        }
        //判断验证码是否为空
        if (TextUtils.isEmpty(mEtCode.getText().toString())) {
            Utils.toast(Constant.Code_Null);
            return;
        }

        next();
    }

    /**
     * 找回密码下一步
     */
    private void next() {
        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("phone", mEtPhone.getText().toString());
        params.put("code", mEtCode.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Regist_Next, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        Intent intent = new Intent();
                        intent.putExtra("phone", mEtPhone.getText().toString());
                        intent.putExtra("turn", 2);
                        ActivityUtils.push(FindPwdSecondAct.class, intent);
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

    /**
     * 注册发送验证码
     */
    private void getCode() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("phone", mEtPhone.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.FindPwd_Code, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        Utils.toast("已发送");
                        //倒计时
                        time.start();//开始计时
                    } else {
                        Utils.toast(getcode.message);
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mTvTimeBtn.setText("重获验证码");
            mTvTimeBtn.setClickable(true);
            mTvTimeBtn.setBackground(getResources().getDrawable(R.drawable.fillet_btn_normal));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mTvTimeBtn.setClickable(false);
            mTvTimeBtn.setText(millisUntilFinished / 1000 + "秒");
            mTvTimeBtn.setBackground(getResources().getDrawable(R.drawable.fillet_btn_cancel));
        }
    }

    private BroadcastReceiver mFindPwdSucBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.FindPwd_Success)) {
                finish();
            }
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.FindPwd_Success);
        //注册广播
        registerReceiver(mFindPwdSucBroadcast, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFindPwdSucBroadcast);
    }
}
