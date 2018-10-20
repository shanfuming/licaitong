package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.utils.TextUtil;
import com.zqzr.licaitong.utils.Utils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 11:31
 * <p/>
 * Description:
 */

public class SecurityChangePhoneActF extends BaseActivity implements View.OnClickListener {
    private EditText mEtCode;
    private TextView mTvCode,mTvNext;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_code:

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
        if (TextUtil.isEmpty(mEtCode.getText().toString().trim())){
            Utils.toast("验证码不能为空");
            return;
        }

        if (mEtCode.getText().toString().trim().length() > 6){
            Utils.toast("请正确输入验证码");
            return;
        }


    }
}
