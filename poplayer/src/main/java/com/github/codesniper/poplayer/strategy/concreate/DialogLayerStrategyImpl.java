package com.github.codesniper.poplayer.strategy.concreate;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/6.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.custom.PopDialog;
import com.github.codesniper.poplayer.strategy.LayerLifecycle;


public class DialogLayerStrategyImpl implements LayerLifecycle {

    private int mLayoutRes;
    private PopDialog mDialog;
    private int themeType;
    private PopLayerView popLayerView;

    /**
     * 构造具体的dialog弹窗实现类
     * @param layoutId dialogview的xml布局
     * @param themeResId dialog样式
     */

    public DialogLayerStrategyImpl(int layoutId, int themeResId) {
        this(layoutId,themeResId,null);
    }


    public DialogLayerStrategyImpl(int layoutId, int themeResId,PopLayerView mPopLayerView) {
        this.mLayoutRes = layoutId;
        this.themeType=themeResId;
        this.popLayerView=mPopLayerView;
    }


    @Override
    public void onCreate(Context context) {
        //配置对象
        if(mDialog==null){
            mDialog=new PopDialog(new ContextThemeWrapper(context,themeType));
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //弹窗消失时需要通知 Poplayerview弹窗已经消失
                    if(popLayerView!=null) popLayerView.getPopDismissListener().onPopDimiss();
                }
            });
        }
    }



    /*
      配置view
     */
    @Override
    public void onInit(Context context) {
        View contentView=LayoutInflater.from(context).inflate(mLayoutRes,null,false);
        mDialog.setContentView(contentView);
    }


    @Override
    public void onShow(Context context) {
        if(!mDialog.isShowing()){
            mDialog.show();
        }
    }

    @Override
    public void onDismiss(Context context) {
        if(mDialog!=null){
            mDialog.cancel();
        }
    }

    @Override
    public void onRecycle(Context context) {
         mDialog=null;
    }



}
