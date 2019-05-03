package com.github.codesniper.poplayer.custom;

/**
   统一
 */
public  interface IPop{

   //整合基础原生组件
    void onPopTouch(int touchStatus);

    //根据传入的class获取View
    <T> T getView(Class<T> service);
}
