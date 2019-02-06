package com.github.codesniper.poplayer.webview.inter;

import android.webkit.WebView;

import com.github.codesniper.poplayer.custom.PopWebView;

/**
 * @Author：陈鸿 on 2019\2\2 0002 21:19
 * 邮箱：15168264355@163.com
 */
public interface WebviewConfig {
    //初始化webview和加载的url
    void setUpWebConfig(PopWebView webView, String showScheme);
}
