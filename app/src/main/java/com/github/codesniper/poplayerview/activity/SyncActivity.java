package com.github.codesniper.poplayerview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.task.Task;
import com.github.codesniper.poplayer.task.TaskManager;
import com.github.codesniper.poplayer.task.TaskManagerV1;
import com.github.codesniper.poplayerview.R;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class SyncActivity extends Activity {


    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        test();

        //两个接口同时请求
//        request_update();
//        request_notice();
        //请求1先于2完成 且都是成功的情况
        //请求1先于2完成 且请求1 失败 请求2成功

//        onUpdateFail();
//        onNoticeSuccess();


        //请求2先于请求1完成 且都是成功的情况
//        onUpdateSuccess(false);
//        onNoticeSuccess(true);
    }

    private Task taskUpdate;
    private Task taskNotice;
    private Task taskDownload;

    private void test(){

        taskUpdate=new Task();
        taskUpdate.setmPriority(1);
        taskUpdate.setmTaskId(1);

        taskDownload=new Task();
        taskDownload.setmPriority(2);
        taskDownload.setmTaskId(2);

        taskNotice=new Task();
        taskNotice.setmPriority(3);
        taskNotice.setmTaskId(3);






        PopLayerView mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);

        Popi mUpgradePopi = new Popi.Builder()
                .setmPopId(10)
                .setmPriority(5)
                .setmCancelType(COUNTDOWN_CANCEL)
                .setMaxShowTimeLength(5)
                .setLayerView(mLayerView)
                .build();


        PopLayerView mLayerView2 = new PopLayerView(this, LayerConfig.dialog6);

        Popi noticePopi = new Popi.Builder()
                .setmPopId(20)
                .setmPriority(8)
                .setmCancelType(TRIGGER_CANCEL)
                .setMaxShowCount(5)
                .setLayerView(mLayerView2)
                .build();


        //黄色标签弹窗 优先级为6 代表下载进度条
        PopLayerView mLayerView1 = new PopLayerView(this,LayerConfig.dialog5);

        Popi downloadPop = new Popi.Builder()
                .setmPopId(30)
                .setmPriority(6)
                .setmCancelType(TRIGGER_CANCEL)
                .setLayerView(mLayerView1)
                .build();



        TaskManager.getInstance(this)
                .pushToQueue(taskUpdate,mUpgradePopi)
                .pushToQueue(taskDownload,downloadPop)
                .pushToQueue(taskNotice,noticePopi);

    }


    private void onUpdateSuccess(boolean delay){
        if(delay){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance(SyncActivity.this).onTaskGoOn(taskUpdate);
                }
            },2000);
        }else {
            TaskManager.getInstance(this).onTaskGoOn(taskUpdate);
        }
        onDownloadSuccess(false);
    }

    private void onUpdateFail(){
        TaskManagerV1.getInstance(this).onTaskInterupt(taskUpdate);
    }


    private void onNoticeSuccess(boolean delay){
        if(delay){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance(SyncActivity.this).onTaskGoOn(taskNotice);
                }
            },2000);
        }else {
            TaskManager.getInstance(SyncActivity.this).onTaskGoOn(taskNotice);
        }
    }


    private void onNoticeFail(){

    }


    private void onDownloadSuccess(boolean delay){
        if(delay){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance(SyncActivity.this).onTaskGoOn(taskDownload);
                }
            },2000);
        }else {
            TaskManager.getInstance(SyncActivity.this).onTaskGoOn(taskDownload);
        }
    }


    private void onDownloadFail(){

    }


    //两个网络请求
    public void request_update(){ }

    public void request_notice(){}

    public void downloadApk(){}
}
