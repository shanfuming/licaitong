package com.zqzr.licaitong.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.R;

/**
 * Created by shanfuming on 2016/6/15.
 */
public class SuccessDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private TextView mContent,mDes;

    private String content,des;
    private int img;

    private ClickListener mClickListener;
    private ImageView mImg;

    public SuccessDialog(Context context) {
        super(context, R.style.calculate_dialog_style);
        this.mContext = context;
    }

    public SuccessDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected SuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public interface ClickListener {
        void onClickLook();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_success);

        initView();
        setListener();
        initData();
    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.dialog_success_img);
        mContent = (TextView) findViewById(R.id.dialog_success_content);
        mDes = (TextView) findViewById(R.id.dialog_success_des);
    }

    private void setListener() {
    }

    private void initData() {
        if (des != null) {
            mDes.setText(des);
        }
        if (content != null) {
            mContent.setText(content);
        }
        if (img != 0) {
            mImg.setImageDrawable(mContext.getResources().getDrawable(img));
        }
    }

    public void setDes(String des){
        this.des = des;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setImg(int img){
        this.img = img;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}