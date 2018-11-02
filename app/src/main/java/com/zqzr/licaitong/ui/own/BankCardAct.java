package com.zqzr.licaitong.ui.own;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.BankCardsAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.BankCards;
import com.zqzr.licaitong.bean.Getcode;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.TipDialog;

import java.util.ArrayList;
import java.util.TreeMap;

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
    private KeyDownLoadingDialog loadingDialog;
    private ArrayList<BankCards.Data> bankCardses = new ArrayList<>();
    private BankCardsAdapter bankCardsAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.act_bankcard);

        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mCardListView = (ListView) findViewById(R.id.card_listview);
        footerView = View.inflate(this, R.layout.footer_card, null);
        mLlAddCard = (LinearLayout) footerView.findViewById(R.id.ll_addCard);
        mCardListView.addFooterView(footerView);
    }

    @Override
    protected void initData() {
        final String userRealName = getIntent().getStringExtra("userRealName");
        final String idNum = getIntent().getStringExtra("idNum");
        //请求银行卡接口
        mLlAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userRealName", userRealName);
                intent.putExtra("idNum", idNum);
                ActivityUtils.push(AddCardAct.class, intent);
            }
        });
        loadingDialog = new KeyDownLoadingDialog(this);
        bankCardsAdapter = new BankCardsAdapter(bankCardses);
        mCardListView.setAdapter(bankCardsAdapter);

        mCardListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final TipDialog tipDialog = new TipDialog(ActivityUtils.peek());
                tipDialog.setTitle("您确定要解绑此银行卡吗");
                tipDialog.setButtonDes("取消", "解绑");
                tipDialog.show();
                tipDialog.setClickListener(new TipDialog.ClickListener() {
                    @Override
                    public void onClickExit() {
                        tipDialog.dismiss();
                    }

                    @Override
                    public void onClickConfirm() {
                        unBindCard(position);
                    }
                });
                return false;
            }
        });

        getCardList();

        registerBoradcastReceiver();
    }

    /**
     * 解绑银行卡
     * @param position
     */
    private void unBindCard(int position) {
        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", bankCardses.get(position).id+"");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.UnBankCard, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode unbind = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(unbind.code)) {
                        getCardList();
                    } else {
                        Utils.toast(unbind.message);
                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 获取银行卡
     */
    private void getCardList() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("userId", SPUtil.getString("userid", ""));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.BankCardList, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    BankCards bankCards = JsonUtil.parseJsonToBean(response.body(), BankCards.class);
                    if (200 == Integer.parseInt(bankCards.code)) {
                        bankCardses.addAll(bankCards.data);
                        bankCardsAdapter.notifyDataSetChanged();
                    } else {
                        Utils.toast(bankCards.message);
                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_card_title));
        setBackOption(true);
    }

    private BroadcastReceiver mBindCardSucBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.BankCard_Success)) {
                getCardList();
            }
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.BankCard_Success);
        //注册广播
        registerReceiver(mBindCardSucBroadcast, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBindCardSucBroadcast);
    }
}
