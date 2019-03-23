package com.github.codesniper.poplayer.task;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PopRxSubscriber<T> implements Observer<T> {

    private Context mContext;
    private Task mTask;
    private boolean isSuccess=false;

    public PopRxSubscriber(Context mContext, Task task) {
        this.mContext = mContext;
        this.mTask=task;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public void onNext(T t) {
        if(t!=null){
           isSuccess=true;
        }else {
            isSuccess=false;
        }
    }

    @Override
    public void onError(Throwable e) {
        isSuccess=false;
    }

    @Override
    public void onComplete() {
        if(isSuccess){
            TaskManager.getInstance(mContext).onTaskGoOn(mTask);
        }else {
            TaskManager.getInstance(mContext).onTaskInterupt(mTask);
        }
    }


}
