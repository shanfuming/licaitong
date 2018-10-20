package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.utils.RegularUtil;
import com.zqzr.licaitong.utils.TextUtil;
import com.zqzr.licaitong.utils.Utils;

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_code:

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
