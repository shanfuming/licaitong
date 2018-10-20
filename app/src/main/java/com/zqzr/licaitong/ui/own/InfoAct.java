package com.zqzr.licaitong.ui.own;

import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.view.CircleImageView;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 11:34
 * <p/>
 * Description:
 */

public class InfoAct extends BaseActivity {
    private CircleImageView mIvIcon;
    private TextView mTvTrueName,mTvPhone,mTvIDCardNum,mTvInviteCode;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_info);

        mIvIcon = (CircleImageView) findViewById(R.id.iv_info_icon);
        mTvTrueName = (TextView) findViewById(R.id.tv_info_trueName);
        mTvPhone = (TextView) findViewById(R.id.tv_info_phone);
        mTvIDCardNum = (TextView) findViewById(R.id.tv_info_IDcardNum);
        mTvInviteCode = (TextView) findViewById(R.id.tv_info_plannerInviteCode);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setBackOption(true);
        setTitle(getResources().getString(R.string.own_info));
    }
}
