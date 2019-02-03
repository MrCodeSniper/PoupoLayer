package com.github.codesniper.poplayer.webview.client;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.codesniper.poplayer.webview.inter.HybirdManager;


    public class PopWebViewClient extends WebViewClient{

    private HybirdManager mHybirdImpl;

    public void setListener(HybirdManager listener) {
        this.mHybirdImpl = listener;
    }


    /**
     * 为前端提供基础服务  PS： Android5.0以上才走
     */
    @TargetApi(21)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return shouldInterceptRequest(view, request.getUrl().toString());
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        if (url.contains("HRZ_QUEUE_HAS_MESSAGE_V1")) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                webView.loadUrl("javascript:__HRZCJSBridgeObject.drainMessageQueue()");
            } else {
                webView.evaluateJavascript("javascript:__HRZCJSBridgeObject.drainMessageQueue()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        String postJson = value.replaceAll("\\\\", "");
                        if (!TextUtils.isEmpty(postJson)) {
                            if (mHybirdImpl != null) {
                                mHybirdImpl.invokeAppServices(postJson);
                            }
                        }
                    }
                });
            }
            return null;
        }
        return shouldInterceptRequest(webView, url);
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //  super.onReceivedSslError(view, handler, error);
        handler.proceed();// 接受所有网站的证书
    }


}
