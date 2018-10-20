package com.zqzr.licaitong.ui.own;

import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/18 20:50
 * <p/>
 * Description:
 */

public class MoneyRecordAct extends BaseActivity {
    private MaterialRefreshLayout mRefreshlayout;
    private ListView moneyRecordListView;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_moneyrecord);

        mRefreshlayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        moneyRecordListView = (ListView) findViewById(R.id.moneyRecord_listview);


    }

    @Override
    protected void initData() {
        mRefreshlayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_moneyRecord));
        setBackOption(true);
    }
}
