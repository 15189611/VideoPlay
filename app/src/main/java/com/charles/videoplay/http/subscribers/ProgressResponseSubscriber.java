package com.charles.videoplay.http.subscribers;

import android.app.Activity;

import com.charles.videoplay.http.progress.ProgressCancelListener;
import com.charles.videoplay.http.progress.ProgressDialogHandler;
import com.charles.videoplay.http.responselistener.ResponseListener;


public class ProgressResponseSubscriber<T> extends BaseSubscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressResponseSubscriber(Activity activity, String requestMethod, ResponseListener<T> responseListener) {
        super(activity, requestMethod, responseListener);
        mProgressDialogHandler = new ProgressDialogHandler(activity, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        removeRequest();
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}