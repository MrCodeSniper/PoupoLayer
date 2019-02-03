package com.github.codesniper.poplayerview.custom;

import android.webkit.JsPromptResult;
import android.webkit.WebView;

import com.github.codesniper.poplayer.webview.client.PopWebViewChromeClient;

/**
 *   如果您需要poplayer提供的服务 请继承基础的PopWebViewChromeClient
 */
public class UserChromeClient extends PopWebViewChromeClient {

    public UserChromeClient() {
        super("jsbrige_name");
    }


    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        //TODO involeAppService
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }









}
