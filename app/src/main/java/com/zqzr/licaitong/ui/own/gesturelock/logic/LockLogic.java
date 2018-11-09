package com.zqzr.licaitong.ui.own.gesturelock.logic;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.ui.GuideActivity;
import com.zqzr.licaitong.ui.SplashActivity;
import com.zqzr.licaitong.ui.activity.AActivity;
import com.zqzr.licaitong.ui.find.FindActivity;
import com.zqzr.licaitong.ui.home.HomeActivity;
import com.zqzr.licaitong.ui.own.LoginAct;
import com.zqzr.licaitong.ui.own.RegistFirstAct;
import com.zqzr.licaitong.ui.own.RegistSecondAct;
import com.zqzr.licaitong.ui.own.gesturelock.activity.LockAct;
import com.zqzr.licaitong.ui.own.gesturelock.activity.LockModifyPwdAct;
import com.zqzr.licaitong.ui.own.gesturelock.activity.LockStepAct;
import com.zqzr.licaitong.utils.Logger;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.view.lock.FingerPasswordBean;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:10
 * <p/>
 * Description: 手势密码管理类,用来指示 当一个Activity Resume时, 是否要跳转到解锁界面
 */
public final class LockLogic {
    private static final String TAG                 = "LockLogic";
    /** 锁屏 或者 返回Home界面 超过多少时间后, 出现手势解锁界面 */
    private final static long                           MILLS_WAIT_LOCK     = 5 * 1000;
    /** 锁屏 或者 按了 Home 按键的时间 */
    private              long                           millsStartTime      = -1L;
    /** 忽略的Activity */
    private final Set<Class<? extends Activity>> ignoreActivities    = new HashSet<>();
    /** 输入的错误次数 */
    private              int                            errInputCount       = 0;
    /** 最大输入错误次数 */
    private final static int                            MAX_ERR_INPUT_COUNT = 5;
    /** 是否是第一次进入 */
    private              boolean                        isFirstIn           = true;

    /**
     * 初始化需要忽略的Activity
     */
    private LockLogic() {
        clearIgnoreActivities();
        // 手势密码解锁页
        registerIgnoreActivity(LockAct.class);
        // 手势密码设置页
        registerIgnoreActivity(LockStepAct.class);
        // 手势密码修改页
        registerIgnoreActivity(LockModifyPwdAct.class);
        // 登录页
        registerIgnoreActivity(LoginAct.class);
        // 注册页
        registerIgnoreActivity(RegistFirstAct.class);
        registerIgnoreActivity(RegistSecondAct.class);
        // 引导页
        registerIgnoreActivity(SplashActivity.class);
        registerIgnoreActivity(GuideActivity.class);
    }

    public static LockLogic getInstance() {
        return LockPresenterInstance.instance;
    }

    private static class LockPresenterInstance {
        static LockLogic instance = new LockLogic();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 华丽分割
    //////////////////////////////////////////////////////////////////////////

    /***
     * 检测当前情况下 是否要跳转到手势界面
     */
    public boolean checkLock(final Activity activity) {
        boolean isOpen = isFingerPasswordOpened();
        Logger.d(TAG, "isFingerPasswordOpened = " + isOpen);
        if (isOpen) {
            if (!LockLogic.getInstance().isIgnoreActivity(activity) && (isFirstIn() || LockLogic.getInstance().isElapsedEnough())) {
                isFirstIn = false;
                Logger.i(TAG, "from " + activity.getClass().getSimpleName());
                activity.startActivity(new Intent(activity, LockAct.class));
                return true;
            }
            millsStartTime = -1L;
        } else {
            // 用户已登录，却没有开启手势密码，则到手势密码设置页面
            if (MyApplication.getInstance().isLand()) {
                if (!LockLogic.getInstance().isIgnoreActivity(activity)) {
                    isFirstIn = false;
                    activity.startActivity(new Intent(activity, LockStepAct.class));
                }
            }
        }
        return false;
    }

