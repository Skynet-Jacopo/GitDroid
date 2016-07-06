package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.login.model.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public class GitHubClient implements GitHubApi {

    private static GitHubClient sClient;

    public static GitHubClient getInstance(){
        if (sClient == null) {
            sClient = new GitHubClient();
        }
        return sClient;
    }

    private final GitHubApi mGitHubApi;

    private GitHubClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())// 添加拦截器, 处理Log (注意添加依赖包)
                .addInterceptor(new TokenInterceptor()) // 添加拦截器, 处理AccessToken
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mGitHubApi =retrofit.create(GitHubApi.class);
    }

    @Override
    public Call<AccessTokenResult> getOAthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return mGitHubApi.getOAthToken(client,clientSecret,code);
    }

    @Override
    public Call<User> getUserInfo() {
        return mGitHubApi.getUserInfo();
    }
}
