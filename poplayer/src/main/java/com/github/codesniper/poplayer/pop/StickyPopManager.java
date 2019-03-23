package com.github.codesniper.poplayer.pop;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.custom.PopCallback;
import com.github.codesniper.poplayer.util.PopUtils;
import com.github.codesniper.poplayer.util.SPUtils;

import java.util.PriorityQueue;
import java.util.Queue;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.POP_TAG;


/**
 * 弹窗队列管理
 */
public class StickyPopManager implements PopDismissListener {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    private PopCallback popCallback;

    public void setPopCallback(PopCallback popCallback) {
        this.popCallback = popCallback;
    }

    //每添加完一个元素都会进行堆排序对队列进行优先级调整  先入先出
    private  PriorityQueue<Popi> queue;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPopi.getContent().dissmissLayer(mContext);
        }
    };

    private static StickyPopManager mInstance;


    public static StickyPopManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (StickyPopManager.class) {
                if (mInstance == null) {
                    mInstance = new StickyPopManager(context);
                }
            }
        }
        return mInstance;
    }

    private StickyPopManager(Context context) {
        mContext = context;
        if(queue==null){
            queue=new PriorityQueue<>();
        }
    }

    private Popi mPopi;

    //队列中有元素才显示
    public boolean canShow() {
        return queue.size() > 0;
    }

    /**
     * 将弹窗实体 推入队列中
     */
    public void pushToQueue(Popi popi) {
        //如果队列中存在此弹窗 不重复加入
        if (!isAlreadyInQueue(popi, queue)) {
            queue.add(popi);
        }else {
            if(popCallback!=null) popCallback.onPopExisted(queue.size());
        }
        Log.e(TAG, "QueueSize:" + queue.size());
    }

    /**
     * 遍历队列 以ID作为唯一标识
     */
    private boolean isAlreadyInQueue(Popi popi, Queue<Popi> queue) {
        for (Popi item : queue) {
            if (item.getPopId() == popi.getPopId()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 只有当前队列中 弹窗为1 才能进行
     */
    public void showNextPopi() {
        if (!canShow()) {
            Log.e(TAG, "队列为空");
            return;
        }

        mPopi = queue.element();
        if (mPopi == null) {
            Log.e(TAG, "队列为空");
        } else {

            //不在限定时间范围
            if (isPopDateOut(mPopi)) {
                //移出队中
                queue.remove(mPopi);
                Log.e(TAG, "不在限定时间");
                if(popCallback!=null) popCallback.onPopOutOfDate();
                removeTopPopi();
                return;
            }

            String key = "PopiItem" + mPopi.getPopId();
            Log.e(TAG, key);


            //用sp将显示次数保存  大于显示次数就不显示
            if (mPopi.getMaxShowCount() > 0) {
                Log.e(TAG, "sp:" + SPUtils.getInstance(mContext).getInt(key));
                if (SPUtils.getInstance(mContext).getInt(key) >= mPopi.getMaxShowCount()) {
                    Log.e(TAG, "显示最大次数");
                    if(popCallback!=null) popCallback.onPopShowMaxCount();
                    removeTopPopi();
                    return;
                }
            }

            //显示弹窗
            mPopi.getContent().showLayer(mContext);

            if(popCallback!=null) popCallback.onPopShowSuccess();

            if (mPopi.getCancelType() == COUNTDOWN_CANCEL) {
                Log.e(TAG, "延迟取消");
                delayDimiss(mPopi.getMaxShowTimeLength(), mPopi);
                if(popCallback!=null) popCallback.onPopDelayDismiss();
            }

            //记录 当前弹窗显示的次数
            if (mPopi.getMaxShowCount() > 0 && mPopi.getMaxShowCount() != Integer.MAX_VALUE - 1) {
                int existTime = SPUtils.getInstance(mContext).getInt(key) + 1;
                Log.e(TAG, "已经显示了" + existTime + "次");
                SPUtils.getInstance(mContext).put(key, existTime);
            }
        }
    }


    /*
      清空队列 消失弹窗
     */
    public void clear() {
        queue.clear();
        if (mPopi != null) {
            mPopi.getContent().dissmissLayer(mContext);
            mPopi.getContent().recycleLayer(mContext);
        }
    }


    //出队顶部实体
    public void removeTopPopi() {
        queue.poll();
    }


    //倒计时消失
    private void delayDimiss(long second, final Popi popi) {
        if (popi.getCancelType() != COUNTDOWN_CANCEL) return;
        final long delayTime = second * 1000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime) < delayTime) {

                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    //不在弹窗的显示时间之内
    private boolean isPopDateOut(Popi popi) {
        long nowTime = System.currentTimeMillis() / 1000;
        Log.e(TAG, "nowtime:" + PopUtils.timeStamp2Date(nowTime) + "&&" + PopUtils.timeStamp2Date(mPopi.getBeginDate()) + "&&" + PopUtils.timeStamp2Date(mPopi.getEndDate()));
        if (nowTime > popi.getBeginDate() && nowTime < popi.getEndDate()) {
            return false;
        }
        return true;
    }


    @Override
    public void onPopDimiss() {
        Log.e(POP_TAG, "弹窗消失,显示队列中下个弹窗");
        //当入队的弹窗消失了移除队列头部实体 显示下一个
        removeTopPopi();
        showNextPopi();
    }
}
