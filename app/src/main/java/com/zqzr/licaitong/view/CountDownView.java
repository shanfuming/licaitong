package com.zqzr.licaitong.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shanfuming on 2016/6/6.
 */
public class CountDownView extends LinearLayout {
    private TextView mMinite;
    private TextView mSecond;
    private long mtime;
    private Timer mTimer;
    private TimerTask mMyTimerTask;
    private TextView mTip;
    private LinearLayout mLlCountDown;
    private LinearLayout mLlHour;
    private TextView mHour;

    private class MyHandler extends Handler {
        private WeakReference<CountDownView> weakReference;

        public MyHandler(CountDownView countDownView) {
            weakReference = new WeakReference<CountDownView>(countDownView);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    onTick((Long)(msg.obj));
                    break;
                case 1:
                    onFinish();
                    break;
                case 2:
                    stopTimer();
                    mTip.setText("服务器异常");
                    break;
            }
        }
    }
    private Handler mHandler = new MyHandler(this);
    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("NewApi")
    public CountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 控件，初始化
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.countdowntime_view,this);

        mTip = (TextView) view.findViewById(R.id.tv_publish_tip);
        mLlCountDown = (LinearLayout) view.findViewById(R.id.ll_countDown);
        mLlHour = (LinearLayout) view.findViewById(R.id.ll_hour);
        mHour = (TextView) view.findViewById(R.id.tv_hour);
        mMinite = (TextView) view.findViewById(R.id.tv_minite);
        mSecond = (TextView) view.findViewById(R.id.tv_second);
    }
    /**
     * @param time
     */
    public void setTime(int time){
        this.mtime= time;
        stopTimer();
        StartCountDownTimer();
    }

    public void StartCountDownTimer(){
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);

    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mtime == -1){
                mHandler.sendEmptyMessage(2);
            }else
            if(mtime == 0){
                mHandler.sendEmptyMessage(1);
            }else {
                Message message = mHandler.obtainMessage();
                message.obj = mtime;
                message.what = 0;
                mHandler.sendMessage(message);
                mtime -= 1000;
            }
        }
    }

    public void onTick(long millisUntilFinished) {
        mTip.setVisibility(GONE);
        mLlCountDown.setVisibility(VISIBLE);
        int ss = 1000;
        int mi = ss * 60;
        long hour = millisUntilFinished / (mi * 60);
        long minute = (millisUntilFinished - hour * (mi * 60))/ mi;
        long second = (millisUntilFinished - hour * (mi * 60) - minute * mi) / ss;
        long milliSecond = millisUntilFinished - hour * (mi * 60) - minute * mi - second * ss;
        String srtHour = hour < 10 ? "0" + hour : "" + hour;//时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond >100 ? strMilliSecond.substring(0,strMilliSecond.length()-1) : (milliSecond == 100 ? "99" : strMilliSecond);
        if (hour > 0){
            mLlHour.setVisibility(VISIBLE);
            mHour.setText(srtHour);
        }else{
            mLlHour.setVisibility(GONE);
            mHour.setText("00");
        }
        mMinite.setText(strMinute);
        mSecond.setText(strSecond);
    }

    public  void  onFinish() {
        stopTimer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMinite.setText("00");
                mSecond.setText("00");
                mLlCountDown.setVisibility(GONE);
                mTip.setText("活动正式开始");
                mTip.setVisibility(VISIBLE);
            }
        }, 10);
    }

    private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }

        if(mMyTimerTask != null){
            mMyTimerTask.cancel();
            mMyTimerTask = null;
        }

        if (mHandler != null){
            mHandler.removeMessages(0);
            mHandler.removeMessages(1);
            mHandler.removeMessages(2);
        }
    }
}
