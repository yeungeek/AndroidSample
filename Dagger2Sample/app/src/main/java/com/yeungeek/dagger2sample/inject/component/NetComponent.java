package com.yeungeek.dagger2sample.inject.component;

import android.content.SharedPreferences;

import com.squareup.okhttp.OkHttpClient;
import com.yeungeek.dagger2sample.inject.module.AppModule;
import com.yeungeek.dagger2sample.inject.module.NetModule;
import com.yeungeek.dagger2sample.network.GithubApiInterface;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

/**
 * Created by yeungeek on 2016/2/1.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed
    Retrofit retrofit();

    OkHttpClient okhttpClient();

    SharedPreferences sharedPreferences();
}
