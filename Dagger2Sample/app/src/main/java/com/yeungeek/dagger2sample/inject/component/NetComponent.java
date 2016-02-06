package com.yeungeek.dagger2sample.inject.component;

import com.yeungeek.dagger2sample.MainActivity;
import com.yeungeek.dagger2sample.inject.module.AppModule;
import com.yeungeek.dagger2sample.inject.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yeungeek on 2016/2/1.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
}
