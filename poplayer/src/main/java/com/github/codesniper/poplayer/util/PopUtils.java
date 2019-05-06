package com.github.codesniper.poplayer.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 2018/10/29.
 */

public class PopUtils {

    /**
     * 获取view的bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    /**
     * JSBRIDGE的文本 用来注入
     *
     * @param context
     * @return
     */
    public static String getJsCode(Context context, String fileName) {

        String str = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int lenght = 0;
            lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            str = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }


    public static String timeStamp2Date(long seconds) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds * 1000));
    }


    /**
     * 设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
     *
     * @param webView
     */
    public static void initFilePermission(WebView webView, boolean isFileGranted) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(isFileGranted);
        } else {
            try {
                Class<?> clazz = webView.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webView.getSettings(), isFileGranted);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean isAppInDebug(Context context) {

        try {

            ApplicationInfo info = context.getApplicationInfo();

            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        } catch (Exception e) {

            return false;

        }


    }


    public static void Log(String msg){
        Log.d("PopLayer",msg);
    }
}