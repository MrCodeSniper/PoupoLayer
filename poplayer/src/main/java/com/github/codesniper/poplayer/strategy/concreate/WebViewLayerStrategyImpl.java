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
import android.webkit.WebView;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.R;
import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.config.WebDismissListener;
import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.custom.newPop.IWindow;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.util.PopUtils;
import com.github.codesniper.poplayer.webview.inter.HybirdManager;
import com.github.codesniper.poplayer.webview.inter.WebviewConfig;
import com.github.codesniper.poplayer.webview.impl.HybirdImpl;
import com.github.codesniper.poplayer.webview.impl.PopWebTouchImpl;
import com.github.codesniper.poplayer.webview.impl.WebConfigImpl;

import static android.content.ContentValues.TAG;
import static com.github.codesniper.poplayer.config.LayerConfig.POP_TAG;

/**
 * @Author：陈鸿 on 2019\2\2 0002 21:11
 * 邮箱：15168264355@163.com
 */
public class WebViewLayerStrategyImpl implements LayerLifecycle {


    private LayerTouchSystem mLayerTouchSystemImpl;

    private HybirdManager mHibirdImp;

    private PopWebView myWebView;

    private boolean isWebViewAttached=false;

    private Context mContext;

    private boolean isShow=false;

    //加载url
    private String globalLoadScheme;

    public WebViewLayerStrategyImpl(Context context,String globalLoadScheme) {
        this.mContext=context;
        this.globalLoadScheme = globalLoadScheme;
    }


    //必须保证oncreate在set之前执行
    @Override
    public void createLayerView() {
        //如果用户没有传入webview 则用默认的全屏透明的webview
        if(myWebView==null){

            isWebViewAttached=false;

            //解析webview对象
            ViewGroup view= (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.poplayer_default_weblayout, null,false);
            myWebView= view.findViewById(R.id.myWeb);
            view.removeAllViews();

            mHibirdImp=new HybirdImpl();
            mLayerTouchSystemImpl=new PopWebTouchImpl();

            //设置webconfig
            WebConfigImpl webConfig=new WebConfigImpl();
            webConfig.setHybirdManager(mHibirdImp);
            webConfig.setUpWebConfig(myWebView,globalLoadScheme);
        }
    }

    @Override
    public void initLayerView() {
        myWebView.setLayerTouchSystemImpl(mLayerTouchSystemImpl);
    }

    @Override
    public void showLayer() {
        if(isWebViewAttached){
            Log.e("xxx","显示1");
            myWebView.setVisibility(View.VISIBLE);
            isShow=true;
        }else {
            Log.e("xxx","显示2");
            //如果不存在直接在window上加
            if(mContext instanceof Activity){
                ((Activity)mContext).getWindow().addContentView(myWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                isWebViewAttached=true;
                isShow=true;
            }
        }
    }


    @Override
    public void dissmissLayer() {
        Log.e(POP_TAG,"WebView onDismiss");

        if(myWebView!=null){
            myWebView.setVisibility(View.GONE);
        }

        PopManager.getInstance(mContext).onPopDimiss();

        isShow=false;
    }

    @Override
    public void recycleLayer() {
        if(myWebView!=null){
            myWebView.stopLoading();
            myWebView.clearHistory();
            myWebView.clearCache(true);
            myWebView.loadUrl("about:blank");
            myWebView.pauseTimers();
            myWebView = null;
            isWebViewAttached=false;
            isShow=false;
        }
    }


    @Override
    public Context getLayerContext() {
        return mContext;
    }

    @Override
    public boolean isShowing() {
        return isShow;
    }

    @Override
    public IWindow getWindowView() {
        return null;
    }

    @Override
    public View getViewById(int id) {
        return null;
    }
}
