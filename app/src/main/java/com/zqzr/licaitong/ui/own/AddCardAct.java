package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 14:29
 * <p/>
 * Description:
 */

public class AddCardAct extends BaseActivity {
    private TextView mUsername,mAddBind;
    private EditText mEtCardNum;

    @Override
    protected void initView() {
        setContentView(R.layout.act_addcard);

        mUsername = (TextView) findViewById(R.id.tv_card_username);
        mEtCardNum = (EditText) findViewById(R.id.et_addCardNum);
        mAddBind = (TextView) findViewById(R.id.tv_add_bind);

    }

    @Override
    protected void initData() {
        mAddBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBind();
            }
        });
    }

    /**
     * 绑定银行卡
     */
    private void cardBind() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_card_addcard));
        setBackOption(true);
    }
}
