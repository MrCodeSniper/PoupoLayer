package com.github.codesniper.poplayer.strategy.concreate;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.github.codesniper.poplayer.R;
import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.webview.HybirdManager;
import com.github.codesniper.poplayer.webview.WebviewConfig;
import com.github.codesniper.poplayer.webview.impl.HybirdImpl;
import com.github.codesniper.poplayer.webview.impl.PopWebTouchImpl;
import com.github.codesniper.poplayer.webview.impl.WebConfigImpl;

/**
 * @Author：陈鸿 on 2019\2\2 0002 21:11
 * 邮箱：15168264355@163.com
 */
public class WebViewLayerStrategyImpl implements LayerLifecycle {


    private WebviewConfig mWebViewConfigImpl;

    private LayerTouchSystem mLayerTouchSystemImpl;

    private PopWebView myWebView;

    private String globalLoadScheme;

    private boolean isWebViewAttached=false;

    private HybirdManager mHibirdImp;

    private String jsBridgeFileName;

    private String jsObjName;

    public WebViewLayerStrategyImpl(String globalLoadScheme, String jsBridgeFileName, String jsObjName) {
        this.globalLoadScheme = globalLoadScheme;
        this.jsBridgeFileName = jsBridgeFileName;
        this.jsObjName = jsObjName;
    }

    public void setmWebViewConfigImpl(WebviewConfig mWebViewConfigImpl) {
        this.mWebViewConfigImpl = mWebViewConfigImpl;
    }

    public void setmHibirdImp(HybirdManager mHibirdImp) {
        this.mHibirdImp = mHibirdImp;
    }

    public void setWebView(PopWebView myWebView) {
        this.myWebView = myWebView;
    }

    public void setLayerTouchSystemImpl(LayerTouchSystem mLayerTouchSystemImpl) {
        this.mLayerTouchSystemImpl = mLayerTouchSystemImpl;
    }


    //必须保证oncreate在set之前执行
    @Override
    public void onCreate(Context context) {
        //如果用户没有传入webview 则用默认的全屏透明的webview
        if(myWebView==null){
            isWebViewAttached=false;
            myWebView= View.inflate(context, R.layout.poplayer_default_weblayout, null).findViewById(R.id.myWeb);
            mHibirdImp=new HybirdImpl(context);
            mWebViewConfigImpl=new WebConfigImpl(jsBridgeFileName,jsObjName);
            mLayerTouchSystemImpl=new PopWebTouchImpl();
        }
    }

    @Override
    public void onInit(Context context) {
        mWebViewConfigImpl.setUpWebConfig(myWebView,globalLoadScheme);
        mWebViewConfigImpl.initHybirdImpl(mHibirdImp);
        myWebView.setLayerTouchSystemImpl(mLayerTouchSystemImpl);
        isWebViewAttached=true;
    }

    @Override
    public void onShow(Context context) {
        if(isWebViewAttached){
            myWebView.setVisibility(View.VISIBLE);
        }else {
            //如果不存在直接在window上加
            ((Activity)context).getWindow().addContentView(myWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void onDismiss(Context context) {
        if(myWebView!=null){
            myWebView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRecycle(Context context) {
        if(myWebView!=null){
            myWebView.stopLoading();
            myWebView.clearHistory();
            myWebView.clearCache(true);
            myWebView.loadUrl("about:blank");
            myWebView.pauseTimers();
            myWebView = null;
        }
    }
}
