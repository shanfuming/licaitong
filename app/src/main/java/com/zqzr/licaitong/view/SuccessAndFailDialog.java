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
public class SuccessAndFailDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private TextView mContent,mDes,mNext;

    private String content,des,next;
    private int img,background;

    private ClickListener mClickListener;
    private ImageView mImg;

    private boolean isShowDes,isShowContent,isShowImg,isShowNext;

    public SuccessAndFailDialog(Context context) {
        super(context, R.style.calculate_dialog_style);
        this.mContext = context;
    }

    public SuccessAndFailDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected SuccessAndFailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        mNext = (TextView) findViewById(R.id.dialog_success_next);

        mNext.setOnClickListener(this);
    }

    private void setListener() {
    }

    private void initData() {
        if (des != null) {
            if (isShowDes){
                mDes.setVisibility(View.VISIBLE);
                mDes.setText(des);
            }else{
                mDes.setVisibility(View.GONE);
            }
        }
        if (content != null) {
            if (isShowContent){
                mContent.setVisibility(View.VISIBLE);
                mContent.setText(des);
            }else{
                mContent.setVisibility(View.GONE);
            }
        }
        if (img != 0) {
            if (isShowImg){
                mImg.setVisibility(View.VISIBLE);
                mImg.setImageDrawable(mContext.getResources().getDrawable(img));
            }else{
                mImg.setVisibility(View.GONE);
            }
        }

        if (next != null) {
            if (isShowContent){
                mNext.setVisibility(View.VISIBLE);
                mNext.setBackgroundResource(background);
                mNext.setText(next);
            }else{
                mNext.setVisibility(View.GONE);
            }
        }

    }

    public void setDes(String des,boolean isShowDes){
        this.des = des;
        this.isShowDes = isShowDes;
    }

    public void setContent(String content,boolean isShowContent){
        this.content = content;
        this.isShowContent = isShowContent;
    }

    public void setImg(int img,boolean isShowImg){
        this.img = img;
        this.isShowImg = isShowImg;
    }

    public void setNext(String next,boolean isShowNext,int background){
        this.next = next;
        this.isShowNext = isShowNext;
        this.background = background;

    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_success_next:
                mClickListener.onClickLook();
                break;
        }
    }
}