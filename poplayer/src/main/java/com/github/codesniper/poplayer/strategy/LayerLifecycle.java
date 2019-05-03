package com.github.codesniper.poplayer.strategy;

import android.content.Context;
import android.view.View;

import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.custom.newPop.IWindow;

/**
 *  自定义弹窗生命周期 具体跟随context而变化
 */
public interface LayerLifecycle extends BasicStrategy{

    //弹窗建立回调
    void createLayerView();
    //弹窗初始化回调
    void initLayerView();
    //弹窗显示回调
    void showLayer();
    //弹窗消失回调
    void dissmissLayer();
    //弹窗对象回收回调
    void recycleLayer();
    //获取context对象
    Context getLayerContext();
    //弹窗是否正在显示
    boolean isShowing();
    //获取对应策略的实体弹窗对象
    IWindow getWindowView();


}
