package com.zqzr.licaitong.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zqzr.licaitong.R;

public class KeyDownLoadingDialog extends Dialog {

    private Context mContext;
    private LayoutInflater mInflater;
    private AnimationDrawable AniDraw;
    private TextView mTextTv;
    private ProgressBar loadingImage;
    private String mText;

    public KeyDownLoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
        mContext = context;
        iniView();
    }

    public KeyDownLoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        iniView();
    }

    protected KeyDownLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        iniView();
    }

    public static KeyDownLoadingDialog create(Context context){
        return new KeyDownLoadingDialog(context, R.style.CustomDialog);
    }

    public void setText(String text){
        updateText(text);
    }

    public void updateText(String text){
        if(mTextTv != null){
            mTextTv.setGravity(Gravity.CENTER);
            mTextTv.setText(text);
        }
    }

    public void show(){
        if(!isShowing())
            super.show();
    }

    public void dismiss(){
        if(isShowing())
            super.dismiss();
    }

    @SuppressWarnings("ResourceType")
    private void iniView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.dialog_loading,null);
        loadingImage = (ProgressBar) view.findViewById(R.id.loading_pb);
        mTextTv = (TextView) view.findViewById(R.id.loading_text);
//        AniDraw = (AnimationDrawable)loadingImage.getBackground();
//        AniDraw.start();
        setContentView(view);
    }
}