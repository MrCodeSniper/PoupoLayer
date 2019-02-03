package com.github.codesniper.poplayerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.strategy.concreate.DialogLayerStrategyImpl;
import com.github.codesniper.poplayer.strategy.concreate.WebViewLayerStrategyImpl;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class WebviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_subject);
    }


    /**
     * 普通弹窗无需纳入管理
     *
     * @param view
     */
    public void showNormalPop(View view) {

        //我需要加载自己的url 满足
        //我可以任选jsbridge方案还是第三方方案 还是原生 不满足
        //我可以自定义注入的对象名 满足
        //我可以自定义配置webview 满足
        //可不可以自定义X5webview

        PopLayerView mLayerView = new PopLayerView(this);
        WebViewLayerStrategyImpl webViewLayerStrategy = new WebViewLayerStrategyImpl(LayerConfig.dialog5, "hrz_jsb_new.js", "poplayer");
        mLayerView.setiLayerStrategy(webViewLayerStrategy);
        //    initWebview(webViewLayerStrategy.getMyWebView());


        if (!mLayerView.isShow()) {
            mLayerView.show();
        }

    }


    /**
     * 显示延迟弹窗
     * @param view
     */
    public void showDealyPop(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        WebViewLayerStrategyImpl webViewLayerStrategy = new WebViewLayerStrategyImpl(LayerConfig.dialog3, "hrz_jsb_new.js", "poplayer");
        mLayerView.setiLayerStrategy(webViewLayerStrategy);

        Popi  popi = new Popi.Builder()
                .setmPopId(1)
                .setmPriority(1)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setmPopLayerView(mLayerView)
                .build();

        PopManager.getInstance().pushToQueue(popi);
        PopManager.getInstance().showNextPopi();
    }


    /**
     * 限定活动时间
     * @param view
     */
    public void showEventPop(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        WebViewLayerStrategyImpl webViewLayerStrategy = new WebViewLayerStrategyImpl(LayerConfig.dialog4, "hrz_jsb_new.js", "poplayer");
        mLayerView.setiLayerStrategy(webViewLayerStrategy);

        Popi  mUpgradePopi = new Popi.Builder()
                .setmPopId(2)
                .setmPriority(1)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setmBeginDate(154885802L)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1549666666)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView)
                .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi);
        PopManager.getInstance().showNextPopi();
    }

    /**
     * 限定次数5次
     * @param view
     */
    public void showTimePop(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        WebViewLayerStrategyImpl webViewLayerStrategy = new WebViewLayerStrategyImpl(LayerConfig.dialog6, "hrz_jsb_new.js", "poplayer");
        mLayerView.setiLayerStrategy(webViewLayerStrategy);

        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(154885802L)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1549666666)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView)
                .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi);
        PopManager.getInstance().showNextPopi();
    }











    /**
     * 显示红包雨弹窗
     * @param view
     */
    public void showRpRain(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        WebViewLayerStrategyImpl webViewLayerStrategy = new WebViewLayerStrategyImpl(LayerConfig.redPocketScheme, "hrz_jsb_new.js", "poplayer");
        mLayerView.setiLayerStrategy(webViewLayerStrategy);
     //   initWebview(webViewLayerStrategy.getMyWebView());

        if (!mLayerView.isShow()) {
            mLayerView.show();
        }
    }

//
//    private void initWebview(WebView myWebView) {
//        //可以在这里添加自己业务的原生js交互方案
//        myWebView.addJavascriptInterface(new TestJs(), "test");
//        //可以拿到webviewsetting进行设置
//        myWebView.getSettings().setAppCacheEnabled(true);
//        //Poplayer默认设置了webclient 可以根据
//        myWebView.setWebChromeClient(new UserChromeClient());
//        myWebView.setWebViewClient(new UserWebviewClient());
//    }


}
