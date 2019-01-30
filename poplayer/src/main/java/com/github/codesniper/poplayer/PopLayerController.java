package com.github.codesniper.poplayer;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.github.codesniper.poplayer.impl.PushManagerImpl;
import com.github.codesniper.poplayer.impl.TimerManagerImpl;
import com.github.codesniper.poplayer.interfaces.LayerTouchSystem;
import com.github.codesniper.poplayer.interfaces.PushManager;
import com.github.codesniper.poplayer.interfaces.RouterManager;
import com.github.codesniper.poplayer.interfaces.TimerManager;

/**
 * 装配能力类 如果需要具体的能力 通过此类获得 像接口实现类的工厂
 */
public class PopLayerController {

    //功能组成
    private TimerManager timerManagerImpl;
    private PushManager pushManagerImpl;
    private RouterManager navigationManagerImpl;
    private LayerTouchSystem layerTouchImpl;

    //添加单例 使用内部类实现真正的延迟加载
    //当getInstance方法第一次被调用的时候，它第一次读取SingletonHolder.instance，
    // 内部类SingletonHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静态域，
    // 从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，
    // 并由虚拟机来保证它的线程安全性。

//    public static class Holder {
//        static HrzLayer INSTANCE=new HrzLayer(builders);
//    }
//
//    public static HrzLayer getInstance(Builder builder){
//        // 外围类能直接访问内部类（不管是否是静态的）的私有变量
//        builders=builder;
//        return Holder.INSTANCE;
//    }

    public PopLayerController(Builder builder){//私有化构造
        initialize(builder);
   }

    private void initialize(Builder builder) {
        this.timerManagerImpl=builder.timerManagerImpl;
        this.pushManagerImpl=builder.pushManagerImpl;
        this.navigationManagerImpl=builder.navigationManagerImpl;
        this.layerTouchImpl=builder.layerTouchImpl;
    }

    public TimerManager getTimerManagerImpl() {
        return timerManagerImpl;
    }

    public PushManager getPushManagerImpl() {
        return pushManagerImpl;
    }

    public RouterManager getNavigationManagerImpl() {
        return navigationManagerImpl;
    }

    public LayerTouchSystem getLayerTouchImpl() {
        return layerTouchImpl;
    }

    //////////////////////////////////内部类builder 配置适配层接口//////////////////////////////////////////////

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private TimerManager timerManagerImpl;
        private PushManager pushManagerImpl;
        private RouterManager navigationManagerImpl;
        private LayerTouchSystem layerTouchImpl;

        public Builder setTimerManagerImpl(TimerManagerImpl timerManagerImpls) {
            this.timerManagerImpl = timerManagerImpls;
            return this;
        }

        public Builder setPushManagerImpl(PushManagerImpl pushManagerImpls) {
            this.pushManagerImpl = pushManagerImpls;
            return this;
        }

        public Builder setLayerTouchImpl(LayerTouchSystem layerTouchImpl) {
            this.layerTouchImpl = layerTouchImpl;
            return this;
        }

        public PopLayerController build(){
            return new PopLayerController(this);
        }

    }


}
