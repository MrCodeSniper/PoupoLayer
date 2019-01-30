package com.github.codesniper.poplayer.strategy;

import android.content.Context;

/**
 *  自定义弹窗生命周期 具体跟随context而变化
 */
public interface LayerLifecycle {

    //弹窗建立回调
    void onCreate(Context context);
    //弹窗初始化回调
    void onInit(Context context);
    //弹窗显示回调
    void onShow(Context context);
    //弹窗消失回调
    void onDismiss(Context context);
    //弹窗对象回收回调
    void onRecycle(Context context);

}
