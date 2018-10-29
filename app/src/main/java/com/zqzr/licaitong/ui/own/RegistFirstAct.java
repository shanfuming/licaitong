package com.zqzr.licaitong.ui.own;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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
 * Time: 2018/10/13 17:56
 * <p/>
 * Description:
 */

public class RegistFirstAct extends BaseActivity implements View.OnClickListener {
    private EditText mEtPhone, mEtInviteCode, mEtCode;
    private TextView mTvApplyPlanner, mTvTimebtn, mTvRule, mTvNext;
    private CheckBox mChectBox;
    private TimeCount time;
    private KeyDownLoadingDialog loadingDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_register_first);

        mEtPhone = (EditText) findViewById(R.id.register_phone);
        mEtInviteCode = (EditText) findViewById(R.id.register_inviter_code);
        mTvApplyPlanner = (TextView) findViewById(R.id.apply_planner);
        mEtCode = (EditText) findViewById(R.id.register_code);
        mTvTimebtn = (TextView) findViewById(R.id.register_timebtn);
        mTvRule = (TextView) findViewById(R.id.turn_rule);
        mTvNext = (TextView) findViewById(R.id.register_next);
        mChectBox = (CheckBox) findViewById(R.id.checkBox);

        mTvApplyPlanner.setOnClickListener(this);
        mTvTimebtn.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvRule.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        loadingDialog = new KeyDownLoadingDialog(this);

        registerBoradcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.register_title));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_planner:
                applyPlanner();
                break;
            case R.id.register_timebtn:
                getCode();
                break;
            case R.id.register_next:
                check();
                break;
            case R.id.turn_rule:

                break;
        }
    }

    /**
     * 申请理财师
     */
    private void applyPlanner() {
        ActivityUtils.push(ApplyPlannerAct.class);
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
        //判断邀请码是否为空
        if (TextUtils.isEmpty(mEtInviteCode.getText().toString())) {
            Utils.toast(Constant.Regist_InviteCode_Null);
            return;
        }
        //判断验证码是否为空
        if (TextUtils.isEmpty(mEtCode.getText().toString())) {
            Utils.toast(Constant.Code_Null);
            return;
        }

        mChectBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    next();
                } else {
                    Utils.toast("请先阅读并同意用户协议");
                }
            }
        });

    }

    /**
     * 注册下一步
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
                        intent.putExtra("fpName", mEtInviteCode.getText().toString());
                        ActivityUtils.push(RegistSecondAct.class, intent);
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
        params.put("fpName", mEtInviteCode.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Regist_Code, params);
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
            mTvTimebtn.setText("重获验证码");
            mTvTimebtn.setClickable(true);
            mTvTimebtn.setBackground(getResources().getDrawable(R.drawable.fillet_btn_normal));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mTvTimebtn.setClickable(false);
            mTvTimebtn.setText(millisUntilFinished / 1000 + "秒");
            mTvTimebtn.setBackground(getResources().getDrawable(R.drawable.fillet_btn_cancel));
        }
    }

    private BroadcastReceiver mRegistSucBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.Regist_Success)) {
                finish();
            }
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.Regist_Success);
        //注册广播
        registerReceiver(mRegistSucBroadcast, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRegistSucBroadcast);
    }
}
