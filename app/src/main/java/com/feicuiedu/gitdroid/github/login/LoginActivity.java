package com.feicuiedu.gitdroid.github.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.github.main.MainActivity;
import com.feicuiedu.gitdroid.github.network.GitHubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by liuqun on 7/5/2016.
 */
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter>
        implements LoginView {

    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.webView)
    WebView      mWebView;
    @Bind(R.id.gifImageView)
    GifImageView mGifImageView;

    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivityUtils = new ActivityUtils(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
    }

    private void initWebView() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        mWebView.loadUrl(GitHubApi.AUTH_URL);
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 登陆成功后github将重定向一个url路径
            // 我们从中对比，取出code(临时授权值, 在真正授权时一定要的值)
            // 进行授权接口操作,且获取到你的基本信息
            Uri uri = Uri.parse(url);
            // 检测加载到的新URL是否是用我们规定好的CALL_BACK开头的
            if (GitHubApi.CALL_BACK.equals(uri.getScheme())) {
                // 获取授权码
                String code = uri.getQueryParameter("code");
                LogUtils.d("-------- " + code);
                // 执行登陆的操作Presenter
                getPresenter().login(code);
                //退出当前WebView
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mGifImageView.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }
        }
    };

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        mGifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void navigateToMain() {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
