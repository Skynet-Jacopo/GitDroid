package com.feicuiedu.gitdroid.github.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuqun on 6/30/2016.
 *
 * Created by Administrator on 2016/6/30 0030.
 * <p/>
 * <p/>最热门仓库Fragment,里面放着不放语言的仓库
 * <p/>
 * <p/>本Fragment是被添加到MainActivity中。
 * <p/>
 * <p/>它上面有一个ViewPager和一个相对应的TabLayout。
 * <p/>
 * <p/>ViewPager上，每一个页面都是一个LanguageFragment
 */
public class HotRepoFragment extends Fragment {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_repo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 注意此处是在Fragment中添加Fragment，属于嵌套Fragment
        mViewPager.setAdapter(new HotRepoPagerAdapter(getChildFragmentManager(),getContext()));
        // 将ViewPager绑定到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
