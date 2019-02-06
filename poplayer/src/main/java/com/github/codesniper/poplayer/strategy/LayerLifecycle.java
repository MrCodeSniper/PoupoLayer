package com.github.codesniper.poplayer.strategy;

import android.content.Context;

import com.github.codesniper.poplayer.custom.IPop;

/**
 *  自定义弹窗生命周期 具体跟随context而变化
 */
public interface LayerLifecycle {

    //弹窗建立回调
    void createLayerView(Context context);
    //弹窗初始化回调
    void initLayerView(Context context);
    //弹窗显示回调
    void showLayer(Context context);
    //弹窗消失回调
    void dissmissLayer(Context context);
    //弹窗对象回收回调
    void recycleLayer(Context context);
    //获取实际弹窗对象
    IPop getLayerConcreteView();
    //获取context对象
    Context getLayerContext();

}
