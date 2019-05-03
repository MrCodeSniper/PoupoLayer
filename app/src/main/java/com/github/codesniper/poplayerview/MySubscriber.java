package com.github.codesniper.poplayerview;

import android.content.Context;

import com.github.codesniper.poplayer.task.PopRxSubscriber;
import com.github.codesniper.poplayer.task.Task;

public class MySubscriber extends PopRxSubscriber {

    public MySubscriber(Context mContext, Task task) {
        super(mContext, task);
    }
}
