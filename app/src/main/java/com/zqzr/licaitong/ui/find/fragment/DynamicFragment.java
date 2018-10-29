package com.zqzr.licaitong.ui.find.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.FindItemAdapter;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/6/9.
 */
public class DynamicFragment extends Fragment {
    private MaterialRefreshLayout mRefreshLayout;
    private ListView mNewsListView;
    private ArrayList<FindItem.Data.CList> findItems = new ArrayList<>();
    private int currentPage = 1;
    private int nextPage = 2;
    private FindItemAdapter findItemAdapter;
    private KeyDownLoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.frag_news,null,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.mrl_refreshLayout);
        mRefreshLayout.setLoadMore(true);
        mNewsListView = (ListView) view.findViewById(R.id.news_listview);

        findItemAdapter = new FindItemAdapter(findItems);
        mNewsListView.setAdapter(findItemAdapter);

        getData(currentPage,false);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getData(currentPage,false);
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getData(nextPage,true);
                nextPage = nextPage+1;
                mRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    public void getData(int currentPage, final boolean isLoadmore) {
        loadingDialog = new KeyDownLoadingDialog(getActivity());
        TreeMap<String,String> params = new TreeMap<>();
        params.put("type","2");
        params.put("pageNum", currentPage+"");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(getActivity(), BaseParams.Monent,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    FindItem finditem = JsonUtil.parseJsonToBean(response.body(), FindItem.class);
                    if (200 == Integer.parseInt(finditem.code)) {
                        if (!isLoadmore){
                            findItems.clear();
                        }
                        findItems.addAll(finditem.data.cList);
                        findItemAdapter.notifyDataSetChanged();
                    } else {
                        Utils.toast(finditem.message);
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
