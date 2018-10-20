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
 * Time: 2018/10/19 19:28
 * <p/>
 * Description:
 */

public class IdentifyAct extends BaseActivity implements View.OnClickListener {
    private EditText mEtName,mEtIdCard,mEtAddress;
    private TextView mCommit;

    @Override
    protected void initView() {
        setContentView(R.layout.act_identify);

        mEtName = (EditText) findViewById(R.id.et_identify_name);
        mEtIdCard = (EditText) findViewById(R.id.et_identify_idcard);
        mEtAddress = (EditText) findViewById(R.id.et_identify_address);
        mCommit = (TextView) findViewById(R.id.tv_identify_commit);

        mCommit.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_personIdentify));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_identify_commit:
                commit();
                break;
        }
    }

    private void commit(){
        if (TextUtil.isEmpty(mEtName.getText().toString().trim())){
            Utils.toast("请输入真实姓名");
            return;
        }
        if (TextUtil.isEmpty(mEtIdCard.getText().toString().trim())){
            Utils.toast("请输入身份证号码");
            return;
        }
        if (TextUtil.isEmpty(mEtAddress.getText().toString().trim())){
            Utils.toast("请输入居住地址");
            return;
        }


    }
}
