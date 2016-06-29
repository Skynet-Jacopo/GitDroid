package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.feicuiedu.gitdroid.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView)
    NavigationView mNavigationView;//侧滑菜单视图
    @Bind(R.id.drawerLayout)
    DrawerLayout   mDrawerLayout;

    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        //设置mNavigationView的监听事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                mActivityUtils.showToast("最热门");
                break;
            case R.id.github_hot_coder:
                mActivityUtils.showToast("开发者");
                break;
            case R.id.github_trend:
                mActivityUtils.showToast("流行趋势");
                break;
            case R.id.arsenal_my_repo:
                mActivityUtils.showToast("我的收藏");
                break;
            case R.id.arsenal_recommend:
                mActivityUtils.showToast("推荐");
                break;
            case R.id.tips_daily:
                mActivityUtils.showToast("每日干货");
                break;
            case R.id.tips_share:
                mActivityUtils.showToast("分享");
                break;
        }
        //返回true,代表该菜单项变为checked状态
        return true;
    }

    @Override
    public void onBackPressed() {

        //如果mNavigationView是开着的-->关闭
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);//关闭左侧侧滑菜单mNavigationView
        }
        //如果mNavigationView是关的-->退出当前Activity
        else {
            super.onBackPressed();
        }
    }
}