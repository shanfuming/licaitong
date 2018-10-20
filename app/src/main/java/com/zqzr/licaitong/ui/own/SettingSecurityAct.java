package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.utils.ActivityUtils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 17:49
 * <p/>
 * Description:
 */

public class SettingSecurityAct extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mRlLock,mRlChangeLoginPwd,mRlChangePhone;
    private ImageView mIvIsLock;
    private boolean isLock = true;

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting_security);

        mRlLock = (RelativeLayout) findViewById(R.id.rl_security_lock);
        mRlChangeLoginPwd = (RelativeLayout) findViewById(R.id.rl_security_changeLoginPwd);
        mRlChangePhone = (RelativeLayout) findViewById(R.id.rl_security_changePhone);
        mIvIsLock = (ImageView) findViewById(R.id.iv_isLock);

        mRlLock.setOnClickListener(this);
        mRlChangeLoginPwd.setOnClickListener(this);
        mRlChangePhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_security_lock:
                setIvLock(!isLock);
                //开启与关闭手势密码 // TODO: 2018/10/20  
                
                break;
            case R.id.rl_security_changeLoginPwd:
                if(MyApplication.getInstance().isLand()){
                    Intent intent = new Intent();
                    intent.putExtra("turn", Constant.NUMBER_1);
                    ActivityUtils.push(FindPwdFirstAct.class,intent);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_security_changePhone:

                break;
        }
    }

    /**
     * 设置
     */
    private void setIvLock(boolean isLock){
        if (isLock){
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.open));
        }else{
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.off));
        }
    }
}
