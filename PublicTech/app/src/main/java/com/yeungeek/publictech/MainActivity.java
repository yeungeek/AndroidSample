package com.yeungeek.publictech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.yeungeek.publictech.annotation.SimplePojo;
import com.yeungeek.publictech.proxy.RealSubject;
import com.yeungeek.publictech.proxy.Subject;
import com.yeungeek.publictech.proxy.TimeProxy;
import com.yeungeek.publictech.reflection.ReflectionActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.id_annotation)
    public void onAnnotation(Button btn) {
        btn.setText(new SimplePojo("s1,", "s2").toString());
    }

    @OnClick(R.id.id_reflection)
    public void onReflection() {
        startActivity(new Intent(this, ReflectionActivity.class));
    }

    @OnClick(R.id.id_proxy)
    public void onProxy() {
        RealSubject delegate = new RealSubject();
        Subject subject = (Subject) new TimeProxy().bind(delegate);
        subject.m1();
        subject.m2();
    }
}
