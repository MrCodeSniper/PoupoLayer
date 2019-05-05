package com.github.codesniper.poplayer.custom.newPop;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.codesniper.poplayer.custom.IPop;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.task.TaskManagerV1;

/**
 *  框架基类Dialog弹窗
 */
public class PopDialog extends Dialog implements IPop, IWindow<Dialog> {


    public PopDialog(@NonNull Context context) {
        super(context);
    }

    public PopDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public PopDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog(){
        Window window=getWindow();
        if(window!=null){
            window.setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams wl=window.getAttributes();
            wl.gravity= Gravity.CENTER;
            window.setAttributes(wl);
            show();
        }
    }

    @Override
    public void onPopTouch(int touchStatus) {

    }

    @Override
    public <T> T getView(Class<T> service) {
        return (T) this;
    }


    ///////////////////////////////////////////////////////////////////////////
    // 统一回调
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void setContent(View view) {
        setContentView(view);
    }

    @Override
    public void showPoupo() {
        showDialog();
    }

    @Override
    public void cancelPoupo() {
        cancel();
    }

    @Override
    public void dismissPoupo() {
        dismiss();
    }

    @Override
    public PopDialog getPoupo() {
        return this;
    }

    @Override
    public Context getEnvironment() {
        return getContext();
    }

    @Override
    public boolean isPoupoShowing() {
        return isShowing();
    }
}


