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
public class Pager2 extends FrameLayout {
    @Bind(R.id.ivBubble1)
    ImageView mIvBubble1;
    @Bind(R.id.ivBubble2)
    ImageView mIvBubble2;
    @Bind(R.id.ivBubble3)
    ImageView mIvBubble3;

    public Pager2(Context context) {
        super(context);
        init();
    }

    public Pager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this,
                true);
        ButterKnife.bind(this);
        mIvBubble1.setVisibility(View.GONE);
        mIvBubble2.setVisibility(View.GONE);
        mIvBubble3.setVisibility(View.GONE);
    }

    public void showAnimation() {
        if (mIvBubble1.getVisibility() != View.VISIBLE) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble1.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown).duration(300).playOn
                            (mIvBubble1);
                }
            }, 50);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown).duration(300).playOn
                            (mIvBubble2);
                }
            }, 550);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble3.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown).duration(300).playOn
                            (mIvBubble3);
                }
            }, 1050);
        }
    }
}
