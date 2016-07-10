package com.feicuiedu.gitdroid.github.login;

import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.CurrentUser;
import com.feicuiedu.gitdroid.github.login.model.User;
import com.feicuiedu.gitdroid.github.network.GitHubApi;
import com.feicuiedu.gitdroid.github.network.GitHubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuqun on 7/5/2016.
 * <p/>
 * 此类是处理登录操作的,并在登录的过程中将触发调用LoginView
 * <p/>
 * 登录过程遵循标准的OAuth2.0协议
 *
 * 用户通过WebView登录GitHub网站，如果登录成功，且用户给我们授权，GitHub会访问我们的回调地址，给我们一个授权码。
 *
 * 我们就能过授权码去获得访问令牌, 最终就有权利访问信息了.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call<AccessTokenResult> mTokenResultCall;
    private Call<User> mUserCall;

    public void login(String code){
        getView().showProgress();
        if (mTokenResultCall != null) mTokenResultCall.cancel();
        mTokenResultCall = GitHubClient.getInstance().getOAthToken(GitHubApi
                .CLIENT_ID,GitHubApi.CLIENT_SECRET,code);
        mTokenResultCall.enqueue(tokenCallback);
    }

    private Callback<AccessTokenResult> tokenCallback =new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            // 保存token到内存里面
            String token = response.body().getAccessToken();
            CurrentUser.setAccessToken(token);
            // 再次业务操作
            // 再使用这个token去获取当前已认证的用户信息
            // 从而拿到你的名称，头像....
            if (mUserCall != null) {
                mUserCall.cancel();
            }
            mUserCall = GitHubClient.getInstance().getUserInfo();
            mUserCall.enqueue(userCallback);
        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            getView().showMessage("Fail:" + t.getMessage());
            // 失败，重置WebView
            getView().showProgress();
            getView().resetWeb();
        }
    };

    // 获取用户信息的回调
    private Callback<User> userCallback =new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            // 保存user到内存里面
            User user =response.body();
            CurrentUser.setUser(user);
            // 导航至主页面
            getView().showMessage("登陆成功");
            getView().navigateToMain();
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            // 清除缓存的用户信息，
            CurrentUser.clear();
            getView().showMessage("Fail:" + t.getMessage());
            // 重置WebView
            getView().showProgress();
            getView().resetWeb();
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            if (mTokenResultCall != null) {
                mTokenResultCall.cancel();
            }
        }
    }
}
