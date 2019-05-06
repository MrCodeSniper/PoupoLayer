package com.github.codesniper.poplayer.custom;

/**
 * 弹窗管理的回调
 */
public interface PopCallback {

    //弹窗已经存在于队列中
    void onPopExisted(int queueSize);

    //弹窗不在活动时间内
    void onPopOutOfDate();

    //弹窗已经显示了最大个数
    void onPopShowMaxCount();

    //弹窗显示成功回调
    void onPopShowSuccess();

    //弹窗延迟消失回调
    void onPopDelayDismiss();

}
