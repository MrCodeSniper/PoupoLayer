package com.github.codesniper.poplayer.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;
import com.github.codesniper.poplayer.util.PopUtils;

import static android.content.ContentValues.TAG;

/**
 * @Author：陈鸿 on 2019\2\2 0002 21:24
 * 邮箱：15168264355@163.com
 */
public class PopWebView extends WebView implements IPop,View.OnTouchListener {

    private Context mContext;
    private int mTouchType;
    private LayerTouchSystem layerTouchSystemImpl;

    public PopWebView(Context context) {
        super(context);
    }

    public PopWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayerTouchSystemImpl(LayerTouchSystem layerTouchSystemImpl) {
        this.layerTouchSystemImpl = layerTouchSystemImpl;
    }

    //return false自己没处理给原生处理
    // return true 自己没处理 原生没处理
    //super.onTouchEvent(event) return true 自己处理 原生不处理
    //super.onTouchEvent(event) return false 自己没处理给原生处理  + ontouch接口 false  自己没处理给原生处理

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mTouchType==LayerConfig.POP_TOUCH_WEB){//交给网页处理
            return super.onTouchEvent(event);
        }else {//交给原生处理
            return false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(layerTouchSystemImpl==null) return false;

        int alpha=0;
        //每一次触摸生成bitmap
        Bitmap bitmap= PopUtils.getBitmapFromView(this);
        //获取触摸点的ARGB的alpha值 将位图回收
        if (null != bitmap) {
            int pixel = bitmap.getPixel((int)event.getX(), (int)event.getY());
            alpha = Color.alpha(pixel);
            Log.e(TAG,event.getX()+"**"+event.getY()+"**"+alpha);
            bitmap.recycle();
        }

        if(alpha==255){//实体
            layerTouchSystemImpl.onTouchSolidArea(this);
            Log.e(TAG,"触摸监听器为true 消费调 webview的ontouch不执行");
        }else {
            layerTouchSystemImpl.onTouchOutSideArea(this);
            Log.e(TAG,"设置触摸监听器:返回false");
        }
        return false;
    }

    @Override
    public void onPopTouch(int touchStatus) {
        mTouchType=touchStatus;
    }
}
