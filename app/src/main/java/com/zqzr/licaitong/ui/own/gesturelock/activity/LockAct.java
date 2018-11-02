package com.zqzr.licaitong.ui.own.gesturelock.activity;

import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.common.info.ErrorInfo;
import com.zqzr.licaitong.common.info.SharedInfo;
import com.zqzr.licaitong.ui.own.UserLogic;
import com.zqzr.licaitong.ui.own.gesturelock.logic.LockLogic;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.lock.LockPatternView;

import java.util.List;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:02
 * <p/>
 * Description: 手势密码登录页面
 */
public class LockAct extends BaseActivity implements LockPatternView.OnPatternListener {
    private List<LockPatternView.Cell> lockPatterns;
    private              ImageView[] pointers = new ImageView[9];
    private static final int         TIME     = 1000;
    private Handler mHandler = new Handler();
    private LinearLayout lockSmall;
    private TextView lockHint,lockForgot,lockVisitor;
    private LockPatternView lockPattern;

    @Override
    protected void initView() {
        setContentView(R.layout.lock_act);

        lockSmall = (LinearLayout) findViewById(R.id.lock_small);
        lockHint = (TextView) findViewById(R.id.lock_hint);
        lockPattern = (LockPatternView) findViewById(R.id.lock_pattern);
        lockForgot = (TextView) findViewById(R.id.lock_forgot);
        lockVisitor = (TextView) findViewById(R.id.lock_visitor);

        smallView();
        lockPattern.setOnPatternListener(this);
        final String password = LockLogic.getInstance().getPassword();
        if (!TextUtils.isEmpty(password)) {
            lockPatterns = LockPatternView.stringToPattern(password);
        } else {
            LockLogic.getInstance().checkLock(this);
            this.finish();
        }
    }

    @Override
    protected void initData() {
        lockForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogic.signOutToLogin(getResources().getString(R.string.lock_forgot_prompt),"重新登录");
            }
        });

        lockVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogic.signOutToLogin(getResources().getString(R.string.lock_visitor_prompt),"重新登录");
            }
        });
    }

    private void smallView() {
        for (int i = 0; i < 3; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                ImageView pointer = new ImageView(this);
                pointer.setScaleType(ImageView.ScaleType.CENTER);
                pointer.setPadding(4, 4, 4, 4);
                pointer.setImageDrawable(getResources().getDrawable(R.mipmap.lock_small_default));
                layout.addView(pointer);
                pointers[i * 3 + j] = pointer;
            }
            lockSmall.addView(layout);
        }
    }

    private void changeSmall(List<LockPatternView.Cell> pattern) {
        for (ImageView pointer : pointers) {
            pointer.setImageDrawable(getResources().getDrawable(R.mipmap.lock_small_default));
        }
        for (LockPatternView.Cell cell : pattern) {
            pointers[cell.getRow() * 3 + cell.getColumn()].setImageDrawable(getResources().getDrawable(R.mipmap.lock_small_blue));
        }
    }

    @Override
    public void onPatternStart() {

    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            lockHint.setText(getResources().getString(R.string.lock_pattern_recording_incorrect_too_short));
            lockHint.setTextColor(Color.RED);
            lockPattern.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lockPattern.clearPattern();
                    lockPattern.enableInput();
                }
            }, TIME);
            return;
        }
        changeSmall(pattern);
        if (pattern.equals(lockPatterns)) {
            refreshToken();
        } else {
            LockLogic.getInstance().addErrInputCount();
            final int other = LockLogic.getInstance().getRemainErrInputCount();
            if (other > 0) {
               lockHint.setText(getString(R.string.lock_pattern_error2, other));
               lockHint.setTextColor(Color.RED);
                // 左右移动动画
                Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                lockHint.startAnimation(shakeAnimation);
                lockPattern.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lockPattern.clearPattern();
                        lockPattern.enableInput();
                    }
                }, TIME);
            } else {
                lockPattern.clearPattern();
                UserLogic.signOutPwdTime(getResources().getString(R.string.lock_pattern_error3),"重新登录");
            }
        }
    }

    private void refreshToken() {
        lockPattern.clearPattern();
        LockLogic.getInstance().reset();
        if (Utils.isConnect(this)){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.onExit();
    }
}