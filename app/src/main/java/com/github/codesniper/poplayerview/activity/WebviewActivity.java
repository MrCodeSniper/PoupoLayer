package com.github.codesniper.poplayerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.custom.PopCallback;
import com.github.codesniper.poplayer.custom.PopWebView;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayerview.R;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;




public class WebviewActivity extends AppCompatActivity {

    //我需要加载自己的url 满足
    //我可以任选jsbridge方案还是第三方方案 还是原生 不满足
    //我可以自定义注入的对象名 满足
    //我可以自定义配置webview 满足
    //可不可以自定义X5webview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_subject);
        PopManager.getInstance(this).setPopCallback(new PopCallback() {
            @Override
            public void onPopExisted(int queueSize) {

            }

            @Override
            public void onPopOutOfDate() {
                Toast.makeText(WebviewActivity.this,"弹窗超出限定时间显示范围", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopShowMaxCount() {
                Toast.makeText(WebviewActivity.this,"弹窗显示已经达到最大次数", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopShowSuccess() {

            }

            @Override
            public void onPopDelayDismiss() {

            }
        });
    }


    /**
     * 普通弹窗无需纳入管理
     * @param view
     */
    public void showNormalPop(View view) {
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.dialog5);
        mLayerView.show();
    }



    /**
     * 显示延迟弹窗 倒数5s
     * @param view
     */
    public void showDealyPop(View view) {
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.dialog3);

        Popi delayPop = new Popi.Builder()
                .setmPopId(1)
                .setmPriority(2)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setLayerView(mLayerView)
                .build();

        PopManager.getInstance(this).pushToQueue(delayPop);
        PopManager.getInstance(this).showNextPopi();
    }



    /**
     * 限定活动时间
     * @param view
     */
    public void showEventPop(View view) {
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.redPocketScheme);

        Popi eventPop = new Popi.Builder()
                .setmPopId(2)
                .setmPriority(4)
                .setmCancelType(TRIGGER_CANCEL)
                .setmBeginDate(154885802L)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1559666666)//结束时间 2019-01-31 22:20:28
                .setLayerView(mLayerView)
                .build();

        PopManager.getInstance(this).pushToQueue(eventPop);
        PopManager.getInstance(this).showNextPopi();
    }



    /**
     * 限定次数5次
     * @param view
     */
    public void showTimePop(View view) {

        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.dialog6);

        Popi timePop = new Popi.Builder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowCount(5)
                .setLayerView(mLayerView)
                .build();

        PopManager.getInstance(this).pushToQueue(timePop);
        PopManager.getInstance(this).showNextPopi();
    }


    public void showPriorityPop(View view){

        //黑色弹窗 优先级为4 延迟5S取消
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.dialog6);

        Popi eventPop = new Popi.Builder()
                .setmPopId(2)
                .setmPriority(4)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setLayerView(mLayerView)
                .build();

        PopManager.getInstance(this).pushToQueue(eventPop);

        //黄色标签弹窗 优先级为1
        PopLayerView mLayerView1 = new PopLayerView(this,LayerConfig.dialog5);

        Popi timePop = new Popi.Builder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setLayerView(mLayerView1)
                .build();


        PopManager.getInstance(this).pushToQueue(timePop);

        PopManager.getInstance(this).showNextPopi();
    }


    /**
     * 显示红包雨弹窗
     * @param view
     */
    public void showRpRain(View view) {
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.redPocketScheme);
        mLayerView.show();
    }

    private PopWebView webView;

    /**
     * 显示JS
     * @param view
     */
    public void showJS(View view) {
        PopLayerView mLayerView = new PopLayerView(this,LayerConfig.jsTest);
//        webView= (PopWebView) mLayerView.getiPop();
        mLayerView.show();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView!=null){
            webView.destroy();
        }
    }
}
