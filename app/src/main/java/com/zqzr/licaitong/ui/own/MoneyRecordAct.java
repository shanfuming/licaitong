package com.zqzr.licaitong.ui.own;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.MoneyRecordAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.MoneyRecord;
import com.zqzr.licaitong.bean.ReturnPlan;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.ArrayList;
import java.util.TreeMap;

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
    private ArrayList<MoneyRecord.Data.CList> records = new ArrayList<>();
    private MoneyRecordAdapter moneyRecordAdapter;
    private KeyDownLoadingDialog loadingDialog;
    private int currentPage = 1;
    private int nextPage = 2;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_moneyrecord);

        mRefreshlayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mRefreshlayout.setLoadMore(true);
        moneyRecordListView = (ListView) findViewById(R.id.moneyRecord_listview);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        moneyRecordAdapter = new MoneyRecordAdapter(records);
        moneyRecordListView.setAdapter(moneyRecordAdapter);

        getData(currentPage, false);

        mRefreshlayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getData(currentPage, false);
                mRefreshlayout.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getData(nextPage, true);
                nextPage = nextPage + 1;
                mRefreshlayout.finishRefreshLoadMore();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_moneyRecord));
        setBackOption(true);
    }

    /**
     * 加载数据
     *
     * @param page
     * @param isLoad
     */
    private void getData(int page, final boolean isLoad) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("pageNum", page + "");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.MoneyRecord, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        MoneyRecord moneyRecord = JsonUtil.parseJsonToBean(response.body(), MoneyRecord.class);
                        if (!isLoad) {
                            records.clear();
                        }
                        records.addAll(moneyRecord.data.cList);
                        moneyRecordAdapter.notifyDataSetChanged();

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }
}