    /**
     * 重置状态
     * E.G. 解锁次数、APP到后台时间 等
     */
    public void reset() {
        millsStartTime = -1L;
        errInputCount = 0;
        FingerPasswordBean bean = getEntity();
        if (bean != null) {
            bean.setErrInputCount(errInputCount);
            saveEntity(bean);
        }
    }

    /**
     * APP到后台,记录时间
     */
    public void start() {
        if (millsStartTime < 0) {
            millsStartTime = System.currentTimeMillis();
        }
    }

    /**
     * APP在后台运行的时间，是否小于无需解锁的最小时间
     */
    public boolean isElapsedEnough() {
        return millsStartTime > 0 && System.currentTimeMillis() > (millsStartTime + MILLS_WAIT_LOCK);
    }

    /**
     * 保存解锁错误次数
     */
    public void addErrInputCount() {
        FingerPasswordBean bean = getEntity();
        Logger.i(TAG, "errInputCount:" + bean.getErrInputCount());
        this.errInputCount = Math.min(bean.getErrInputCount() + 1, MAX_ERR_INPUT_COUNT);
        bean.setErrInputCount(errInputCount);
        saveEntity(bean);
    }

    /**
     * 重置解锁错误次数
     */
    public void resetErrInputCount() {
        this.errInputCount = 0;
        FingerPasswordBean bean = getEntity();
        bean.setErrInputCount(0);
        saveEntity(bean);
    }

    /**
     * 获取还剩余的可解锁的次数
     */
    public int getRemainErrInputCount() {
        return Math.max(MAX_ERR_INPUT_COUNT - this.errInputCount, 0);
    }

    /**
     * 获取手势密码
     */
    public String getPassword() {
        final FingerPasswordBean bean         = getEntity();
        if (bean != null) {
            Logger.i("LockLogic", "errInputCount:" + bean.getErrInputCount());
            return bean.getPassword();
        }
        return null;
    }

    /**
     * 保存手势密码
     */
    public void setPassword(final String password) {
        if (!TextUtils.isEmpty(password)) {
            FingerPasswordBean bean = new FingerPasswordBean();
            bean.setPassword(password);
            saveEntity(bean);
        } else {
            SPUtil.remove(FingerPasswordBean.class, SPUtil.getString("userid",""));
        }
    }

    /**
     * 当前手势密码是否打开
     */
    private boolean isFingerPasswordOpened() {
        final FingerPasswordBean bean = getEntity();
        return bean != null && !TextUtils.isEmpty(bean.getPassword());
    }

    /**
     * 清空忽略列表
     */
    private void clearIgnoreActivities() {
        ignoreActivities.clear();
    }

    /**
     * 注册忽略的Activity
     */
    private void registerIgnoreActivity(Class<? extends Activity> clazz) {
        ignoreActivities.add(clazz);
    }

    /**
     * 判断activity是否在忽略列表内
     */
    private boolean isIgnoreActivity(Activity activity) {
        return activity != null && ignoreActivities.contains(activity.getClass());
    }

    public boolean isFirstIn() {
        return isFirstIn;
    }

    public void setFirstIn(boolean firstIn) {
        isFirstIn = firstIn;
    }

    /**
     * 加密保存手势密码数据
     */
    private void saveEntity(FingerPasswordBean bean) {
        String userId = SPUtil.getString("userid","");
        if (bean != null&&!TextUtils.isEmpty(userId)) {
            SPUtil.setEntity(bean, userId, true);
        }
    }

    /**
     * 获取加密保存的手势密码数据类
     */
    private FingerPasswordBean getEntity() {
        String userId = SPUtil.getString("userid","");
        if (TextUtils.isEmpty(userId)) {
            return new FingerPasswordBean();
        }
        FingerPasswordBean bean = SPUtil.getEntity(FingerPasswordBean.class, userId, null, true);
        if (null == bean) {
            bean = new FingerPasswordBean();
        }
        return bean;
    }
}