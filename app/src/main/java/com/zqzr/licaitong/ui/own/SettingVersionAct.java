package com.zqzr.licaitong.ui.own;

import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 10:51
 * <p/>
 * Description:
 */

public class SettingVersionAct extends BaseActivity {
    private TextView mTvName,mTvTip;

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting_version);

        mTvName = (TextView) findViewById(R.id.tv_version_name);
        mTvTip = (TextView) findViewById(R.id.tv_version_tip);
    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_version));
        setBackOption(true);
    }
}
