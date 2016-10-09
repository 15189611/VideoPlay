package com.charles.videoplay.http.responselistener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.charles.videoplay.R;
import com.charles.videoplay.http.AppException;
import com.google.gson.Gson;

/**
 *
 * Created by Charles
 */
public class ToastResponseListener<T> implements ResponseListener<T> {

    Activity mActivity;

    public ToastResponseListener(Activity activity) {
        mActivity = activity;
        onPrepare();
        hideErrorView();
        showLoadingView();
    }

    public void onPrepare(){}

    @Override
    public void onSuccess(T t) {
        Log.d("onSuccess" , new Gson().toJson(t));
        hideLoadingView();
    }

    @Override
    public void onFailure(AppException e) {
        hideLoadingView();
        showErrorView();
        handleAppException(e, needShowToast());
    }

    public boolean needShowToast() {
        return true;
    }

    public View getLoadingView(){
        return null;
    }

    private void showLoadingView(){
        if(disableLoadingView()){
            return;
        }
        getLoadingView().setVisibility(View.VISIBLE);
    }

    private void hideLoadingView(){
        if(disableLoadingView()){
            return;
        }

        startHideLoadingViewAnim();
    }

    public View getErrorView(){
        return null;
    }

    public void showErrorView(){
        if(disableErrorView()){
            return;
        }
        getErrorView().setVisibility(View.VISIBLE);
    }

    private void hideErrorView(){
        if(disableErrorView()){
            return;
        }
        getErrorView().setVisibility(View.GONE);
    }

    private boolean disableLoadingView(){
        return isFinish() || getLoadingView() == null;
    }

    private boolean disableErrorView(){
        return isFinish() || getErrorView() == null;
    }

    private void startHideLoadingViewAnim(){
        final View loadingView = getLoadingView();
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.alpha_hide);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingView.setVisibility(View.GONE);
                loadingView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        loadingView.startAnimation(animation);
    }

    private boolean isFinish(){
        return mActivity == null || mActivity.isFinishing();
    }

    public void handleAppException(AppException appException, boolean needShowToast) {
        if (appException == null || isFinish()) {
            return;
        }
    }
}
