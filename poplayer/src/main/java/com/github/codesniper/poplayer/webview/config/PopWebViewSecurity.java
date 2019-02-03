package com.github.codesniper.poplayer.webview.config;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebView;

/**
 * 防止远程执行漏洞
 */

public class PopWebViewSecurity {

    @TargetApi(11)
    public static final void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }

}
