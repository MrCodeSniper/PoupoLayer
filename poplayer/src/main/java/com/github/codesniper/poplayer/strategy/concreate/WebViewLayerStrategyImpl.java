package com.github.codesniper.poplayer.strategy.concreate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.codesniper.poplayer.R;
import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.util.PopUtils;
import com.github.codesniper.poplayer.webview.inter.HybirdManager;
import com.github.codesniper.poplayer.webview.inter.WebviewConfig;
import com.github.codesniper.poplayer.webview.impl.HybirdImpl;
import com.github.codesniper.poplayer.webview.impl.PopWebTouchImpl;
import com.github.codesniper.poplayer.webview.impl.WebConfigImpl;

import static android.content.ContentValues.TAG;

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

    public PopWebView getMyWebView() {
        return myWebView;
    }



    //必须保证oncreate在set之前执行
    @Override
    public void onCreate(Context context) {
        //如果用户没有传入webview 则用默认的全屏透明的webview
        if(myWebView==null){

            isWebViewAttached=false;
            ViewGroup view= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.poplayer_default_weblayout, null,false);
            myWebView= view.findViewById(R.id.myWeb);
            view.removeAllViews();

            mHibirdImp=new HybirdImpl();
            mLayerTouchSystemImpl=new PopWebTouchImpl();

            WebConfigImpl webConfig=new WebConfigImpl(jsBridgeFileName,jsObjName);
            webConfig.setHybirdManager(mHibirdImp);
            mWebViewConfigImpl=webConfig;
            mWebViewConfigImpl.setUpWebConfig(myWebView,globalLoadScheme);

            myWebView.setOnTouchListener(myWebView);
        }
    }

    @Override
    public void onInit(Context context) {
        myWebView.setLayerTouchSystemImpl(mLayerTouchSystemImpl);
    }

    @Override
    public void onShow(Context context) {
        if(isWebViewAttached){
            Log.e("xxx","显示1");
            myWebView.setVisibility(View.VISIBLE);
        }else {
            Log.e("xxx","显示2");
            //如果不存在直接在window上加
            ((Activity)context).getWindow().addContentView(myWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            isWebViewAttached=true;
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
