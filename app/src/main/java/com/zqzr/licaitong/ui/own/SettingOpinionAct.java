package com.zqzr.licaitong.ui.own;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 19:24
 * <p/>
 * Description:
 */

public class SettingOpinionAct extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.act_setting_opinion);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_opinion));
        setBackOption(true);
    }
}
