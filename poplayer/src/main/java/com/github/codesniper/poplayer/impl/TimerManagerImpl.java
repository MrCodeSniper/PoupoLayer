package com.github.codesniper.poplayer.impl;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.util.Log;

import com.github.codesniper.poplayer.interfaces.TimerManager;

import static com.github.codesniper.poplayer.config.LayerConfig.POP_TAG;

/**
 * 时间次数管理实现类
 * @author CH
 */
public class TimerManagerImpl implements TimerManager {

    @Override
    public void onCountOut() {
        Log.d(POP_TAG,"弹窗显示达到最大次数");
    }

    @Override
    public void onShowTimeOut() {
        Log.d(POP_TAG,"弹窗持续时间到");
    }

    @Override
    public void onTimeEnd() {
        Log.d(POP_TAG,"弹窗超出截止时间");
    }

    @Override
    public void onTimeEarly() {
        Log.d(POP_TAG,"弹窗还没到生效时间");
    }
}
