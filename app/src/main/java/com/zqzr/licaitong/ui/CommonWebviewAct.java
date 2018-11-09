package com.zqzr.licaitong.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/17 20:08
 * <p/>
 * Description:
 */

public class CommonWebviewAct extends BaseActivity {
    private MaterialRefreshLayout mRefreshLayout;
    private WebView webview;
    private KeyDownLoadingDialog loadingDialog;
    private String url;
    private String content;

    @Override
    protected void initView() {
        setContentView(R.layout.act_webview);

        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        webview = (WebView) findViewById(R.id.webview);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getIntent().getStringExtra("title"));
        setBackOption(true);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        url = getIntent().getStringExtra("redirectUrl");
        content = getIntent().getStringExtra("content");
        if (TextUtils.isEmpty(url)) {
            if (!TextUtils.isEmpty(content)) {
                webview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
            }
        } else {
            webview.loadUrl(url);
        }
        webview.getSettings().setJavaScriptEnabled(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!CommonWebviewAct.this.isFinishing()) {
                    loadingDialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        };
        webview.setWebViewClient(webViewClient);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (TextUtils.isEmpty(url)){
                    if (!TextUtils.isEmpty(content)){
                        webview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                    }
                }else{
                    webview.loadUrl(url);
                }
                mRefreshLayout.finishRefreshing();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
