package com.github.codesniper.poplayerview.custom;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：陈鸿 on 2019\2\3 0003 14:30
 * 邮箱：15168264355@163.com
 */
public class TestJs extends Object {

    @JavascriptInterface
    public void test(String str){
        Log.e("TestJs",str);
    }

}
