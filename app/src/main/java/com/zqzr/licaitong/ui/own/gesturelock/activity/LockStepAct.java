package com.zqzr.licaitong.ui.own.gesturelock.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.ui.own.gesturelock.logic.LockLogic;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.lock.LockPatternView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:06
 * <p/>
 * Description: 手势密码设置界面
 */
public class LockStepAct extends BaseActivity implements LockPatternView.OnPatternListener {
    private static final String CONFIRM = "confirm";
    //	private static final int COLOR_TEXT_NORMAL = 0x9900A8FF;
    private TextView tv_hint;
    private TextView tv_again;
    private LockPatternView lockPatternView;
    private              ImageView[] pointers = new ImageView[9];
    // 开始设置
    private static final int         STEP_1   = 1;
    // 第一次设置手势完成
    private static final int         STEP_2   = 2;
    // 按下继续按钮
    private static final int         STEP_3   = 3;
    // 操作延时
    private static final int         TIME     = 1000;
    // 当前第几步
    private int step;
    private List<LockPatternView.Cell> choosePattern = new ArrayList<>();
    private boolean                    confirm       = false;
    private Handler mHandler      = new Handler();
    private LinearLayout lockSetupSmall;

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getString(R.string.lock_setup));
        setBackOption(false);
    }

    @Override
    protected void initView() {

        setContentView(R.layout.lock_step_act);
        tv_hint = (TextView) findViewById(R.id.lock_setup_hint);
        tv_again = (TextView) findViewById(R.id.lock_setup_again);
        lockSetupSmall = (LinearLayout) findViewById(R.id.lock_setup_small);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        tv_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = STEP_1;
                updateView();
            }
        });
        lockPatternView.setOnPatternListener(this);

        step = STEP_1;
        smallView();
        updateView();
    }

    private void updateView() {
        switch (step) {
            case STEP_1:
                choosePattern.clear();
                confirm = false;
                tv_hint.setText(R.string.lock_pattern_recording_incorrect_start);
                // tv_hint.setTextColor(COLOR_TEXT_NORMAL);
                tv_again.setVisibility(View.INVISIBLE);
                changeSmall();
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;

            case STEP_2:
                tv_hint.setText(R.string.lock_pattern_recording_incorrect_again);
                // tv_hint.setTextColor(COLOR_TEXT_NORMAL);
                tv_again.setVisibility(View.VISIBLE);
                changeSmall();
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;

            case STEP_3:
                if (confirm) {
                    // 保存密码到本地
                    LockLogic.getInstance().setPassword(LockPatternView.patternToString(choosePattern));
                    LockLogic.getInstance().reset();
                    Utils.toast(getString(R.string.lock_pattern_recording_incorrect_finish));
                    // Intent intent = new Intent();
                    // intent.putExtra(CONFIRM, confirm);
                    // setResult(RESULT_OK, intent);
                    this.finish();
                } else {
                    tv_hint.setText(R.string.lock_pattern_recording_incorrect_again_wrong);
                    tv_hint.setTextColor(Color.RED);
                    //tv_again.setVisibility(View.VISIBLE);
                    lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lockPatternView.clearPattern();
                            lockPatternView.enableInput();
                        }
                    }, TIME);
                }
                break;

            default:
                break;
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
            lockSetupSmall.addView(layout);
        }
    }

    private void changeSmall() {
        for (ImageView pointer : pointers) {
            pointer.setImageDrawable(getResources().getDrawable(R.mipmap.lock_small_default));
        }
        for (LockPatternView.Cell cell : choosePattern) {
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
        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE && step == STEP_1) {
            tv_hint.setText(getResources().getString(R.string.lock_pattern_recording_incorrect_too_short));
            tv_hint.setTextColor(Color.RED);
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            lockPatternView.clearPattern();
            return;
        }

        if (choosePattern.size() == 0) {
            choosePattern = new ArrayList<>(pattern);
            step = STEP_2;
            updateView();
            return;
        }
        confirm = choosePattern.equals(pattern);

        step = STEP_3;
        updateView();
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.onExit();
    }
}