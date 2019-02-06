package com.github.codesniper.poplayerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.strategy.concreate.DialogLayerStrategyImpl;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_subject);
    }

    /**
     * 普通弹窗无需纳入管理
     *
     * @param view
     */
    public void showNormalDialog(View view) {
        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);
        mLayerView.onShow();
    }


    public void showDelayDialog(View view) {



        PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);

        Popi  mUpgradePopi = new Popi.Builder()
                    .setmPopId(1)
                    .setmPriority(1)
                    .setmCancelType(COUNTDOWN_CANCEL)
                    .setMaxShowTimeLength(5)
                    .setConcreateLayer(mLayerView.getiLayerStrategy())
                    .build();


        Log.e("tag", mUpgradePopi.toString());

        PopManager.getInstance(this).pushToQueue(mUpgradePopi);
        PopManager.getInstance(this).showNextPopi();
    }


    public void showDateDialog(View view) {

        PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);

        Popi  mUpgradePopi = new Popi.Builder()
                    .setmPopId(2)
                    .setmPriority(1)
                    .setmCancelType(COUNTDOWN_CANCEL)
                    .setMaxShowTimeLength(5)
                    .setmBeginDate(1548858028L)//开始时间 2019-01-30 22:20:28
                    .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                    .setConcreateLayer(mLayerView.getiLayerStrategy())
                    .build();

        PopManager.getInstance(this).pushToQueue(mUpgradePopi);
        PopManager.getInstance(this).showNextPopi();
    }

    public void showTimeDialog(View view) {

        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);


        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028L)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428L)//结束时间 2019-01-31 22:20:28
                .setConcreateLayer(mLayerView.getiLayerStrategy())
                .build();

        PopManager.getInstance(this).pushToQueue(mUpgradePopi);
        PopManager.getInstance(this).showNextPopi();
    }


    public void showPriorityDialog(View view) {


        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);

        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(4)
                .setmPriority(10)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                .setConcreateLayer(mLayerView.getiLayerStrategy())
                .build();

        PopManager.getInstance(this).pushToQueue(mUpgradePopi);


        PopLayerView mLayerView1 = new PopLayerView(this,R.layout.common_dialog_upgrade_app2);

        Popi mUpgradePopi1 = new Popi.Builder()
                .setmPopId(5)
                .setmPriority(2)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                .setConcreateLayer(mLayerView1.getiLayerStrategy())
                .build();

        PopManager.getInstance(this).pushToQueue(mUpgradePopi1);


        PopManager.getInstance(this).showNextPopi();


    }
}
