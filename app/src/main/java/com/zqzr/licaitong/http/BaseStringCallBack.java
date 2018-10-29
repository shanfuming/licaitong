package com.zqzr.licaitong.http;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zqzr.licaitong.utils.Utils;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/25 11:17
 * <p/>
 * Description:
 */

public abstract class BaseStringCallBack extends StringCallback {

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        Utils.toast("网络繁忙，请稍后再试!");
    }
}
