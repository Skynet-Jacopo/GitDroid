package com.feicuiedu.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuqun on 6/28/2016.
 */
public class Pager0 extends FrameLayout {
    LayoutInflater mInflater = LayoutInflater.from(getContext());
    @Bind(R.id.ivTablet)
    ImageView mIvTablet;
    @Bind(R.id.ivDesktop)
    ImageView mIvDesktop;

    public Pager0(Context context) {
        super(context);
        init();
    }

    public Pager0(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInflater.inflate(R.layout.content_pager_0, this, true);
        ButterKnife.bind(this);
        mIvTablet.setVisibility(View.INVISIBLE);
        mIvDesktop.setVisibility(View.INVISIBLE);
    }

    public void showAnimation() {
        if (mIvTablet.getVisibility() != View.VISIBLE) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvTablet.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.RotateInDownRight).duration(300).playOn(mIvTablet);
                }
            }, 50);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvDesktop.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.RotateInDownRight).duration(300).playOn(mIvDesktop);
            }
        }, 550);
    }
}
