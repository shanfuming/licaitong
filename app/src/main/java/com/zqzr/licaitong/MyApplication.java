package com.zqzr.licaitong;

import android.app.Application;
import android.util.DisplayMetrics;

import com.zqzr.licaitong.receiver.GestureLockWatcher;

import java.util.Map;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/17 14:41
 * <p/>
 * Description: Application
 */
public class MyApplication extends Application {
    private static final String TAG          = "MyApplication";
    public               boolean isShowFriday = true;
    private String CLIENTID     = "";
    /**
     * application单例
     */
    private static MyApplication mInstance;
    /**
     * 用于存放获取验证的倒计时时间
     */
    public static Map<String, Long> map;
    /**
     * 屏幕的密度
     */
    public DisplayMetrics metrics;
    /**
     * 用户是否登录
     */
    private boolean isLand      = false;
    private boolean isHome      = false;
    /**
     * 0 - 无网络
     * 1 - WIFI网络
     * 2 - 数据网络
     */
    public  int     networkType = -1;
    /**
     * 用于监听APP是否到后台
     */
    public GestureLockWatcher watcher;

    /**
     * 单例模式中获取唯一的ZRJFApplication实例
     *
     * @return Application
     */
    public static MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public String getCLIENTID() {
        return CLIENTID;
    }

    public void setCLIENTID(String CLIENTID) {
        this.CLIENTID = CLIENTID;
    }

    /**
     * 更新登录状态
     * 必须在 SharedPreferences 之后
     * isLand  true 表示登录， false 表示未登录
     *
     * @param isLand
     */
    public void updataLand(boolean isLand) {
        setLand(isLand);
//        RDClient.getInstance().updataRetrofit();
    }

    private static String appServer;

    public static String getAppServer() {
        return appServer;
    }

    public void setAppServer(String appServer) {
        String lastString = appServer.substring(appServer.length() - 1);
        if (lastString.equals("/")) {
            appServer = appServer.substring(0, appServer.length() - 1);
        }
        this.appServer = appServer;
    }

    public boolean isLand() {
        return isLand;
    }

    private void setLand(boolean land) {
        isLand = land;
    }

    private String imageServer;

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // 为glide中使用的setTag设置特殊的ID用以标识，以防冲突。
//        ViewTarget.setTagId(R.id.glide_tag);
//        // 抓取异常LOG，保存在本地
////        CrashHandler.getInstance().init(this);
//        // APP启动是，测试网络连接是否异常
//        if (!Utils.isConnect(this)) {
//            ErrorInfo.getInstance().addError(null, getString(R.string.app_network_error));
//        }
//
//
//        // 监听APP是否到后台
//        watcher = new GestureLockWatcher(this);
//        watcher.setOnScreenPressedListener(new GestureLockWatcher.OnScreenPressedListener() {
//            @Override
//            public void onPressed() {
//                LockLogic.getInstance().start();
//            }
//        });
//        watcher.startWatch();
        initLocalData();
//        initUmengShare();
//        addPromotion();
//        MobclickAgent.setScenarioType(getInstance(), MobclickAgent.EScenarioType.E_UM_NORMAL);
//        PushManager.getInstance().initialize(getInstance(), MyPushService.class);
//        PushManager.getInstance().registerPushIntentService(getInstance(), IntentService.class);
//        KeyVo.initPost(MyApplication.this);
    }

    /**
     * 根据本地数据，进行初始化
     */
    private void initLocalData() {
//        if (SharedInfo.getInstance().getValue(OauthTokenMo.class) != null) {
            MyApplication.getInstance().isLand = true;
//        }
    }

    /**
     * 友盟分享初始化
     */
    private void initUmengShare() {
//        PlatformConfig.setSinaWeibo(BaseParams.APPKeySina, BaseParams.APPSecretSina);
//        PlatformConfig.setWeixin(BaseParams.APPKeyWX, BaseParams.APPSecretWx);
    }

    private void addPromotion() {
//        PromotionMo mo = new PromotionMo();
//        mo.setAppCode(DeviceInfoUtils.getAppMetaData(MyApplication.getInstance(), "UMENG_CHANNEL"));
//        mo.setAppVersion(DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
//        mo.setIdentifier(DeviceInfoUtils.getIdentifier(MyApplication.getInstance()));
//        mo.setModel(android.os.Build.MODEL);
//        mo.setOs("ANDROID");
//        mo.setOsVersion(android.os.Build.VERSION.RELEASE);
//        Call<HttpResult> call = RDClient.getService(CommonService.class).promotionAdd(mo);
//        call.enqueue(new RequestCallBack<HttpResult>() {
//            @Override
//            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
//
//            }
//        });
    }
}