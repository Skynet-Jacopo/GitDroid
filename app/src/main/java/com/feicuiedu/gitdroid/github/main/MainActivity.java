package com.feicuiedu.gitdroid.github.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.login.LoginActivity;
import com.feicuiedu.gitdroid.github.login.model.CurrentUser;
import com.feicuiedu.gitdroid.github.hotrepo.HotRepoFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView)
    NavigationView mNavigationView;//侧滑菜单视图
    @Bind(R.id.drawerLayout)
    DrawerLayout   mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar        mToolbar;

    private ActivityUtils   mActivityUtils;
    private HotRepoFragment mHotRepoFragment;

    private     Button    mBtnLogin;
    private ImageView ivIcon; // 用户头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //实例化ActivityUtils
        mActivityUtils = new ActivityUtils(this);

        //ActionBar 处理
        setSupportActionBar(mToolbar);
        //设置mNavigationView的监听器
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置ToolBar左上角切换侧滑菜单的按钮
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,
                mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R
                .string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        //同步
        toggle.syncState();
        //登录
        mBtnLogin = ButterKnife.findById(mNavigationView.getHeaderView(0),R
                .id.btnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityUtils.startActivity(LoginActivity.class);
            }
        });
        ivIcon =ButterKnife.findById(mNavigationView.getHeaderView(0),R.id.ivIcon);
        //mToolbar右边三个点
        mToolbar.inflateMenu(R.menu.mymenu);

        //设置mNavigationView的监听事件
        mNavigationView.setNavigationItemSelectedListener(this);

        mHotRepoFragment = new HotRepoFragment();
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.container,mHotRepoFragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 还没有授权登陆
        if (CurrentUser.isEmpty()){
            mBtnLogin.setText(R.string.login_github);
            return;
        }
        // 已经授权登陆
        mBtnLogin.setText(R.string.switch_account);
        getSupportActionBar().setTitle(CurrentUser.getUser().getName());
        // 设置用户头像
        String photoUrl = CurrentUser.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "点急了弄你!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings1:
                Toast.makeText(MainActivity.this, "刘承宇:刘承宇吃屁!", Toast
                        .LENGTH_SHORT).show();
                break;
            case R.id.action_settings2:
                Toast.makeText(MainActivity.this, "王智:刘承宇吃屁!", Toast
                        .LENGTH_SHORT).show();
                break;
            case R.id.action_settings3:
                Toast.makeText(MainActivity.this, "郭勤波:刘承宇吃屁!", Toast
                        .LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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