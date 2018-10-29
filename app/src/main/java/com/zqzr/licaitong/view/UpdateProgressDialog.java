package com.zqzr.licaitong.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zqzr.licaitong.R;

public class UpdateProgressDialog extends AlertDialog {

    private Context mContext;
    private LayoutInflater mInflater;
    private AnimationDrawable AniDraw;
    private TextView mTextTv;
    private ProgressBar loadingImage;
    private String mText;
    private ProgressBar updatePro;
    private TextView tvsize;

    public UpdateProgressDialog(Context context) {
        super(context);
        mContext = context;
    }

    public UpdateProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected UpdateProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

//    public static UpdateProgressDialog create(Context context){
//        return new UpdateProgressDialog(context, R.style.MyDialogStyle);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);

        initView();
    }

    private void initView() {
        updatePro = (ProgressBar) findViewById(R.id.update_progress);
        tvsize = (TextView) findViewById(R.id.tv_update);
    }

    public void setProgressUp(int progressUp){
        updatePro.setProgress(progressUp);
        tvsize.setText("下载进度 " + progressUp + "%");
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        return true;
    }
}