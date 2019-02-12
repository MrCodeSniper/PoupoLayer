package com.github.codesniper.poplayer.webview.service;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.webview.impl.HybirdImpl;
import com.github.codesniper.poplayer.webview.inter.HybirdManager;


/**
 * 提供app基础服务 包含原生和jsbrdge
 */
public class PopWebViewService extends Object{

    private PopWebView mWebView;

    private HybirdManager hybirdManager;

    public PopWebViewService() {
    }

    public PopWebViewService(PopWebView webView) {
        this.mWebView=webView;
    }

    public void setHybirdManager(HybirdManager hybirdManager) {
        this.hybirdManager = hybirdManager;
    }

    /**
     * 提供隐藏webview弹窗的服务 from native
     */
    @JavascriptInterface
    public void hidePopLayer() {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setVisibility(View.GONE);
                PopManager.getInstance(mWebView.getContext()).onPopDimiss();
                mWebView.stopLoading();
                mWebView.clearHistory();
                mWebView.clearCache(true);
                mWebView.loadUrl("about:blank");
                mWebView.pauseTimers();
                mWebView = null;
            }
        });
    }


    /**
     * 提供APP路由服务 from JsBridge
     */
    @JavascriptInterface
    public void route(String routePath){
        if(hybirdManager!=null) hybirdManager.invokeAppServices(routePath);
    }



    public void printService(String str){
        Log.e("PopWebViewService",str);
    }







}
