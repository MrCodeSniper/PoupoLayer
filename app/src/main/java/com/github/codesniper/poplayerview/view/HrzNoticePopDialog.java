package com.github.codesniper.poplayerview.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.github.codesniper.poplayer.custom.newPop.IWindow;
import com.github.codesniper.poplayer.custom.newPop.PopDialog;
import com.github.codesniper.poplayerview.R;

import java.util.Date;


public class HrzNoticePopDialog extends Dialog implements IWindow<Dialog> {

    public HrzNoticePopDialog(@NonNull Context context) {
        super(context);
    }

    public HrzNoticePopDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_popview_frame);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
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
    public Dialog getPoupo() {
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
