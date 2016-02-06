package com.yeungeek.dagger2sample;

import android.app.Application;

import com.yeungeek.dagger2sample.inject.component.DaggerNetComponent;
import com.yeungeek.dagger2sample.inject.component.NetComponent;
import com.yeungeek.dagger2sample.inject.module.AppModule;
import com.yeungeek.dagger2sample.inject.module.NetModule;

/**
 * Created by yeungeek on 2016/2/1.
 */
public class SampleApp extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // Dagger%COMPONENT_NAME%
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.github.com"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
