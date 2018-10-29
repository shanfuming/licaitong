package com.zqzr.licaitong.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zqzr.licaitong.R;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 18:11
 * <p/>
 * Description:
 */

public class ProjectIntroduceBaoLiFragment extends Fragment {
    private RelativeLayout mRlLook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_inbaoli, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlLook = (RelativeLayout) view.findViewById(R.id.rl_look);

        mRlLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                look();
            }
        });
    }

    private void look() {

    }
}
