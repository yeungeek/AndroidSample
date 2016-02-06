package com.yeungeek.dagger2sample.inject.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yeungeek on 2016/2/1.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
