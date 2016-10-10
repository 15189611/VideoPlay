package com.charles.videoplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charles.videoplay.R;
import com.charles.videoplay.base.swpieback.SwipeBackBaseActivity;
import com.charles.videoplay.util.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public abstract class BaseActivity extends SwipeBackBaseActivity {

    protected final String TAG = this.getClass().getName();
    protected  TextView tvTitle;
    public Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeBack();
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initToolbar();
        initData();
        initView();
        getRemoteData();
        registEventBus();
    }

    private void initToolbar() {
        if (!enableToolbar()) {
            return;
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            throw new NullPointerException("please add toolbar in layout");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setTitle(String title) {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    public void setTitle(int resId) {
        String title = getString(resId);
        setTitle(title);
    }

    protected  void registEventBus(){
        if (!enableEventBus()) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    private void unregisterEventBus() {
        if (!enableEventBus()) {
            return;
        }
        EventBus.getDefault().unregister(this);
    }

    protected boolean enableEventBus() {
        return false;
    }

    protected boolean enableToolbar() {
        return true;
    }

    private void initSwipeBack() {
        if (!enableSwipeBack()) {
            setBackEnable(enableSwipeBack());
        }else{
            setBackEnable(enableSwipeBack());
        }
    }

    public boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onHomeAsUpPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onHomeAsUpPressed(){
        onBackPressed();
    }

    protected abstract int getLayoutResId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void getRemoteData();

}
