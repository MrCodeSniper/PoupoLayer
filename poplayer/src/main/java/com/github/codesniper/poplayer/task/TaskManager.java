package com.github.codesniper.poplayer.task;

import android.content.Context;
import android.util.Log;

import com.github.codesniper.poplayer.config.PopDismissListener;
import com.github.codesniper.poplayer.pop.PopManager;
import com.github.codesniper.poplayer.pop.Popi;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskManager implements PopDismissListener {

    private Context mContext;

    //每添加完一个元素都会进行堆排序对队列进行优先级调整  先入先出
    private  PriorityQueue<Task> queue;



    private static TaskManager mInstance;

    private HashMap<Task,Popi> relationMap;

    public static TaskManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (TaskManager.class) {
                if (mInstance == null) {
                    mInstance = new TaskManager(context);
                }
            }
        }
        return mInstance;
    }

    private TaskManager(Context context) {
        mContext = context;
        if(queue==null){
            queue=new PriorityQueue<>();
        }
        if(relationMap==null){
            relationMap=new HashMap<>();
        }
    }


    public void pushToReserve(Popi popi){
        PopManager.getInstance(mContext).pushToQueue(popi);
    }



    /**
     * 将弹窗实体 推入队列中
     */
    public TaskManager pushToQueue(Task task, Popi popi) {
        relationMap.put(task,popi);
        //如果队列中存在此弹窗 不重复加入
        if (!isAlreadyInQueue(task, queue)) {
            queue.add(task);
        }else {

        }
        Log.e("TaskManagerV1", "QueueSize:" + queue.size());
        return this;
    }

    /**
     * 遍历队列 以ID作为唯一标识
     */
    private boolean isAlreadyInQueue(Task task, Queue<Task> queue) {
        for (Task item : queue) {
            if (item.getmTaskId() == task.getmTaskId()) {
                return true;
            }
        }
        return false;
    }


    public void onTaskGoOn(Task task){
        //目前的task优先级判断是否在队列中是最高的
        //是最高的 显示对应的弹窗
        Popi popi=relationMap.get(task);
        if(isTaskFirstPriority(task)){
            Log.d("hrz","当前task是优先级最高的");
            //显示弹窗
            if(popi!=null){
                pushToReserve(popi);
                PopManager.getInstance(mContext).showNextPopi();
            }
        }else {  //不是优先级最高的 进入保留队列
            Log.d("hrz","当前task不是优先级最高的");
            if(popi!=null)   pushToReserve(popi);
        }
    }

    private boolean isTaskFirstPriority(Task task){
        //拿到队列中的第一个如果是当前task 说明当前task优先值最高
        Task firstTask=queue.peek();
        if(firstTask!=null&&firstTask==task){
            return true;
        }else {
            return false;
        }
    }


    public void onTaskInterupt(Task task){
        //队列清除当前task map清除task和popi
        //如果失败 取消关联 从队列中移除
        relationMap.remove(task);
        queue.remove(task);
    }


    //弹窗消失
    @Override
    public void onPopDimiss() {
        //会掉两个manager的这个方法


    }



}
