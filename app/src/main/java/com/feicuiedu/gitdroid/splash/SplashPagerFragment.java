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
import android.widget.ImageView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.splash.pager.Pager0;
import com.feicuiedu.gitdroid.splash.pager.Pager2;

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
    @Bind(R.id.ivPhoneBlank)
    ImageView       mIvPhoneBlank;
    @Bind(R.id.ivPhone)
    ImageView       mIvPhone;
    @Bind(R.id.layoutPhone)
    FrameLayout     mLayoutPhone;

    private SplashPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new SplashPagerAdapter(view.getContext());
        mViewPager.setAdapter(mAdapter);
        //监听页面,添加属性动画以及YoYo动画
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        //监听页面,添加mLayoutPhone的平移,缩放,渐变效果
        mViewPager.addOnPageChangeListener(phoneChangeListener);
        mIndicator.setViewPager(mViewPager);

        Pager0 pager0 = (Pager0) mAdapter.getView(0);
        pager0.showAnimation();
    }
//此监听主要负责viewpager在scroll过程中,当前布局上mLayoutPhone布局的平移,缩放,渐变的处理
    private final ViewPager.OnPageChangeListener phoneChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //viewPager在第一和第二个页面之间
            if (position == 0) {
                //移动过程中实时scale(缩放)
                float scale = 0.3f + positionOffset * 0.7f;
                mLayoutPhone.setScaleX(scale);
                mLayoutPhone.setScaleY(scale);

                //在移动过程中,有一个平移的动画
                int scroll = (int) ((positionOffset-1)*400);
                mLayoutPhone.setTranslationX(scroll);
                //在移动过程中,alph实时变化
                mIvPhone.setAlpha(positionOffset);
                return;
            }
            //当ViewPager在第二和第三个页面之间时(总是为1),手机要和ViewPager一起平移
            if (position == 1) {
                mLayoutPhone.setTranslationX(-positionOffsetPixels);
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
            if (position == 2) {
                Pager2 pager2 = (Pager2) mAdapter.getView(2);
                pager2.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
