package com.zqzr.licaitong.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.common.info.ErrorInfo;
import com.zqzr.licaitong.utils.Logger;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/21 11:24
 * <p/>
 * Description: 网络变化监听广播
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        State wifiState;
        State mobileState;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState) {
            // 数据网络连接成功
            setNetworkState(context, Constant.NUMBER_1);
            Logger.i(TAG, "数据网络连接成功");
        } else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED != mobileState) {
            // 没有任何的网络
            setNetworkState(context, Constant.NUMBER_0);
            Logger.i(TAG, "没有任何的网络");
        } else if (wifiState != null && State.CONNECTED == wifiState) {
            // WIFI网络连接成功
            setNetworkState(context, Constant.NUMBER_2);
            Logger.i(TAG, "WIFI网络连接成功");
        }
    }

    /**
     * 设置和提示网络状态
     *
     * @param context
     *         上下文
     * @param type
     *         0 - 无网络
     *         1 - WIFI网络
     *         2 - 数据网络
     */
    private void setNetworkState(Context context, int type) {
        MyApplication.getInstance().networkType = type;
        switch (type) {
            case Constant.NUMBER_0:
                ErrorInfo.getInstance().addError(null, context.getString(R.string.app_network_error));
                break;

            case Constant.NUMBER_1:
            case Constant.NUMBER_2:
            default:
                ErrorInfo.getInstance().addError(null, context.getString(R.string.empty));
                break;
        }
    }
}