package com.github.codesniper.poplayerview.custom;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.github.codesniper.poplayer.webview.client.PopWebViewClient;

/**
 * @Author：陈鸿 on 2019\2\3 0003 14:44
 * 邮箱：15168264355@163.com
 */
public class UserWebviewClient extends PopWebViewClient {


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        //TODO  invokeAppService
        return super.shouldInterceptRequest(webView, url);
    }
}
