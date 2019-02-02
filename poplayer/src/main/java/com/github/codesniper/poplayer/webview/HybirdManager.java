package com.github.codesniper.poplayer.webview;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!

import android.webkit.WebView;

/**
 * 混合开发管理
 */
public interface HybirdManager {

    //注入JSBridge
    void injectJsBridge(WebView webView,String jsName);

    //调用本地提供的基础服务
    void invokeAppServices(String instruction) ;

    //加入JS中android本地对象
    void addUpJavaNativeJSInterface(WebView webView, String windowObjName);

}
