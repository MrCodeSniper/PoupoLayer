package com.github.codesniper.poplayer.webview.impl;

import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;

/**
 * @Author：陈鸿 on 2019\2\2 0002 22:29
 * 邮箱：15168264355@163.com
 */
public class PopWebTouchImpl implements LayerTouchSystem {
    @Override
    public void onTouchOutSideArea(IPop iPop) {
        if(iPop!=null){
            iPop.onPopTouch(LayerConfig.POP_TOUCH_NATIVE);
        }
    }

    @Override
    public void onTouchSolidArea(IPop iPop) {
        if(iPop!=null) {
            iPop.onPopTouch(LayerConfig.POP_TOUCH_WEB);
        }
    }
}
