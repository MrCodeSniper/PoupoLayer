package com.github.codesniper.poplayer.pop;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PopSubscriber<T> implements Observer<T> {


    private Popi popi;
    private Context mContext;
    private boolean isRequestSuccess=false;

    public PopSubscriber(Context context,Popi popi) {
        this.mContext=context;
        this.popi = popi;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(t!=null){
           isRequestSuccess=true;
        }
    }

    @Override
    public void onError(Throwable e) {
        isRequestSuccess=false;
    }

    @Override
    public void onComplete() {
        if(isRequestSuccess){
            StickyPopManager.getInstance(mContext).pushToQueue(popi);
        }else {
            StickyPopManager.getInstance(mContext).clear();
        }
    }
}
