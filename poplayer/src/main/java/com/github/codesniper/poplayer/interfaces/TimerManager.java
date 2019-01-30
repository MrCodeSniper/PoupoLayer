package com.github.codesniper.poplayer.interfaces;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!

/**
 * 对下发活动的时间和显示次数进行管理
 */
public interface TimerManager {


    //弹窗显示次数超出
    void onCountOut();

    //弹窗的持续显示时间到期  以毫秒数为基本单位
    void onShowTimeOut();


    // 2019.1.1 ~ 2019.1.3 以时间撮为基准单位

    //弹窗已经超出生效结束时间
    void onTimeEnd();
    //弹窗还没到生效开始时间
    void onTimeEarly();

}
