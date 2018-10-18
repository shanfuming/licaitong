package com.zqzr.licaitong.ui.find.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zqzr.licaitong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/9.
 */
public class DynamicFragment extends Fragment {
    private MaterialRefreshLayout mRefreshLayout;
    private ListView mNewsListView;
    private int currentPage = 1;
    private int nextPage;

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
        mNewsListView = (ListView) view.findViewById(R.id.news_listview);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

            }
        });
    }

    public void getDataFromServer(int pagesize,int page,boolean loadmore){

    }
}
