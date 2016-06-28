package com.feicuiedu.gitdroid.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by liuqun on 6/28/2016.
 */
public class SplashPagerFragment extends Fragment {

    @Bind(R.id.viewPager)
    ViewPager       mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;

    private SplashPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new SplashPagerAdapter(view.getContext());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.viewPager, R.id.indicator})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewPager:
                break;
            case R.id.indicator:
                break;
            

        }
    }
}
