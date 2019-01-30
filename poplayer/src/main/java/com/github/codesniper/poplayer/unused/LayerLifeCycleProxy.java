package com.github.codesniper.poplayer.unused;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author CH
 * Created by mac on 2018/7/10.
 * poplayer生命周期代理管理 控制和代理访问 动态代理
 */
public class LayerLifeCycleProxy {

    private  final String TAG=getClass().getSimpleName();

    private Object target;

    public LayerLifeCycleProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        return returnValue;
                    }
                }

        );
    }
}
