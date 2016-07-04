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
import com.feicuiedu.gitdroid.components.FooterView;
import com.feicuiedu.gitdroid.presenter.ReopListPresenter;
import com.feicuiedu.gitdroid.view.PtrPageView;
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

/**
 * Created by liuqun on 6/30/2016.
 */
public class RepoListFragment extends MvpFragment<PtrPageView,ReopListPresenter> implements
        PtrPageView {


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

    public static RepoListFragment getInstance(String language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle           bundle   = new Bundle();
        bundle.putSerializable("key_language", language);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    @Override
    public ReopListPresenter createPresenter() {
        return new ReopListPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout
                .simple_list_item_1);
        mLvRepos.setAdapter(mAdapter);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getPresenter().loadData();
            }
        });

        footerView = new FooterView(getContext());
        // 上拉加载更多(listview滑动动最后的位置了，就可以loadmore)
        Mugen.with(mLvRepos, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "loadmore", Toast.LENGTH_SHORT).show();
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
