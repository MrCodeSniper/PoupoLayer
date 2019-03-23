package com.github.codesniper.poplayerview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.task.Task;
import com.github.codesniper.poplayer.task.TaskManagerV1;
import com.github.codesniper.poplayer.task.TaskManager;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler=new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
        //两个接口同时请求
        request_update();
        request_notice();
        //请求1先于2完成 且都是成功的情况
        //请求1先于2完成 且请求1 失败 请求2成功

//        onUpdateFail();
//        onNoticeSuccess();


        //请求2先于请求1完成 且都是成功的情况
        onUpdateSuccess(false);
        onNoticeSuccess(true);
    }


    private void onUpdateSuccess(boolean delay){
        if(delay){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance(MainActivity.this).onTaskGoOn(taskUpdate);
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
                    TaskManager.getInstance(MainActivity.this).onTaskGoOn(taskNotice);
                }
            },2000);
        }else {
            TaskManager.getInstance(MainActivity.this).onTaskGoOn(taskNotice);
        }
    }


    private void onNoticeFail(){

    }


    private void onDownloadSuccess(boolean delay){
        if(delay){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance(MainActivity.this).onTaskGoOn(taskDownload);
                }
            },2000);
        }else {
            TaskManager.getInstance(MainActivity.this).onTaskGoOn(taskDownload);
        }
    }


    private void onDownloadFail(){

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


    //两个网络请求
    public void request_update(){ }

    public void request_notice(){}

    public void downloadApk(){}





    public void toDialogSubject(View view){
        startActivity(new Intent(this,DialogActivity.class));
    }

    public void toWebSubject(View view){
        startActivity(new Intent(this,WebviewActivity.class));
    }



}
