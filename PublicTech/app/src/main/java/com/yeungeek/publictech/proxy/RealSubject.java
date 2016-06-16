package com.yeungeek.publictech.proxy;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by yeungeek on 2016/6/16.
 */
public class RealSubject implements Subject{
    @Override
    public void m1() {
        SystemClock.sleep(1000);
        Log.d("DEBUG","##### call method 1");
    }

    @Override
    public void m2() {
        SystemClock.sleep(2000);
        Log.d("DEBUG","##### call method 2");
    }
}
