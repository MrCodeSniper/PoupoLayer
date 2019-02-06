package com.github.codesniper.poplayer;

import android.content.Context;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.strategy.concreate.DialogLayerStrategyImpl;
import com.github.codesniper.poplayer.strategy.concreate.WebViewLayerStrategyImpl;


/**
 *  整合app内弹窗为一个概念-Poplayerview
 *  包含： 1.全屏透明的webview 2.dialog 3.poupowindow 4.widget插件 5.toast 6.snackbar 7.可扩展自定义的弹窗view
 */
public class PopLayerView{

    private Context mContext;

    //当前弹窗是否显示
    private boolean isShow=false;

    //抽象弹窗 依赖抽象
    private LayerLifecycle iLayerStrategy;

    //获取实体对象
    public IPop getiPop() {
        if(iLayerStrategy!=null){
            return iLayerStrategy.getLayerConcreteView();
        }
        return null;
    }



    public PopLayerView(Context context, LayerLifecycle iLayerStrategy) {
        this.mContext = context;
        this.iLayerStrategy = iLayerStrategy;
        onCreate();
        onInit();
    }


    //方便的构造方法 for Dialog
    public PopLayerView(Context context,int dialogLayout){
        this(context,new DialogLayerStrategyImpl(dialogLayout,R.style.FullTransDialog));
    }

    //方便的构造方法 for Webview
    public PopLayerView(Context context,String loadUrl){
        this(context,new WebViewLayerStrategyImpl(loadUrl));
    }


    public Context getContext() {
        return mContext;
    }

    private void onCreate(){
        if(iLayerStrategy!=null){
            iLayerStrategy.createLayerView(mContext);
        }
    }

    private void onInit(){
        if(iLayerStrategy!=null){
            iLayerStrategy.initLayerView(mContext);
        }
    }

    public void onShow(){
        if(iLayerStrategy!=null){
            iLayerStrategy.showLayer(mContext);
            isShow=true;
        }
    }

    public void onDismiss(){
        if(iLayerStrategy!=null){
            iLayerStrategy.dissmissLayer(mContext);
            isShow=false;
        }
    }

    public void onRecycle(){
        if(iLayerStrategy!=null){
            iLayerStrategy.recycleLayer(mContext);
            mContext=null;
        }
    }

    public LayerLifecycle getiLayerStrategy() {
        return iLayerStrategy;
    }

    /**
     * 判断弹窗是否显示
     */
    public boolean isShow() {
        return isShow;
    }



}
