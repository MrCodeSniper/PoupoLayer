package com.github.codesniper.poplayer;

import android.content.Context;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;


/**
 *  整合app内弹窗为一个概念-Poplayerview
 *  包含： 1.全屏透明的webview 2.dialog 3.poupowindow 4.widget插件 5.toast 6.snackbar 7.可扩展自定义的弹窗view
 */
public class PopLayerView  {

    private Context mContext;
    //当前弹窗是否显示
    private boolean isShow=false;
    //当前弹窗的类型
    public int mLayerType;
    //当前弹窗消失的回调
    private PopDismissListener popDismissListener;
    //弹窗生命周期
    private LayerLifecycle iLayerStrategy;

    public void setiLayerStrategy(LayerLifecycle iLayerStrategy) {
        this.iLayerStrategy = iLayerStrategy;
        initLayerView();

    }

    public void setListener(PopDismissListener listener) {
        this.popDismissListener = listener;
    }

    public PopDismissListener getPopDismissListener() {
        return popDismissListener;
    }

    public void setPopDismissListener(PopDismissListener popDismissListener) {
        this.popDismissListener = popDismissListener;
    }

    public PopLayerView(Context context) {
        this.mContext=context;
    }

    public Context getContext() {
        return mContext;
    }

    private void initLayerView(){
        if(iLayerStrategy!=null){
            iLayerStrategy.onCreate(mContext);
            iLayerStrategy.onInit(mContext);
        }
    }

    public void show(){
        iLayerStrategy.onShow(mContext);
        isShow=true;
    }

    public void dismiss(){
        //会调用具体弹窗的消失方法
        iLayerStrategy.onDismiss(mContext);
        //回调自身消失函数
        if(popDismissListener!=null){
            popDismissListener.onPopDimiss();
        }
        isShow=false;
    }

    public void recycle(){
        iLayerStrategy.onRecycle(mContext);
        mContext=null;
    }

    /**
     * 判断弹窗是否显示
     */
    public boolean isShow() {
        return isShow;
    }





//    //初始化时 应该用接口扩展
//    public void initLayerView(WebViewUiManager listener) {
//
//        WebViewConfig webViewConfig=new WebConfigImpl();
//        webViewConfig.initHybirdImpl(new HybirdImpl(mContext));
//        webViewConfig.initUiImpl(listener);
//        webViewConfig.initWebInterfaceImpl(new WebViewListener() {
//            @Override
//            public void OnWebViewDissmissListener() {
//                dismiss();
//            }
//        });
//
//        PopLayerController hrzLayer= PopLayerController.builder()
//                .setPushManagerImpl(new PushManagerImpl())
//                .setTimerManagerImpl(new TimerManagerImpl())
//                .setLayerTouchImpl(new LayerTouchImpl())
//                .setWebViewConfigImpl(webViewConfig)
//                .build();
//
//
//        if(state==STATE_DIALOG){
//            if(dialogView!=null){
//                dialogLayerStrategy=new DialogLayerStrategyImpl(dialogView,R.style.dialog);
//                iLayerStrategy=dialogLayerStrategy;
//            }
//        }else if(state==STATE_WEBVIEW){
//            WebViewLayerStrategyImpl webViewLayerStrategy=new WebViewLayerStrategyImpl(webViewConfig,loadScheme);
//            webViewLayerStrategy.setLayerTouchSystem(hrzLayer.getLayerTouchImpl());
//            iLayerStrategy=webViewLayerStrategy;
//        }else {
//            iLayerStrategy=null;
//            return;
//        }
//
//        iLayerStrategy.onCreate(mContext);
//
//        if(state==STATE_DIALOG){
//            if(dialogLayerStrategy!=null){
//                dialogLayerStrategy.getDialog().setCanceledOnTouchOutside(isDialogOutsideCancel);
//                dialogLayerStrategy.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                         dismiss();
//                    }
//                });
//            }
//        }
//
////        hrzLayer.setLayerStrategyChooser(new LayerStrategyChooser(iLayerStrategy,mContext));
////        proxy= (LayerLifecycle) new LayerLifeCycleProxy(hrzLayer).getProxyInstance();
////        proxy.onCreate(mContext);
//    }





}
