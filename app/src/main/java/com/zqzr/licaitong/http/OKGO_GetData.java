package com.zqzr.licaitong.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.base.BaseParams;

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
                .headers("systemCode", "Android-1.0.8")
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
                .headers("systemCode", "Android-1.0.8")
                .params(parameters);
        return postRequest;
    }
}
