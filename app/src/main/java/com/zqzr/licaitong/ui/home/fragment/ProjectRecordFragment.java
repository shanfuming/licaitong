package com.zqzr.licaitong.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.qiniu.android.http.Client;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.RecordAdapter;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.bean.SubscribeRecord;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 18:11
 * <p/>
 * Description:
 */

public class ProjectRecordFragment extends Fragment {
    private ListView mRecordListview;
    private ArrayList<SubscribeRecord.Data.CList> records = new ArrayList<>();
    private RecordAdapter recordAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_record, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecordListview = (ListView) view.findViewById(R.id.record_listview);
        View headview = View.inflate(getActivity(),R.layout.headview_record,null);
        mRecordListview.addHeaderView(headview);

        recordAdapter = new RecordAdapter(records);
        mRecordListview.setAdapter(recordAdapter);
    }

    public ProjectRecordFragment setProductId(String id ){
        getData(id);
        return this;
    }

    private void getData(String id){
        TreeMap<String,String> params = new TreeMap<>();
        params.put("productId",id);
        PostRequest<String> postRequest = OKGO_GetData.getDatePost(getActivity(), BaseParams.GetProductLog,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        SubscribeRecord subscribeRecord = JsonUtil.parseJsonToBean(response.body(), SubscribeRecord.class);
                        records.addAll(subscribeRecord.data.cList);
                        recordAdapter.notifyDataSetChanged();
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast("网络繁忙，请稍后再试!");
            }
        });
    }
}
