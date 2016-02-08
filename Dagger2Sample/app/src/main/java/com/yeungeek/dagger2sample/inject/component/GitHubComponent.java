package com.yeungeek.dagger2sample.inject.component;

import com.yeungeek.dagger2sample.MainActivity;
import com.yeungeek.dagger2sample.inject.module.GitHubModule;
import com.yeungeek.dagger2sample.inject.scope.UserScope;

import dagger.Component;

/**
 * Created by yeungeek on 2016/2/1.
 */
@UserScope // using the previously defined scope, note that @Singleton will not work
@Component(dependencies = NetComponent.class,modules = GitHubModule.class)
public interface GitHubComponent {
    void inject(MainActivity mainActivity);
}
