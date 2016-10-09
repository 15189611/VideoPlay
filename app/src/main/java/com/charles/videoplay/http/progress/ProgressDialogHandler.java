package com.charles.videoplay.http.progress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.charles.videoplay.R;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog mDialog;

    private Context mContext;
    private boolean mCancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.mContext = context;
        this.mCancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    private void initDialog() {
        if (mContext == null || (mContext instanceof Activity && ((Activity) mContext).isFinishing())) {
            return;
        }

        if (mDialog == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.progressdialog, null);
            mDialog = new Dialog(mContext, R.style.indeterminate_dialog);
            mDialog.setContentView(view);
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(false);

            if (mCancelable) {
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            showDialog();
        }
    }

    private void showDialog() {
        if (mDialog == null) {
            return;
        }

        if (mContext == null || (mContext instanceof Activity && ((Activity) mContext).isFinishing())) {
            return;
        }

        mDialog.show();
    }

    private void dismissDialog() {
        if (mDialog == null) {
            return;
        }

        if (mDialog.getWindow() == null || mDialog.getWindow().getDecorView() == null) {
            return;
        }

        mDialog.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mContext == null || (mContext instanceof Activity && ((Activity) mContext).isFinishing())) {
                    return;
                }

                mDialog.dismiss();
                mDialog = null;
            }
        }, 500);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissDialog();
                break;
        }
    }

}
