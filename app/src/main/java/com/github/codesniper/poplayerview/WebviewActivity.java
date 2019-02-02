package com.github.codesniper.poplayerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.strategy.concreate.DialogLayerStrategyImpl;
import com.github.codesniper.poplayer.strategy.concreate.WebViewLayerStrategyImpl;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class WebviewActivity extends AppCompatActivity {

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
    public void showTransparentWebview(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        mLayerView.setiLayerStrategy(new WebViewLayerStrategyImpl(LayerConfig.redPocketScheme,"hrz_jsb_new.js","poplayer"));
        mLayerView.show();
    }



}
