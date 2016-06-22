package com.yeungeek.publictech.proxy;

import android.util.Log;

/**
 */
public class ProxySubject implements Subject {
    private RealSubject subject;

    @Override
    public void m1() {
        log();
        if (null == subject) {
            subject = new RealSubject();
        }

        subject.m1();
    }

    @Override
    public void m2() {
        if (null == subject) {
            subject = new RealSubject();
        }

        subject.m2();
        log();
    }

    private void log() {
        Log.d("DEBUG", "### log it");
    }
}
