package com.github.codesniper.poplayer.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.pop.PopManager;

/**
 *  自定义dialog解决黑背景问题
 */
public class PopDialog extends Dialog implements IPop{


    public PopDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                PopManager.getInstance(getContext()).onPopDimiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    @Override
    public void onPopTouch(int touchStatus) {

    }
}
