package com.github.codesniper.poplayer;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.custom.newPop.IWindow;
import com.github.codesniper.poplayer.custom.newPop.IWindowEvent;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.strategy.concreate.DialogLayerStrategyImpl;
import com.github.codesniper.poplayer.strategy.concreate.WebViewLayerStrategyImpl;
import com.github.codesniper.poplayer.unused.ILayerStrategy;


/**
 *  整合app内弹窗为一个概念-Poplayerview
 *  包含： 1.全屏透明的webview 2.dialog 3.poupowindow 4.widget插件 5.toast 6.snackbar 7.可扩展自定义的弹窗view
 */
public   class  PopLayerView <T> implements IWindowEvent { //提供给外界的入口

    private Context mContext;


    //抽象弹窗策略
    private LayerLifecycle starategy;


    public interface onPopDismissListener{
        void onDismiss();
    }

    private onPopDismissListener onPopDismissListener;

    public void setOnPopDismissListener(PopLayerView.onPopDismissListener onPopDismissListener) {
        this.onPopDismissListener = onPopDismissListener;
    }

    /**------------------根据构造方法决定对应策略--------------------**/

    private int mLayout;
    private int mTheme;
    private IWindow<T> iWindow;
    private String loadUrl;



    public static final int CUSTOM = 0;
    public static final int CUSTOM_LAYOUT_DIALOG = 2;
    public static final int CUSTOM_LAYOUT_THEME_DIALOG = 3;
    public static final int CUSTOM_WEBVIEW = 4;

    private static int mType=CUSTOM;


    //构造 for 自定义Dialog
    public PopLayerView(Context context, IWindow<T> iWindow){
        this.iWindow=iWindow;
        this.mContext=context;
        mType=CUSTOM;
        initView();
    }

    //构造 for Dialog带布局
    public PopLayerView(Context context,int dialogLayout){
        this.mLayout=dialogLayout;
        this.mContext=context;
        mType=CUSTOM_LAYOUT_DIALOG;
        initView();
    }

    //构造 for Dialog带布局和主题
    public PopLayerView(Context context,int dialogLayout,int themeId){
        this.mLayout=dialogLayout;
        this.mTheme=themeId;
        this.mContext=context;
        mType=CUSTOM_LAYOUT_THEME_DIALOG;
        initView();
    }

    private void initView(){
        switch (mType){
            case CUSTOM:
                if(iWindow.getPoupo() instanceof Dialog){
                    DialogLayerStrategyImpl impl=new DialogLayerStrategyImpl(mContext,iWindow);
                    impl.setWindowEvent(this);
                    this.starategy=impl;
                }else if(iWindow.getPoupo() instanceof WebView){
                    //TODO
                }
                break;
            case CUSTOM_LAYOUT_DIALOG:
                DialogLayerStrategyImpl impl=new DialogLayerStrategyImpl(mContext,mLayout);
                impl.setWindowEvent(this);
                this.starategy=impl;
                break;
            case CUSTOM_LAYOUT_THEME_DIALOG:
                DialogLayerStrategyImpl impl1=new DialogLayerStrategyImpl(mContext,mLayout,mTheme);
                impl1.setWindowEvent(this);
                this.starategy=impl1;
                break;
            case CUSTOM_WEBVIEW:
                this.starategy=new WebViewLayerStrategyImpl(mContext,loadUrl);
                break;
        }
        if(starategy!=null){
            starategy.createLayerView();
            starategy.initLayerView();
        }
    }




   /**--------------------------------------**/


    //构造 for 默认Webview
    public PopLayerView(Context context,String loadUrl){
        this.mContext=context;
        this.loadUrl=loadUrl;
        mType=CUSTOM_WEBVIEW;
        initView();
    }





    /**------------------交给对应的策略执行--------------------**/

    public void show(){
        if(starategy!=null){
            starategy.showLayer();
        }
    }

    public void dismiss(){
        if(starategy!=null){
            starategy.dissmissLayer();
        }
    }

    public void recycle(){
        if(starategy!=null){
            starategy.recycleLayer();
            mContext=null;
        }
    }

    public boolean isShow() {
        if(starategy!=null){
            return starategy.isShowing();
        }
        return false;
    }

    public Context getContext() {
        return mContext;
    }


    public IWindow<T> getView(){
        if(starategy!=null){
            return starategy.getWindowView();
        }
        return null;
    }


    public View findViewById(int id){
        if(starategy!=null){
            return starategy.getViewById(id);
        }
        return null;
    }


    ///////////////////////////////////////////////////////////

    @Override
    public void onWindowShow() {

    }

    @Override
    public void onWindowDismiss() {
        if(onPopDismissListener!=null){
            onPopDismissListener.onDismiss();
        }
    }

    public LayerLifecycle getConcreateStrategy(){
        return starategy;
    }
}
