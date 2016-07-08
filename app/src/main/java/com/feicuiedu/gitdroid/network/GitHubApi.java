package com.feicuiedu.gitdroid.network;


import com.feicuiedu.gitdroid.github.hotrepo.pager.modle.RepoResult;
import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public interface GitHubApi {
    // GitHub开发者，申请时填写的(重定向返回时的一个标记)
    String CALL_BACK     = "jacopo";
    // GitHub开发者，申请就行
    String CLIENT_ID     = "7d623e801aa41ba670bf";
    String CLIENT_SECRET = "fc514f95cbcabbb6e108c4b17d20d0034832eddd";


    // 授权时申请的可访问域
    String INITIAL_SCOPE = "user,public_repo,repo";

    // WebView来加载此URL,用来显示GitHub的登陆页面
    String AUTH_URL =
            "https://github.com/login/oauth/authorize?client_id=" +
                    CLIENT_ID + "&" + "scope=" + INITIAL_SCOPE;

    /**
     * 获取訪問令牌API
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    /**
     * @return 获取用户信息
     */
    @GET("user")
    Call<User> getUserInfo();

    /**
     * @param query  查询参数           java [232323123] items[{},{},{}]
     * @param pageId 查询页数，从1开始
     * @return 查询结果
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepo(
            @Query("q") String query,
            @Query("page") int pageId);

}
