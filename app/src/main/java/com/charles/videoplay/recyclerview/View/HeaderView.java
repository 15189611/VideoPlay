package com.charles.videoplay.recyclerview.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.charles.videoplay.R;


/**
 * Created by Charles
 */
public class HeaderView extends FrameLayout {

    private View mRotateView;
    private View mProgressBar;

    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private int maxHeight;

    private boolean needRefresh = false;

    private View header;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void setmRotateView (View view){

    }
    private void init() {
        buildAnimation();

        header = LayoutInflater.from(getContext()).inflate(R.layout.layout_header_pull_to_refresh, this);

        mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view);
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int height = getLayoutParams().height;

        setTranslationY(height - maxHeight);

        if (!needRefresh && height > maxHeight) {
            needRefresh = true;
            startFlipAnimation();
        } else if (needRefresh && height <= maxHeight) {
            needRefresh = false;
            startReverseFlipAnimation();
        }
    }

    private void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    public void startFlipAnimation() {
        if (mRotateView.getVisibility() == VISIBLE){
            mRotateView.clearAnimation();
            mRotateView.startAnimation(mFlipAnimation);
        }
    }

    public void startReverseFlipAnimation() {
        if (mRotateView.getVisibility() == VISIBLE) {
            mRotateView.clearAnimation();
            mRotateView.startAnimation(mReverseFlipAnimation);
        }
    }

    public void startRefresh() {
        if (getLayoutParams().height == maxHeight) {
            hideRotateView();
            mProgressBar.setVisibility(VISIBLE);
        } else {
            resetToMaxHeight();
        }
    }

    private void resetToMaxHeight() {
        ValueAnimator animator = ValueAnimator.ofInt(
               getLayoutParams().height, maxHeight);
        animator.setDuration(100);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().height = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hideRotateView();
                mProgressBar.setVisibility(VISIBLE);
            }
        });
        animator.start();
    }

    public void resetView() {
        mRotateView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
    }

    private void hideRotateView() {
        mRotateView.clearAnimation();
        mRotateView.setVisibility(GONE);
    }
}
