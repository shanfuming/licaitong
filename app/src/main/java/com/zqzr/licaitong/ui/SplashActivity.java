package com.zqzr.licaitong.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.utils.SPUtil;

import java.util.HashMap;
import java.util.UUID;

public class SplashActivity extends BaseActivity {

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 2000;

    private ImageView mSplashIv;
    private String UID = "";

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };
//    private AddInfoNetwork addInfoNetwork;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        mSplashIv = (ImageView) findViewById(R.id.splash_iv);

        boolean isFirstOpen = SPUtil.getBoolean( "isFirstOpen", true);

        if (!isFirstOpen) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }
    }

    @Override
    protected void initData() {
        String Metadata = getAppMetaData(this, "UMENG_CHANNEL");
        sendLoginInfo();
    }

    private void sendLoginInfo() {

//        if (!isFirstLogin()){
//            int userId = SPUtil.getInt(YiYuanDuoBaoApplication.sContext, "userId", -1);
//            String loginToken = SharePrefUtil.getString(YiYuanDuoBaoApplication.sContext, "loginToken", null);
//            String device_model = Build.MODEL; // 设备型号
//            String brand = Build.BRAND;//设备名称
//            String version_sdk = Build.VERSION.SDK; // 设备SDK版本
//            String version_release = Build.VERSION.RELEASE;
//            final TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//            final String tmDeviceId, tmSerial, tmPhone, androidId;
//            tmDeviceId = "" + tm.getDeviceId();
//            tmSerial = "" + tm.getSimSerialNumber();
//            androidId = "" + android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDeviceId.hashCode() << 32) | tmSerial.hashCode());
//            String uniqueId = deviceUuid.toString().toUpperCase();
//
//            HashMap<String, String> params = new HashMap<String, String>();
//            params.put("userid", String.valueOf(userId));
//            params.put("token", loginToken);
//            params.put("UUID", "");
//            params.put("IDFA", uniqueId);
//            params.put("Device", device_model);
//            params.put("OS", version_sdk);
//            params.put("Version", getVersionCode(SplashActivity.this)+"");
//            addInfoNetwork = new AddInfoNetwork(YiYuanDuoBaoApplication.sContext);
//            addInfoNetwork.sendPostRequest(SystemParams.AddInfo,params);
//        }
    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    /*
    * 获取application中指定的meta-data
    * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
    */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    "com.ydtx.yyqb", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /***
     * 获取版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(
                    "com.own.fastdb", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private boolean isFirstLogin() {
        return SPUtil.getBoolean("isfirstlogin", true);
    }

    @Override
    protected void onDestroy() {
//        this.finishBroadcastReceiver();
        super.onDestroy();
    }
}

