package com.zqzr.licaitong.ui.own;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.TipDialog;

import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 19:24
 * <p/>
 * Description: 意见反馈
 */

public class SettingOpinionAct extends BaseActivity {
    private EditText mEtOpinion;
    private TextView mCommit;
    private KeyDownLoadingDialog loadingDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loadingDialog.dismiss();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting_opinion);

        mEtOpinion = (EditText) findViewById(R.id.et_opinion);
        mCommit = (TextView) findViewById(R.id.opinion_commit);

    }

    @Override
    protected void initData() {

        loadingDialog = new KeyDownLoadingDialog(this);

        if (mEtOpinion.getText().toString().trim().length() < 10){
            Utils.toast(getResources().getString(R.string.own_opinion_hint));
            return;
        }

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.setText("正在提交");
                TreeMap<String,String> params = new TreeMap<>();
                params.put("userId", SPUtil.getString("userid",""));
                params.put("opinion", mEtOpinion.getText().toString().trim());

                PostRequest<String> postRequest = OKGO_GetData.getDatePost(SettingOpinionAct.this, BaseParams.Opinion,params);
                postRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            loadingDialog.setText("反馈成功");
                            handler.sendEmptyMessageDelayed(1, 2500);
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
        });

    }

    /**
     * 退出二次确认
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showTip();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void showTip() {
        final TipDialog tipDialog = new TipDialog(this);
        tipDialog.setTitle("您确定要放弃此次编辑吗");
        tipDialog.setButtonDes("取消","放弃");
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                finish();
                tipDialog.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_opinion));
        setBackOption(true);
    }
}
