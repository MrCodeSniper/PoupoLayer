package com.github.codesniper.poplayer.webview.impl;

import android.content.Context;
import android.webkit.WebView;


import com.github.codesniper.poplayer.util.PopUtils;
import com.github.codesniper.poplayer.webview.HybirdManager;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by mac on 2018/10/29.
 */

public class HybirdImpl implements HybirdManager {


    private Context context;

    public HybirdImpl(Context context) {
        this.context=context;
    }

    @Override
    public void injectJsBridge(WebView webView,String JsName) {
        webView.loadUrl("javascript:" + PopUtils.getJsCode(context,JsName));
    }

    @Override
    public void invokeAppServices(String instruction) {

        try{
            PopWebViewService popWebViewService=null;

            JSONObject jsonObject = new JSONObject(instruction.substring(instruction.indexOf("{"), instruction.lastIndexOf("}") + 1));

            String invokeId=jsonObject.getString("invokeId");
            String methodName = jsonObject.getString("methodName");
            String android_methodName = methodName.split(Pattern.quote("."))[1];
            JSONObject paramObject = jsonObject.getJSONObject("methodParams");

            Iterator iterator = paramObject.keys();
            Map map = new HashMap();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                map.put(key, paramObject.getString(key));
            }

//        map.put("context", BrowserActivity.this);
//        map.put("webview",webView);
            map.put("invokeId",invokeId);


            //前端调原生 方法集合类
            Class<PopWebViewService> invokeMethodObject = (Class<PopWebViewService>) Class.forName(PopWebViewService.class.getResource("").toString());
            if (invokeMethodObject != null) {
                popWebViewService = invokeMethodObject.newInstance();
            }

            if (invokeMethodObject != null) {
                Method repay1 = invokeMethodObject.getDeclaredMethod(android_methodName, Map.class);
                if (repay1 != null&& popWebViewService!=null) {
                    repay1.setAccessible(true);
                    repay1.invoke(popWebViewService, map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addUpJavaNativeJSInterface(WebView webView,String name) {
        webView.addJavascriptInterface(new PopWebViewJsInterface(webView),name);
    }
}
