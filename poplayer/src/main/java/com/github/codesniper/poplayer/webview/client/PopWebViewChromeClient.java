package com.github.codesniper.poplayer.webview.client;

import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.github.codesniper.poplayer.webview.inter.HybirdManager;

import static com.github.codesniper.poplayer.config.LayerConfig.POP_JSB;

/**
 * @Author：陈鸿 on 2019\2\2 0002 22:16
 * 邮箱：15168264355@163.com
 */
public class PopWebViewChromeClient extends WebChromeClient {

    private HybirdManager mHybirdImpl;

    public void setmHybirdImpl(HybirdManager mHybirdImpl) {
        this.mHybirdImpl = mHybirdImpl;
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(newProgress>=80){ //视为webview加载完毕

        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        //注入JSBridge的时机
        if(mHybirdImpl!=null){
            mHybirdImpl.injectJsBridge(view,POP_JSB);
        }
        super.onReceivedTitle(view, title);
    }


    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        String postJson=message.replaceAll("\\\\", "");//格式化字符串
        if (!TextUtils.isEmpty(postJson)) {
            if(mHybirdImpl!=null){
                mHybirdImpl.invokeAppServices(postJson);
            }
            //防止prompt阻塞交互
            result.confirm();
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}
