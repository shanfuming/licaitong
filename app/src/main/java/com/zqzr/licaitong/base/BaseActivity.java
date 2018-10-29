package com.zqzr.licaitong.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;

import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.ui.activity.AActivity;
import com.zqzr.licaitong.ui.find.FindActivity;
import com.zqzr.licaitong.ui.home.HomeActivity;
import com.zqzr.licaitong.ui.own.OwnActivity;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.PermissionCheck;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 10:11
 * <p/>
 * Description: Activity基类
 */
public abstract class BaseActivity extends AppBarActivity {
    static final String TAG = "BaseActivity";

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.push(this);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_in_to_left);
        //        if (BuildConfig.DEBUG) {
        //            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        //                    .detectAll()
        //                    .penaltyLog()
        //                    .build());
        //
        //            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        //                    .detectAll()
        //                    .penaltyLog()
        //                    .penaltyDeathOnNetwork()
        //                    .build());
        //        }
        initView();
        initData();
    }

    protected abstract void initView();

    protected void initData(){}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this instanceof HomeActivity || this instanceof FindActivity || this instanceof AActivity || this instanceof OwnActivity) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        LockLogic.getInstance().checkLock(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.remove(this);
        //页面销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheck.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_from_left, R.anim.slide_out_to_right);
    }

    /**
     * 向Fragment 分发onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fm    = getSupportFragmentManager();
        int             index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}