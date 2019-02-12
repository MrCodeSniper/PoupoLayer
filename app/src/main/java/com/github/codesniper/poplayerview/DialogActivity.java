package com.github.codesniper.poplayerview;

import android.hardware.camera2.params.LensShadingMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.custom.PopCallback;
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
        PopManager.getInstance(this).setPopCallback(new PopCallback() {
            @Override
            public void onPopExisted(int queueSize) {

            }

            @Override
            public void onPopOutOfDate() {
                Toast.makeText(DialogActivity.this,"弹窗超出限定时间显示范围", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopShowMaxCount() {
                Toast.makeText(DialogActivity.this,"弹窗显示已经达到最大次数", Toast.LENGTH_SHORT).show();
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
                    .setLayerView(mLayerView)
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
                    .setLayerView(mLayerView)
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
                .setMaxShowCount(10)
                .setLayerView(mLayerView)
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
                .setLayerView(mLayerView)
                .build();
        PopManager.getInstance(this).pushToQueue(mUpgradePopi);


        PopLayerView mLayerView1 = new PopLayerView(this,R.layout.common_dialog_upgrade_app2);
        Popi mUpgradePopi1 = new Popi.Builder()
                .setmPopId(5)
                .setmPriority(2)
                .setmCancelType(TRIGGER_CANCEL)
                .setLayerView(mLayerView1)
                .build();
        PopManager.getInstance(this).pushToQueue(mUpgradePopi1);



        PopManager.getInstance(this).showNextPopi();


    }
}
