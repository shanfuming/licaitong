package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.bean.Version;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Logger;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.UpdateProgressDialog;

import java.io.File;
import java.util.TreeMap;

import static android.R.attr.version;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 10:51
 * <p/>
 * Description:
 */

public class SettingVersionAct extends BaseActivity implements View.OnClickListener {
    private TextView mTvName,mTvTip;
    private KeyDownLoadingDialog loadingDialog;
    private ImageView mIvUpdate;
    private UpdateProgressDialog mDownloadDialog;
    private String updateUrl;

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载保存路径 */
    private String mSavePath;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk(mSavePath);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting_version);

        mTvName = (TextView) findViewById(R.id.tv_version_name);
        mTvTip = (TextView) findViewById(R.id.tv_version_tip);
        mIvUpdate = (ImageView) findViewById(R.id.iv_version_update);

        mIvUpdate.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        mTvName.setText("版本号 "+Utils.getVersionName());

        TreeMap<String,String> params = new TreeMap<>();
        params.put("v","0");
        params.put("userId", SPUtil.getString("userid",""));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Version,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Version version = JsonUtil.parseJsonToBean(response.body(), Version.class);
                    if (200 == Integer.parseInt(version.code)&&version.data!=null) {
                        if (Integer.valueOf(version.data.get(0).versionCode.replace(".","")) > Integer.valueOf(Utils.getVersionName().replace(".",""))){
                            mIvUpdate.setVisibility(View.VISIBLE);
                            mTvTip.setText(getResources().getString(R.string.own_version_tip1));
                        }else{
                            mIvUpdate.setVisibility(View.GONE);
                            mTvTip.setText(getResources().getString(R.string.own_version_tip2));
                        }
                        updateUrl = version.data.get(0).loadUrl;
                    } else {
                        Utils.toast(version.message);
                    }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_version));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_version_update:
                showDownloadDialog();
                break;
        }
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        mDownloadDialog = new UpdateProgressDialog(this);
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {

        OkGo.<File>get(updateUrl)//
                .tag(this)//
                .execute(new FileCallback("理财通" + version + ".apk") {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        Logger.e("test","正在下载中");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        Logger.e("test","下载完成"+response.body());
//                        mSavePath = BaseParams.ROOT_PATH+response.body().toString();
                        mSavePath = response.body().toString();
                    }

                    @Override
                    public void onError(Response<File> response) {
                        Logger.e("test","下载出错message="+response.message()+"----code="+response.code()+"----body="+response.body());
                        Utils.toast("下载错误,请重新尝试!");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        mDownloadDialog.setProgressUp((int)(progress.fraction * 100));
                        if (progress.currentSize == progress.totalSize) {
                            // 下载完成
                            mDownloadDialog.dismiss();
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        }
                    }
                });

    }

    /**
     * 安装APK文件
     */
    private void installApk(String mSavePath) {
        File apkfile = new File(mSavePath);
        if (!apkfile.exists()) {
            Utils.toast("下载错误,请重新尝试!");
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + mSavePath),
                "application/vnd.android.package-archive");
        this.startActivity(i);
    }
}
