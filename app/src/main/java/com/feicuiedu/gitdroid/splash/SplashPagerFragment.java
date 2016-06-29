package com.feicuiedu.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by liuqun on 6/28/2016.
 */
public class SplashPagerFragment extends Fragment {

    @Bind(R.id.viewPager)
    ViewPager       mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;
    @BindColor(R.color.colorGreen)
    int             colorGreen;
    @BindColor(R.color.colorRed)
    int             colorRed;
    @BindColor(R.color.colorYellow)
    int             colorYellow;
    @Bind(R.id.content)
    FrameLayout     frameLayout;

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
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mIndicator.setViewPager(mViewPager);
    }

    private final ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        //ARGB取值器(属性动画)
        final ArgbEvaluator mEvaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //当前scroll 的比例 :positionOffset
            //start颜色:做一个
            //end 颜色:自己做一个
            if (position == 0) {
                int color = (int) mEvaluator.evaluate(positionOffset,
                        colorGreen, colorRed);
                frameLayout.setBackgroundColor(color);
                return;
            }
            if (position == 1) {
                int color = (int) mEvaluator.evaluate(positionOffset,
                        colorRed, colorYellow);
                frameLayout.setBackgroundColor(color);
                return;
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
