package com.zqzr.licaitong.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.ui.SplashActivity;
import com.zqzr.licaitong.utils.DensityUtils;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;

import java.util.Map;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/15 10:08
 * <p/>
 * Description:
 */

public class OKGO_GetData {

    /**
     * post请求
     *
     * 请求服务器获取数据
     *
     * 如有header参数在这里统一添加
     *
     * @param context
     * @param url 请求地址
     * @param parameters 请求参数
     * @return
     */
    public static PostRequest<String> getDatePost(Context context, String url, Map<String, String> parameters){
        PostRequest postRequest = (PostRequest) OkGo
                .<String>post(BaseParams.URL_ADDRESS+url)
                .tag(context)
                .params("v", Utils.getVersionName())
                .params("token", SPUtil.getString("token",""))
                .params("userId", SPUtil.getString("userid",""))
                .params("deviceCode", Utils.getIMEI())
                .params(parameters);
        return postRequest;
    }
    /**
     * get请求
     *
     * 请求服务器获取数据
     *
     * 如有header参数在这里统一添加
     *
     * @param context
     * @param url 请求地址
     * @param parameters 请求参数
     * @return
     */
    public static GetRequest<String> getDateGet(Context context, String url, Map<String, String> parameters){
        GetRequest postRequest = (GetRequest) OkGo
                .<String>get(url)
                .tag(context)
                .params("v", Utils.getVersionName())
                .params("token", SPUtil.getString("token",""))
                .params("id", SPUtil.getString("userid",""))
                .params("deviceCode", Utils.getIMEI())
                .params(parameters);
        return postRequest;
    }
}
