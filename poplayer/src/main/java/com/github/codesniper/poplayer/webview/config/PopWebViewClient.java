package com.github.codesniper.poplayer.webview.config;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.codesniper.poplayer.webview.HybirdManager;


public class PopWebViewClient extends WebViewClient{


    private HybirdManager listener;

    public void setListener(HybirdManager listener) {
        this.listener = listener;
    }

    private final String TAG=getClass().getSimpleName();

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(TAG,"webview start time:"+System.currentTimeMillis());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG,"webview end time:"+System.currentTimeMillis());
    }


    /**
     * 为前端提供基础服务  PS： Android5.0以上才走
     * @param view
     * @param request
     * @return
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
                            if (listener != null) {
                                listener.invokeAppServices(postJson);
                            }
                        }
                    }
                });
            }
            return null;
        }
        return shouldInterceptRequest(webView, url);
    }

    /**
     * 拦截web端的href.location跳转 进行重定向
     * @param view
     * @param request
     * @return
     */
    //TODO 对“location.href”限制 比如小米mix2,小米5等等
    //TODO 重定向个别机型不走 https://blog.csdn.net/Jack_chb/article/details/79509311
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.getUrl().toString().startsWith("xxxx")) {
            return  true;
        }
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("xxxxx")) {
            return  true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //  super.onReceivedSslError(view, handler, error);
        handler.proceed();// 接受所有网站的证书
    }


}
