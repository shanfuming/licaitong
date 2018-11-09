package com.zqzr.licaitong.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.ui.CommonWebviewAct;
import com.zqzr.licaitong.ui.home.TenderDetailAct;
import com.zqzr.licaitong.utils.ActivityUtils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 18:11
 * <p/>
 * Description:
 */

public class ProjectIntroduceBaoLiFragment extends Fragment {
    private RelativeLayout mRlLook;
    private String url;

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

    public void setUrl(String url){
        this.url = url;
    }

    private void look() {
        if (!TextUtils.isEmpty(url)){
            Intent intent = new Intent();
            intent.putExtra("title","预览协议");
            intent.putExtra("redirectUrl", url);
            ActivityUtils.push(CommonWebviewAct.class,intent);
        }
    }
}
