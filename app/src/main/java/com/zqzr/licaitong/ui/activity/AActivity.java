package com.zqzr.licaitong.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.ActivitysAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Activitys;
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.DensityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 13:06
 * <p/>
 * Description:
 */

public class AActivity extends BaseActivity {
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mActRecyclerView;
    private ArrayList<Activitys.Data> activityses = new ArrayList<>();
    private ActivitysAdapter activitysAdapter;
    private KeyDownLoadingDialog loadingDialog;
    private int currentPage = 1;
    private int nextPage = 2;

    @Override
    protected void initView() {
        setContentView(R.layout.act_activity);

        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mActRecyclerView = (RecyclerView) findViewById(R.id.activity_recylerview);
        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(this);
        mActRecyclerView.setLayoutManager(mLayoutMgr);
//        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtils.dp2px(this, 0f);
        int topBottom = DensityUtils.dp2px(this, 12f);
        mActRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, 0));

        activitysAdapter = new ActivitysAdapter(this, activityses);
        mActRecyclerView.setAdapter(activitysAdapter);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.show();
        getActivitys(currentPage, false);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getActivitys(currentPage, false);
                mRefreshLayout.finishRefreshing();
            }

//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                super.onRefreshLoadMore(materialRefreshLayout);
//                getActivitys(nextPage,true);
//                nextPage = nextPage + 1;
//                mRefreshLayout.finishRefreshLoadMore();
//            }
        });
    }

    private void getActivitys(int pageNum, final boolean isLoad) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum + "");
        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Activity, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {

                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Activitys activitys = JsonUtil.parseJsonToBean(response.body(), Activitys.class);
                        if (!isLoad) {
                            activityses.clear();
                        }
                        activityses.addAll(activitys.data);
                        activitysAdapter.notifyDataSetChanged();

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

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.bottom_tv_act));
        setBackOption(true);
    }
}
