package com.github.codesniper.poplayer.webview.impl;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


public class PopWebViewJsInterface extends Object{

    private WebView mWebView;

    public PopWebViewJsInterface(WebView webView) {
        this.mWebView=webView;
    }

    @JavascriptInterface
    public void hidePopLayer() {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setVisibility(View.GONE);
            }
        });
    }


}
