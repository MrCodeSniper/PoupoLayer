package com.github.codesniper.poplayer.config;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/13.Best Wishes to You!  []~(~▽~)~* Cheers!


public class LayerConfig {
    //示例html 红包雨
    public static final String redPocketScheme="file:///android_asset/index_rain.html";
    public static final String dialog2="file:///android_asset/dialog2.html";
    public static final String dialog3="file:///android_asset/dialog3.html";
    public static final String dialog4="file:///android_asset/dialog4.html";
    public static final String dialog1="file:///android_asset/dialog1.html";
    public static final String dialog6="file:///android_asset/dialog6.html";
    public static final String dialog5="file:///android_asset/dialog5.html";


    //触摸区域常量 0为web组件 1为原生组件
    public static final int POP_TOUCH_WEB=0;
    public static final int POP_TOUCH_NATIVE=1;

    public static final String POP_TAG="PopLayer";

    public static final String POP_JSB="poplayer_jsb.js";

    public static final String POP_OBJ="poplayer";

    //弹窗的取消方式 0为点击消失 1为计时消失
    public static int TRIGGER_CANCEL=0;
    public static int COUNTDOWN_CANCEL=1;
}
