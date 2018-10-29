package com.zqzr.licaitong.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.RecordAdapter;
import com.zqzr.licaitong.bean.Record;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 18:11
 * <p/>
 * Description:
 */

public class ProjectRecordFragment extends Fragment {
    private ListView mRecordListview;
    private ArrayList<Record> records = new ArrayList<>();
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
        records.add(new Record("李三","1000.00","13366230910"));
        records.add(new Record("张思","2000.00","13365730910"));
        records.add(new Record("王二麻子","3000.00","13366230450"));
        records.add(new Record("李三","4000.00","13366345910"));

        recordAdapter = new RecordAdapter(records);
        mRecordListview.setAdapter(recordAdapter);

    }
}
