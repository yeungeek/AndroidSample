package com.yeungeek.dagger2sample.network;

import com.yeungeek.dagger2sample.network.models.Repository;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yeungeek on 2016/2/1.
 */
public interface GithubApiInterface {
    @GET("/orgs/{orgName}/repos")
    Call<ArrayList<Repository>> getRepository(@Path("orgName") String orgName);
}
