package com.github.codesniper.poplayer.strategy.concreate;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/6.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.github.codesniper.poplayer.custom.newPop.IWindowEvent;
import com.github.codesniper.poplayer.custom.newPop.PopDialog;
import com.github.codesniper.poplayer.custom.newPop.IWindow;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;
import com.github.codesniper.poplayer.task.TaskManagerV1;


public class  DialogLayerStrategyImpl implements LayerLifecycle {

    private Context mContext;

    private IWindow<Dialog> mDialog;


    //策略需要注意的是 布局和主题
    private int mLayoutRes;
    private int themeResId;

    //需要把这里发生的事件发送出去
    private IWindowEvent windowEvent;

    public void setWindowEvent(IWindowEvent windowEvent) {
        this.windowEvent = windowEvent;
    }

    public DialogLayerStrategyImpl(Context context, int layoutId, int themeResId) {
        this.mContext=context;
        this.mLayoutRes = layoutId;
        this.themeResId=themeResId;
    }
    public DialogLayerStrategyImpl(Context context,int layoutId) {
        this.mContext=context;
        this.mLayoutRes = layoutId;
    }

    public DialogLayerStrategyImpl(Context context,IWindow window) {
        this.mContext=context;
        this.mDialog = window;
    }


    @Override
    public void createLayerView() {
      if(mDialog==null){
          if(themeResId==0){
              this.mDialog=new PopDialog(mContext);
          }else {
              this.mDialog=new PopDialog(new ContextThemeWrapper(mContext,themeResId));
          }
      }
      //给自定义Dialog或者继承自自己的dialog都加上监听
        mDialog.getPoupo().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(windowEvent!=null) windowEvent.onWindowDismiss();
            }
        });
    }


    @Override
    public void initLayerView() {
        if(mLayoutRes!=0){
            View contentView=LayoutInflater.from(mContext).inflate(mLayoutRes,null,false);
            mDialog.setContent(contentView);
        }
    }


    @Override
    public void showLayer() {
        if(!mDialog.isPoupoShowing()){
            mDialog.showPoupo();
            if(windowEvent!=null) windowEvent.onWindowShow();
        }
    }

    @Override
    public void dissmissLayer() {
        if(mDialog!=null){
            mDialog.dismissPoupo();
            if(windowEvent!=null) windowEvent.onWindowDismiss();
        }
    }

    @Override
    public void recycleLayer() {
        if(mDialog!=null){
            mDialog.cancelPoupo();
            if(windowEvent!=null) windowEvent.onWindowDismiss();
        }
    }



    @Override
    public Context getLayerContext() {
        return mDialog.getEnvironment();
    }

    @Override
    public boolean isShowing() {
        return mDialog.isPoupoShowing();
    }

    @Override
    public IWindow getWindowView() {
        return mDialog;
    }

    @Override
    public View getViewById(int id) {
        return mDialog.getPoupo().findViewById(id);
    }
}
