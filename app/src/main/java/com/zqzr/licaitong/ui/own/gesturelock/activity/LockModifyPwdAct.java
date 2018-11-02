package com.zqzr.licaitong.ui.own.gesturelock.activity;

import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.ui.own.UserLogic;
import com.zqzr.licaitong.ui.own.gesturelock.logic.LockLogic;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.view.lock.LockPatternView;

import java.util.List;

/**
 * <p/>
 * Description: 手势密码修改页面
 */
public class LockModifyPwdAct extends BaseActivity implements LockPatternView.OnPatternListener {
    private List<LockPatternView.Cell> lockPatterns;
    private static final int         TIME     = 1000;
    private Handler mHandler = new Handler();
    private              ImageView[] pointers = new ImageView[9];
    private LinearLayout lockModifySmall;
    private LockPatternView lockPatternView;
    private TextView lockModifyHint;

    @Override
    protected void initView() {
        setContentView(R.layout.lock_modify_act);

        lockModifySmall = (LinearLayout) findViewById(R.id.lock_modify_small);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockModifyHint = (TextView) findViewById(R.id.lock_modify_hint);

        lockPatternView.setOnPatternListener(this);
        smallView();
        final String password = LockLogic.getInstance().getPassword();
        if (!TextUtils.isEmpty(password)) {
            lockPatterns = LockPatternView.stringToPattern(password);
        } else {
            LockLogic.getInstance().checkLock(this);
            this.finish();
        }
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
            lockModifySmall.addView(layout);
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
    protected void onStart() {
        super.onStart();
        setTitle(getString(R.string.lock_input_original_title));
//        setBackOption(false);
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
            lockModifyHint.setText(getResources().getString(R.string.lock_pattern_recording_incorrect_too_short));
            lockModifyHint.setTextColor(Color.RED);
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lockPatternView.clearPattern();
                    lockPatternView.enableInput();
                }
            }, TIME);
            return;
        }
        changeSmall(pattern);
        if (pattern.equals(lockPatterns)) {
            LockLogic.getInstance().reset();
            ActivityUtils.push(LockStepAct.class);
            this.finish();
        } else {
            LockLogic.getInstance().addErrInputCount();
            final int other = LockLogic.getInstance().getRemainErrInputCount();
            if (other > 0) {
                lockModifyHint.setText(getString(R.string.lock_pattern_error2, other));
                lockModifyHint.setTextColor(Color.RED);
                // 左右移动动画
                Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                lockModifyHint.startAnimation(shakeAnimation);
                lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lockPatternView.clearPattern();
                        lockPatternView.enableInput();
                    }
                }, TIME);
            } else {
                lockPatternView.clearPattern();
                UserLogic.signOutPwdTime(getResources().getString(R.string.lock_pattern_error3),"重新登录");
            }
        }
    }
}