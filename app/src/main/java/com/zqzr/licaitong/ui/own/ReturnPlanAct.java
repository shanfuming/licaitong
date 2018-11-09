package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.ReturnPlanAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.ReturnPlan;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Logger;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 16:11
 * <p/>
 * Description:
 */

public class ReturnPlanAct extends BaseActivity implements View.OnClickListener {
    private TextView mTvReturnNot,mTvReturnEd;
    private ListView mReturnListIng,mReturnListed;
    private boolean isFirst = true;
    private int currentPage = 1;
    private int nextPage = 2;
    private KeyDownLoadingDialog loadingDialog;
    private MaterialRefreshLayout mRefreshIng,mRefreshEd;
    private int currentStatus = 0;

    private ArrayList<ReturnPlan.Data.CList> returnIngs = new ArrayList<>();
    private ArrayList<ReturnPlan.Data.CList> returnEds = new ArrayList<>();
    private ReturnPlanAdapter returnAdapterIngs, returnAdapterEds;

    @Override
    protected void initView() {
        setContentView(R.layout.act_returnplan);

        mTvReturnNot = (TextView) findViewById(R.id.tv_return_not);
        mTvReturnEd = (TextView) findViewById(R.id.tv_return_ed);
        mReturnListIng = (ListView) findViewById(R.id.returnPlan_listview_ing);
        mReturnListed = (ListView) findViewById(R.id.returnPlan_listview_ed);
        mRefreshIng = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout_ing);
        mRefreshEd = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout_ed);
        mRefreshIng.setLoadMore(true);
        mRefreshEd.setLoadMore(true);

        mTvReturnNot.setOnClickListener(this);
        mTvReturnEd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.show();
        getData(0,currentPage,false);
        getData(1,currentPage,false);

        returnAdapterIngs = new ReturnPlanAdapter(returnIngs);
        returnAdapterEds = new ReturnPlanAdapter(returnEds);
        mReturnListIng.setAdapter(returnAdapterIngs);
        mReturnListed.setAdapter(returnAdapterEds);

        mRefreshIng.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getData(currentStatus,currentPage,false);
                mRefreshIng.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getData(currentStatus,nextPage,true);
                nextPage = nextPage + 1;
                mRefreshIng.finishRefreshLoadMore();
            }
        });

        mRefreshEd.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getData(currentStatus,currentPage,false);
                mRefreshEd.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getData(currentStatus,nextPage,true);
                nextPage = nextPage + 1;
                mRefreshEd.finishRefreshLoadMore();
            }
        });

        mReturnListIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id",returnIngs.get(position).id+"");
                ActivityUtils.push(ReturnPlanDetailAct.class,intent);
            }
        });

        mReturnListed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id",returnEds.get(position).id+"");
                ActivityUtils.push(ReturnPlanDetailAct.class,intent);
            }
        });
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
                currentStatus = 0;
                mRefreshIng.setVisibility(View.VISIBLE);
                mReturnListIng.setVisibility(View.VISIBLE);
                mRefreshEd.setVisibility(View.GONE);
                mReturnListed.setVisibility(View.GONE);
                break;
            case R.id.tv_return_ed:
                isFirst = false;
                setBg(isFirst);
                currentStatus = 1;
                mRefreshIng.setVisibility(View.GONE);
                mReturnListIng.setVisibility(View.GONE);
                mRefreshEd.setVisibility(View.VISIBLE);
                mReturnListed.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setBg(boolean isFirst){
        if (isFirst){
            mTvReturnEd.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_diable_right);
            mTvReturnNot.setTextColor(getResources().getColor(R.color.white));
            mTvReturnNot.setBackgroundResource(R.drawable.fillet_btn_left);
        }else{
            mTvReturnEd.setTextColor(getResources().getColor(R.color.white));
            mTvReturnEd.setBackgroundResource(R.drawable.fillet_btn_right);
            mTvReturnNot.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvReturnNot.setBackgroundResource(R.drawable.fillet_btn_disable_left);
        }
    }

    /**
     * 获取回款计划
     * @param status
     */
    private void getData(final int status, int page, final boolean isLoad){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("status", status+"");
        params.put("pageNum", page+"");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ReturnMoneySchemes, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        ReturnPlan returnPlan = JsonUtil.parseJsonToBean(response.body(), ReturnPlan.class);
                        if (status == 0){
                            if (!isLoad){
                                returnIngs.clear();
                            }
                            returnIngs.addAll(returnPlan.data.cList);
                            returnAdapterIngs.notifyDataSetChanged();
                        }
                        if (status == 1){
                            if (!isLoad){
                                returnEds.clear();
                            }
                            returnEds.addAll(returnPlan.data.cList);
                            returnAdapterEds.notifyDataSetChanged();
                        }

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
