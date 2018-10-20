package com.zqzr.licaitong.ui.own;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:57
 * <p/>
 * Description:
 */

public class FindPwdFirstAct extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.act_own_findpwd_first);


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
}
