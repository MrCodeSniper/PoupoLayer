package com.github.codesniper.poplayerview.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class MyLock implements Lock {

    //抢占式锁


    //抢不到的放入等待列表 线程安全的链队
    LinkedBlockingQueue<Thread> waitQueue;

    //执行完毕后需要通知其他线程来抢

    //执行的代码使用AtomicReference


    private Thread thread;




    AtomicReference<Thread> atomicReference;

    @Override
    public void lock() {
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){
            //当前线程抢占失败
            waitQueue.add(Thread.currentThread());
            LockSupport.park();//阻塞当前线程
            waitQueue.remove(Thread.currentThread());//unpark后往下面走
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if(atomicReference.compareAndSet(Thread.currentThread(),null)){ //释放之后设置为空
            //把等待队列中的所有线程释放出来
            Thread[] threads=new Thread[waitQueue.size()];
             waitQueue.toArray(threads);
            for (int i=0;i<threads.length-1;i++) {
                LockSupport.unpark(threads[i]);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
