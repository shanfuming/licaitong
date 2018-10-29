package com.zqzr.licaitong.ui.own;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
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
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 11:31
 * <p/>
 * Description:
 */

public class SecurityChangePhoneActF extends BaseActivity implements View.OnClickListener {
    private EditText mEtCode;
    private TextView mTvCode, mTvNext;
    private TimeCount time;
    private KeyDownLoadingDialog loadingDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.act_changephone_f);

        mEtCode = (EditText) findViewById(R.id.et_change_code);
        mTvCode = (TextView) findViewById(R.id.tv_change_code);
        mTvNext = (TextView) findViewById(R.id.tv_change_next);

        mTvCode.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_security_changePhone));
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
        switch (v.getId()) {
            case R.id.tv_change_code:
                getCode();
                break;
            case R.id.tv_change_next:
                next();
                break;
        }
    }

    /**
     * 下一步
     */
    private void next() {
        if (TextUtils.isEmpty(mEtCode.getText().toString().trim())) {
            Utils.toast("验证码不能为空");
            return;
        }

        if (mEtCode.getText().toString().trim().length() > 6) {
            Utils.toast("请正确输入验证码");
            return;
        }

        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", SPUtil.getString("userid", ""));
        params.put("str", "old");
        params.put("code", mEtCode.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ChangePhone_Next, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        Intent intent = new Intent();
                        intent.putExtra("code", mEtCode.getText().toString());
                        ActivityUtils.push(SecurityChangePhoneActS.class, intent);
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
        params.put("id", SPUtil.getString("userid", ""));
        params.put("str", "old");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ChangePhone_Code, params);
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
            mTvCode.setText("重获验证码");
            mTvCode.setClickable(true);
            mTvCode.setBackground(getResources().getDrawable(R.drawable.fillet_btn_normal));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mTvCode.setClickable(false);
            mTvCode.setText(millisUntilFinished / 1000 + "秒");
            mTvCode.setBackground(getResources().getDrawable(R.drawable.fillet_btn_cancel));
        }
    }

    private BroadcastReceiver mChangeSucBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ChangeP_Success)) {
                finish();
            }
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.ChangeP_Success);
        //注册广播
        registerReceiver(mChangeSucBroadcast, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mChangeSucBroadcast);
    }
}
