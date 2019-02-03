package com.github.codesniper.poplayer.webview.impl;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.github.codesniper.poplayer.webview.inter.HybirdManager;
import com.github.codesniper.poplayer.webview.inter.WebviewConfig;
import com.github.codesniper.poplayer.webview.client.PopWebViewChromeClient;
import com.github.codesniper.poplayer.webview.client.PopWebViewClient;
import com.github.codesniper.poplayer.webview.config.PopWebViewSecurity;

import static com.github.codesniper.poplayer.util.PopUtils.initFilePermission;
import static com.github.codesniper.poplayer.util.PopUtils.isAppInDebug;


public  class WebConfigImpl implements WebviewConfig {


    private HybirdManager hybirdManager;

    private String jsBridgeFileName;

    private String jsObjectName;

    public WebConfigImpl(String jsBridgeFileName, String jsObjectName) {
        this.jsBridgeFileName = jsBridgeFileName;
        this.jsObjectName = jsObjectName;
    }


    ///////////////////////////////////////配置WEBVIEW 待后续继续优化 参考网易考拉团队webview优化////////////////////////////////

    @Override
    public void setUpWebConfig(WebView webView,String showScheme) {

        //开启debug模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isAppInDebug(webView.getContext())) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        initFilePermission(webView,true);

        /** webviewchromeclient **/
        webView.setWebChromeClient(new PopWebViewChromeClient(jsBridgeFileName));

        /** webviewclient **/
        PopWebViewClient popWebViewClient=new PopWebViewClient();
        if(hybirdManager!=null){
            popWebViewClient.setListener(hybirdManager);
        }
        webView.setWebViewClient(popWebViewClient);


        WebSettings settings = webView.getSettings();
        initSetting(settings,webView.getContext());
        //移除部分系统JavaScript接口 涉及安全问题
        PopWebViewSecurity.removeJavascriptInterfaces(webView);

        //设置背景透明
        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);

        //加载url
        webView.loadUrl(showScheme);

        if(hybirdManager!=null){
            hybirdManager.addUpJavaNativeJSInterface(webView,jsObjectName);
        }
    }


    /**
     * 默认webview设置
     * @param settings
     */
    private void initSetting(WebSettings settings, Context context){
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        //允许js代码 在Android 4.3版本调用WebSettings.setJavaScriptEnabled()方法时会调用一下reload方法，同时会回调多次WebChromeClient.onJsPrompt()
        settings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        settings.setDomStorageEnabled(true);
        //禁用放缩
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);
        //禁用文字缩放
        settings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        settings.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置 缓存位置由用户指定
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(context.getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        settings.setAllowFileAccess(true);
        //不保存密码
        settings.setSavePassword(false);
        //自动加载图片
        settings.setLoadsImagesAutomatically(true);
    }


    public void setHybirdManager(HybirdManager hybirdManager) {
        this.hybirdManager = hybirdManager;
    }
}
