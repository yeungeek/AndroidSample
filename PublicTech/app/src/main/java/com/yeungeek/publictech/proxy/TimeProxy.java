package com.yeungeek.publictech.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yeungeek on 2016/6/16.
 */
public class TimeProxy implements InvocationHandler {
    private Object delegate;

    public Object bind(Object delegate){
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),delegate.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = method.invoke(delegate, args);
        Log.d("DEBUG", "#### cost time: " + (System.currentTimeMillis() - start));
        return obj;
    }
}