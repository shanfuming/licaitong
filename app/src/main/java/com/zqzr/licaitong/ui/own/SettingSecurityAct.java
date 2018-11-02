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
import com.zqzr.licaitong.utils.SPUtil;

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
    private boolean isLock;

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
        isLock = SPUtil.getBoolean("lockOff",false);
        if (!isLock){
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.open));
        }else{
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.off));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_security));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_security_lock:
                setIvLock(isLock);
                isLock = !isLock;
                //开启与关闭手势密码
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
                if(MyApplication.getInstance().isLand()){
                    ActivityUtils.push(SecurityChangePhoneActF.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
        }
    }

    /**
     * 设置
     */
    private void setIvLock(boolean isLock){
        if (isLock){
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.open));
            SPUtil.setValue("lockOff",false);
        }else{
            mIvIsLock.setImageDrawable(getResources().getDrawable(R.mipmap.off));
            SPUtil.setValue("lockOff",true);
        }
    }
}
