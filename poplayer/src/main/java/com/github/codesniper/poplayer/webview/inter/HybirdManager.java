package com.github.codesniper.poplayer.webview.inter;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!

import android.webkit.WebView;

/**
 * 混合开发管理 让用户自己实现
 */
public interface HybirdManager {

    //注入JSBridge 时机在onreceivetitle
    void injectJsBridge(WebView webView,String jsName);

    //调用本地提供的基础服务 时机 1.jsprompt 2.post请求 3.原生 4.shouldoverridUrl
    void invokeAppServices(String instruction) ;

    //加入JS中android本地对象 时机webview初始化
    void addUpJavaNativeJSInterface(WebView webView, String windowObjName);

}
