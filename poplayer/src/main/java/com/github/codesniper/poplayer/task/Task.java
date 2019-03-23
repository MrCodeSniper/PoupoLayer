package com.github.codesniper.poplayer.task;

import com.github.codesniper.poplayer.pop.Popi;


/**
 *  以任务请求作为划分粒度
 *  每个task代表一次网络请求
 *  并具有任务的优先级
 */
public class Task implements Comparable<Task> {

    //优先级越小越好 最好不要为负数
    private int mPriority;

    private int mTaskId;


    public int getmTaskId() {
        return mTaskId;
    }

    public void setmTaskId(int mTaskId) {
        this.mTaskId = mTaskId;
    }

    public int getmPriority() {
        return mPriority;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }

    @Override
    public int compareTo(Task o) {
        if (this.mPriority > o.getmPriority()) {
            return 1;
        } else if (this.mPriority < o.getmPriority()) {
            return -1;
        } else {
            return 0;
        }
    }
}
