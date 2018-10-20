package com.zqzr.licaitong.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.R;

/**
 * <br/>PROJECT_NAME : MinShengAgent
 * <br/>PACKAGE_NAME : com.minsheng.minshengagent.widgets
 * <br/>AUTHOR : Machao
 * <br/>DATA : 2016/guide_3/8 0008
 * <br/>TIME : 11:01
 * <br/>MSG :
 */
public class TipDialog extends Dialog implements OnClickListener {

    private Context mContext;

    private ImageView mIvImg;
    private TextView mTvTitle;
    private Button mBtnExit,mBtnConfirm;

    private String mTitle,mDes1,mDes2,mContent;
    private int mImgResource;

    private ClickListener mClickListener;
    private TextView mTvContent;
    private boolean isShow;

    public TipDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
    }

    public TipDialog(Context context, boolean isShow) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.isShow = isShow;
    }

    public TipDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected TipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public interface ClickListener {
        void onClickExit();
        void onClickConfirm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);

        initView();
        setListener();
        initData();
    }

    private void initView() {
        mIvImg = (ImageView) findViewById(R.id.imageView1);
        mTvTitle = (CenterTextView) findViewById(R.id.title);
        mTvContent = (TextView) findViewById(R.id.content);
        mBtnExit = (Button) findViewById(R.id.btn_exit_dialog);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm_dialog);
    }

    private void setListener() {
        mBtnExit.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    private void initData() {

        if (mImgResource != 0) {
            mIvImg.setImageDrawable(mContext.getResources().getDrawable(mImgResource));
        }

        if (mTitle != null) {
            mTvTitle.setText(mTitle);
        }

        if (mBtnExit != null) {
            mBtnExit.setText(mDes1);
        }
        if (mBtnConfirm != null) {
            mBtnConfirm.setText(mDes2);
        }

        if (mTvContent != null) {
            mTvContent.setText(mContent);
        }

        if (isShow){
            mTvContent.setVisibility(View.VISIBLE);
        }else{
            mTvContent.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setButtonDes(String des1,String des2){
        this.mDes1 = des1;
        this.mDes2 = des2;
    }
    public void setContent(String  content){
        this.mContent = content;
    }

    public void setImgResource(int imgResource){
        this.mImgResource = imgResource;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_dialog:
                if(mClickListener != null){
                    mClickListener.onClickExit();
                }
                break;

            case R.id.btn_confirm_dialog:
                if(mClickListener != null){
                    mClickListener.onClickConfirm();
                }
                break;
        }
    }
}
