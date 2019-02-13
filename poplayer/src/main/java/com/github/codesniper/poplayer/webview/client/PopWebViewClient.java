package com.github.codesniper.poplayer.webview.client;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.codesniper.poplayer.webview.inter.HybirdManager;

import static com.github.codesniper.poplayer.config.LayerConfig.POP_TAG;


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
    public WebResourceResponse shouldInterceptRequest(final WebView webView, String url) {
        //运行在子线程
        Log.d(POP_TAG,url);

        if(url.contains("__HRZ_QUEUE_HAS_MESSAGE_V1")){
            return null;
        }

        if (url.contains("hrz_client_msg")) {
            webView.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    //必须运行在主线程
                    webView.evaluateJavascript("javascript:__HRZCJSBridgeObject.drainMessageQueue()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d(POP_TAG,"onReceiveValue:"+value);
                            String postJson = value.replaceAll("\\\\", "");
                            if (!TextUtils.isEmpty(postJson)) {
                                if (mHybirdImpl != null) {
                                    mHybirdImpl.invokeAppServices(postJson);
                                }
                            }
                        }
                    });
                }
            });
            return null;
        }

        return shouldInterceptRequest(webView, url);
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //  super.onReceivedSslError(view, handler, error);
        handler.proceed();// 接受所有网站的证书
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }
}
