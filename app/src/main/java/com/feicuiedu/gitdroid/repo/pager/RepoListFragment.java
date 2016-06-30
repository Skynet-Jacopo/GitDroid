package com.feicuiedu.gitdroid.repo.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicuiedu.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by liuqun on 6/30/2016.
 */
public class RepoListFragment extends Fragment {


    @Bind(R.id.lvRepos)
    ListView              mLvRepos;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;

    private ArrayAdapter<String> mAdapter;
    private List<String> datas = new ArrayList<>();

    private static int mCount;

    public static RepoListFragment getInstance(String language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle           bundle   = new Bundle();
        bundle.putSerializable("key_language", language);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        for (int i = 0; i < 20; i++) {
//            mCount++;
//            datas.add("我是第" + mCount + "条数据");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout
                .simple_list_item_1);
        mLvRepos.setAdapter(mAdapter);
        mAdapter.addAll(datas);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(20);
            }
        });
    }

    private void loadData(int size) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //只是测试
                try {
                    datas.clear();
                    Thread.sleep(3000);
                    for (int i = 0; i < 20; i++) {
                        mCount++;
                        datas.add("我是第" + mCount + "条数据");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //到UI线程中完成视图工作
                mPtrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mAdapter.addAll(datas);
                        mAdapter.notifyDataSetChanged();
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                });
            }
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
