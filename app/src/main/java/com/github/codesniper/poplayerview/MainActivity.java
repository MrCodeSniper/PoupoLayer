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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    /**
     * 普通弹窗无需纳入管理
     *
     * @param view
     */
    public void showNormalDialog(View view) {
        PopLayerView mLayerView = new PopLayerView(this);
        mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app, R.style.FullTransDialog));
        mLayerView.show();
    }


    public void showDelayDialog(View view) {


        PopLayerView  mLayerView = new PopLayerView(this);
            mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app, R.style.FullTransDialog));



        Popi  mUpgradePopi = new Popi.Builder()
                    .setmPopId(1)
                    .setmPriority(1)
                    .setmCancelType(COUNTDOWN_CANCEL)
                    .setMaxShowTimeLength(5)
                    .setmPopLayerView(mLayerView)
                    .build();


        Log.e("tag", mUpgradePopi.toString());

        PopManager.getInstance().pushToQueue(mUpgradePopi);
        PopManager.getInstance().showNextPopi();
    }


    public void showDateDialog(View view) {

        PopLayerView    mLayerView = new PopLayerView(this);
            mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app, R.style.FullTransDialog));



        Popi  mUpgradePopi = new Popi.Builder()
                    .setmPopId(2)
                    .setmPriority(1)
                    .setmCancelType(COUNTDOWN_CANCEL)
                    .setMaxShowTimeLength(5)
                    .setmBeginDate(1548858028L)//开始时间 2019-01-30 22:20:28
                    .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                    .setmPopLayerView(mLayerView)
                    .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi);
        PopManager.getInstance().showNextPopi();
    }

    public void showTimeDialog(View view) {

        PopLayerView mLayerView = new PopLayerView(this);
        mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app, R.style.FullTransDialog));


        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028L)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428L)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView)
                .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi);
        PopManager.getInstance().showNextPopi();
    }


    public void showPriorityDialog(View view) {


        PopLayerView mLayerView = new PopLayerView(this);
        mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app, R.style.FullTransDialog));

        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(4)
                .setmPriority(10)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView)
                .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi);


        PopLayerView mLayerView1 = new PopLayerView(this);
        mLayerView1.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app2, R.style.FullTransDialog, mLayerView1));

        Popi mUpgradePopi1 = new Popi.Builder()
                .setmPopId(5)
                .setmPriority(2)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowTimeLength(5)
                .setMaxShowCount(5)
                .setmBeginDate(1548858028)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView1)
                .build();

        PopManager.getInstance().pushToQueue(mUpgradePopi1);


        PopManager.getInstance().showNextPopi();


    }
}
