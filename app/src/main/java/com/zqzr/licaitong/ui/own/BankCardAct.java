package com.zqzr.licaitong.ui.own;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.utils.ActivityUtils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 14:02
 * <p/>
 * Description:
 */

public class BankCardAct extends BaseActivity {
    private MaterialRefreshLayout mRefreshLayout;
    private ListView mCardListView;
    private View footerView;
    private LinearLayout mLlAddCard;

    @Override
    protected void initView() {
        setContentView(R.layout.act_bankcard);

        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mCardListView = (ListView) findViewById(R.id.card_listview);
        footerView = View.inflate(this,R.layout.footer_card,null);
        mLlAddCard = (LinearLayout) footerView.findViewById(R.id.ll_addCard);
        mCardListView.addFooterView(footerView);
    }

    @Override
    protected void initData() {
        //请求银行卡接口
        mLlAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.push(AddCardAct.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_card_title));
        setBackOption(true);
    }
}
