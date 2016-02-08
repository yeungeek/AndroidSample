package com.yeungeek.dagger2sample.inject.module;

import com.yeungeek.dagger2sample.inject.scope.UserScope;
import com.yeungeek.dagger2sample.network.GithubApiInterface;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by yeungeek on 2016/2/1.
 */
@Module
public class GitHubModule {
    @Provides
    @UserScope // needs to be consistent with the component scope
    public GithubApiInterface providesGithubApiInterface(Retrofit retrofit) {
        return retrofit.create(GithubApiInterface.class);
    }
}
