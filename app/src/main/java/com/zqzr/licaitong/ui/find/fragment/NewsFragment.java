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

import java.util.ArrayList;
import java.util.List;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 18:11
 * <p/>
 * Description:
 */

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView mListView = new ListView(getActivity());
        mListView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        mListView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getData()));
        return mListView;
    }

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("我是第" + i + "条数据");
        }
        return data;
    }
}
