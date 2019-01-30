package com.github.codesniper.poplayer.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 *  自定义dialog解决黑背景问题
 */
public class PopDialog extends Dialog implements IPop{

    public PopDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
