package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 16:11
 * <p/>
 * Description:
 */

public class ReturnPlanAct extends BaseActivity implements View.OnClickListener {
    private TextView mTvReturnNot,mTvReturnEd;
    private ListView mReturnListview;
    private boolean isFirst = true;

    @Override
    protected void initView() {
        setContentView(R.layout.act_returnplan);

        mTvReturnNot = (TextView) findViewById(R.id.tv_return_not);
        mTvReturnEd = (TextView) findViewById(R.id.tv_return_ed);
        mReturnListview = (ListView) findViewById(R.id.returnPlan_listview);

        mTvReturnNot.setOnClickListener(this);
        mTvReturnEd.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_returnPlan));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_return_not:
                isFirst = true;
                setBg(isFirst);
                break;
            case R.id.tv_return_ed:
                isFirst = false;
                setBg(isFirst);
                break;
        }
    }

    private void setBg(boolean isFirst){
        if (isFirst){
            mTvReturnEd.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_diable_right);
            mTvReturnEd.setTextColor(getResources().getColor(R.color.white));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_left);
        }else{
            mTvReturnEd.setTextColor(getResources().getColor(R.color.white));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_right);
            mTvReturnEd.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_disable_left);
        }
    }
}
