package com.github.codesniper.poplayerview.activity;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.custom.PopCallback;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayerview.R;
import com.github.codesniper.poplayerview.view.HrzNoticePopDialog;

import java.util.Timer;
import java.util.TimerTask;

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
    public void showNormalDialog1(View view) {
        HrzNoticePopDialog hrzNoticePopDialog = new HrzNoticePopDialog(this);
        PopLayerView mLayerView = new PopLayerView(this,hrzNoticePopDialog);
        mLayerView.show();
    }


    public void showNormalDialog2(View view) {
        final PopLayerView<Dialog> mLayerView = new PopLayerView(this,R.layout.common_popview_frame2);
        Dialog dialog=mLayerView.getView().getPoupo();
        mLayerView.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayerView.dismiss();
            }
        });
        mLayerView.show();
    }

    public void showDelayDialog(View view) {
        PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_popview_frame3);
        Popi.getBuilder()
                    .setmPopId(1)
                    .setmPriority(1)
                    .setmCancelType(COUNTDOWN_CANCEL)
                    .setMaxShowTimeLength(5)
                    .setLayerView(mLayerView)
                    .build()
                    .show();
}


    public void showDateDialog(View view) {
        PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_popview_frame4);
        Popi.getBuilder()
                .setmPopId(2)
                .setmPriority(1)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setmBeginDate(1525360159)//开始时间 2018-05-03 23:09:19
                .setmEndDate(1568858028)//结束时间 2019-09-19 09:53:48 1568858028
                .setLayerView(mLayerView)
                .build()
                .show();
    }

    public void showDateDialog2(View view) {
        PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_popview_frame4);
        Popi.getBuilder()
                .setmPopId(2)
                .setmPriority(1)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setmBeginDate(1525360159)//开始时间 2018-05-03 23:09:19
                .setmEndDate(1525360159)//结束时间 2019-09-19 09:53:48 1568858028
                .setLayerView(mLayerView)
                .build()
                .show();
    }

    public void showTimeDialog(View view) {
        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_popview_frame5);
        Popi.getBuilder()
                .setmPopId(3)
                .setmPriority(1)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowCount(5)
                .setLayerView(mLayerView)
                .build()
                .show();
    }


    public void showPriorityDialog(View view) {
        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_popview_frame1);
        Popi.getBuilder()
                .setmPopId(4)
                .setmPriority(10)
                .setmCancelType(TRIGGER_CANCEL)
                .setLayerView(mLayerView)
                .build()
                .pushToQueue();

        PopLayerView mLayerView1 = new PopLayerView(this,R.layout.common_popview_frame2);
        Popi.getBuilder()
                .setmPopId(5)
                .setmPriority(2)
                .setmCancelType(TRIGGER_CANCEL)
                .setLayerView(mLayerView1)
                .build()
                .pushToQueue();

        PopManager.getInstance(this).showNextPopi();
    }
}
