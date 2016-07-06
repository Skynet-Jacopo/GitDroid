package com.feicuiedu.gitdroid.repo.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.components.FooterView;
import com.feicuiedu.gitdroid.repo.pager.view.PtrPageView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by liuqun on 6/30/2016.
 */
public class LanguageFragment extends MvpFragment<PtrPageView,LanguagePresenter> implements
        PtrPageView {


    public static final String KEY_LANGUAGE = "key_language";
    @Bind(R.id.lvRepos)
    ListView              mLvRepos;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.emptyView)
    TextView              mEmptyView;
    @Bind(R.id.errorView)
    TextView              mErrorView;

    private ArrayAdapter<String> mAdapter;
    private FooterView           footerView; // 上拉加载更多的视图
    private ActivityUtils mActivityUtils;

    public static LanguageFragment getInstance(String language) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle           bundle   = new Bundle();
        bundle.putSerializable(KEY_LANGUAGE, language);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String getLanguage(){
        return (String) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils = new ActivityUtils(this);
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout
                .simple_list_item_1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    // 重写Mosby库父类MvpFragment的方法,返回当前视图所使用的Presenter对象
    @Override
    public LanguagePresenter createPresenter() {
        return new LanguagePresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLvRepos.setAdapter(mAdapter);

        // 初始下拉刷新
        initPullToRefresh();
        // 初始上拉加载
        initLoadMoreScroll();
        // 如果当前页面没有数据，开始自动刷新
        if (mAdapter.getCount() == 0){
            mPtrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            },200);
        }
    }

    private void initPullToRefresh() {
        // 使用本对象作为key，来记录上一次刷新时间，如果两次下拉间隔太近，不会触发刷新方法
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
        //关闭Header所耗时长
        mPtrClassicFrameLayout.setDurationToCloseHeader(1500);

        // 以下的代码只是一个好玩的Header效果，非什么重要内容
        StoreHouseHeader header =new StoreHouseHeader(getContext());
        header.setPadding(0,60,0,60);
        header.initWithString("I LIKE "+ getLanguage());
        mPtrClassicFrameLayout.setHeaderView(header);
        mPtrClassicFrameLayout.addPtrUIHandler(header);
        //下拉刷新处理
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //执行下拉刷新数据业务
                getPresenter().loadData();
            }
        });
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        // 上拉加载更多(listview滑动动最后的位置了，就可以loadmore)
        Mugen.with(mLvRepos, new MugenCallbacks() {
            // ListView滚动到底部，触发加载更多，此处要执行加载更多的业务逻辑
            @Override
            public void onLoadMore() {
                // 执行上拉加载数据业务
                getPresenter().loadMore();
            }

            // 是否正在加载，此方法用来避免重复加载
            @Override
            public boolean isLoading() {
                return mLvRepos.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            // 是否所有数据都已加载
            @Override
            public boolean hasLoadedAllItems() {
                return mLvRepos.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        mPtrClassicFrameLayout.autoRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    // 这是上拉加载更多视图层实现------------------------------------------------------
    @Override
    public void addMoreData(List<String> datas) {
        mAdapter.addAll(datas);
    }

    @Override
    public void hideLoadMore() {
        mLvRepos.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreLoading() {
        if (mLvRepos.getFooterViewsCount() == 0) {
            mLvRepos.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void showLoadMoreErro(String msg) {
        if (mLvRepos.getFooterViewsCount() == 0) {
            mLvRepos.addFooterView(footerView);
        }
        footerView.showError(msg);
    }

    @Override
    public void showLoadMoreEnd() {
        if (mLvRepos.getFooterViewsCount() == 0) {
            mLvRepos.addFooterView(footerView);
        }
        footerView.showComplete();
    }

    // 这是下拉刷新视图的实现-------------------------------------------------------
    @Override
    public void showContentView() {
        mPtrClassicFrameLayout.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showErroView(String msg) {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void refreshData(List<String> strings) {
        mAdapter.clear();
        mAdapter.addAll(strings);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {
        mPtrClassicFrameLayout.refreshComplete();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
