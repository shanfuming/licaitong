package com.zqzr.licaitong.ui;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cjj.MaterialRefreshLayout;
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
        webview.loadUrl(getIntent().getStringExtra("url"));
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(!CommonWebviewAct.this.isFinishing()){
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
