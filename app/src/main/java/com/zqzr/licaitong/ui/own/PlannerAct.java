package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.view.TipDialog;

import org.w3c.dom.Text;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 13:26
 * <p/>
 * Description:
 */

public class PlannerAct extends BaseActivity {
    private TextView mTvName,mTvPhone,mtvCommoit;

    @Override
    protected void initView() {
        setContentView(R.layout.act_planner);

        mTvName = (TextView) findViewById(R.id.tv_planner_name);
        mTvPhone = (TextView) findViewById(R.id.tv_planner_phone);
        mtvCommoit = (TextView) findViewById(R.id.tv_change_commit);

        mtvCommoit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_planner));
        setBackOption(true);
    }

    /**
     * 提示
     */
    private void tip(){
        final TipDialog tipDialog = new TipDialog(this,true);
        tipDialog.setTitle("温馨提示");
        tipDialog.setContent("电话联系理财师"+mTvName.getText().toString());
        tipDialog.setButtonDes("取消", "确定");
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                SettingAct.call(PlannerAct.this, mTvPhone.getText().toString());
                tipDialog.dismiss();
            }
        });
    }


}
