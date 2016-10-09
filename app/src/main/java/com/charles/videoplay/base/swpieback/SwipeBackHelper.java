package com.charles.videoplay.base.swpieback;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.View;

import java.util.Stack;

public class SwipeBackHelper {

    private static final Stack<SwipeBackHelper> sActivities = new Stack<>();

    public Activity getActivity() {
        return mActivity;
    }

    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackHelper(Activity activity) {
        mActivity = activity;
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
        sActivities.push(this);
    }

    public boolean hasSecondActivity() {
        return sActivities.size() >= 2;
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(this);
    }

    public void onActivityDestroy() {
        sActivities.remove(this);
    }

    public SwipeBackHelper getSecondActivity() {
        if (sActivities.size() >= 2)
            return sActivities.elementAt(sActivities.size() - 2);
        return null;
    }

    public void drawThumb(Canvas canvas) {
        View decorChild = getBackLayout().getContentView();
        decorChild.draw(canvas);
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public void scrollToFinishActivity() {
        getBackLayout().scrollToFinishActivity();
    }

    public void setBackEnable(boolean enable) {
        getBackLayout().setEnableGesture(enable);
    }

    public SwipeBackLayout getBackLayout() {
        return mSwipeBackLayout;
    }
}
