package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.os.CountDownTimer;
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
import com.zqzr.licaitong.utils.RegularUtil;
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

public class SecurityChangePhoneActS extends BaseActivity implements View.OnClickListener{

    private EditText mEtCode,mEtNewPhone;
    private TextView mTvCode, mTvCommit;
    private KeyDownLoadingDialog loadingDialog;
    private TimeCount time;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Constant.NUMBER_0:
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void initView() {
        setContentView(R.layout.act_changephone_s);

        mEtCode = (EditText) findViewById(R.id.et_change_code);
        mTvCode = (TextView) findViewById(R.id.tv_change_code);
        mTvCommit = (TextView) findViewById(R.id.tv_change_commit);
        mEtNewPhone = (EditText) findViewById(R.id.et_change_newPhone);

        mTvCode.setOnClickListener(this);
        mTvCommit.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_code:
                getCode();
                break;
            case R.id.tv_change_commit:
                setNewPhone();
                break;
        }
    }

    /**
     * 设置新的手机号
     */
    private void setNewPhone() {
        if (!RegularUtil.isPhone(mEtNewPhone.getText().toString())){
            Utils.toast("请正确输入手机号");
            return;
        }
        if (TextUtils.isEmpty(mEtCode.getText().toString().trim())){
            Utils.toast("验证码不能为空");
            return;
        }

        if (mEtCode.getText().toString().trim().length() > 6){
            Utils.toast("请正确输入验证码");
            return;
        }

        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", SPUtil.getString("userid",""));
        params.put("str", "new");
        params.put("code", mEtCode.getText().toString());
        params.put("phone", mEtNewPhone.getText().toString().trim());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ChangePhone_Next, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.ChangeP_Success);
                        sendBroadcast(intent);

                        Utils.toast(Constant.ChangeP_Success);

                        handler.sendEmptyMessageDelayed(Constant.NUMBER_0,1500);
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
     * 新手机发送验证码
     */
    private void getCode() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", SPUtil.getString("userid", ""));
        params.put("str", "old");
        params.put("phone", mEtNewPhone.getText().toString().trim());

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(Constant.NUMBER_0);
    }
}
