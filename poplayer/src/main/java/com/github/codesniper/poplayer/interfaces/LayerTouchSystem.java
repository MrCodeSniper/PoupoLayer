package com.github.codesniper.poplayer.interfaces;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.custom.PopWebView;

/**
 *  弹窗的触摸机制 统一   分为实体区域 和 外围区域 触摸时的事件收发可以自定义
 */
public interface LayerTouchSystem {
    //触摸到外部区域
    void onTouchOutSideArea(IPop iPop);
    //触摸到内部区域
    void onTouchSolidArea(IPop iPop);
}
