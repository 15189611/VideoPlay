package com.charles.videoplay.recyclerview.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.charles.videoplay.R;
import com.charles.videoplay.recyclerview.LayoutManager.ChGridLayoutManager;
import com.charles.videoplay.recyclerview.LayoutManager.ChLinearLayoutManager;
import com.charles.videoplay.recyclerview.LayoutManager.ChStaggeredGridLayoutManager;
import com.charles.videoplay.recyclerview.Listener.LoadDataListener;
import com.charles.videoplay.recyclerview.Listener.OverScrollListener;

/**
 * Created by Charles on 2016/8/23.
 */

public class PullRefreshRecycleView extends RecyclerView implements Runnable {
    private Context mContext;
    private int dp1;
    private int headerImageHeight = -1;
    private int headerImageScaleHeight = -1;
    private int headerImageMaxHeight = -1;
    private boolean isTouching = false;
    private HeaderView headerView;

    private boolean pullToRefreshEnable = true;
    private boolean loadMoreEnable = true;
    private boolean noLoadMore;             //不要加载更多的回调

    private BaseQuickAdapter mAdapter;
    private Handler mHandler = new MyHandler();
    private LoadDataListener mLoadDataListener;
    private boolean isLoadingData = false;

    public PullRefreshRecycleView(Context context) {
        this(context,null);
    }

    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private OverScrollListener mOverScrollListener = new OverScrollListener() {
        @Override
        public void overScrollBy(int dy) {
            // dy为拉伸过度时每毫秒拉伸的距离，正数表示向上拉伸多度，负数表示向下拉伸过度
            if ( pullToRefreshEnable && isTouching && (dy < 0 ) ){
                mHandler.obtainMessage(0, dy, 0, null).sendToTarget();
                onScrollChanged(0, 0, 0, 0);
            }
        }
    };

    private void init(Context context) {
        this.mContext = context;
        dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
        headerImageMaxHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getContext().getResources().getDisplayMetrics());
        setOverScrollMode(OVER_SCROLL_NEVER);
        this.postDelayed(this,300);
    }

    private void getManager() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof ChLinearLayoutManager) {
            ((ChLinearLayoutManager) manager).setOverScrollListener(mOverScrollListener);
        }else if (manager instanceof ChGridLayoutManager) {
            ((ChGridLayoutManager) manager).setOverScrollListener(mOverScrollListener);
        } else if (manager instanceof ChStaggeredGridLayoutManager) {
            ((ChStaggeredGridLayoutManager) manager).setOverScrollListener(mOverScrollListener);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(mHandler);
    }

    @Override
    public void run() {
        getManager();
    }

    public void setLoadDataListener(LoadDataListener listener) {
        mLoadDataListener = listener;
    }

    public void refreshComplete() {
        isLoadingData = false;
       if (!loadMoreEnable) {       //这是当我们加载更多没有数据了 此时的标记会为false,刷新后又可以重新去加载更多
            mAdapter.removeAllFooterView();
            mAdapter.setNextLoadEnable(true);
        }
        headerImageHint();
    }

    public void loadMoreComplete() {   //加载更多后，调用消失加载的Dialog
        isLoadingData = false;
        if(mAdapter != null){
            mAdapter.loadComplete();
        }
        setLoadMoreEnable(true);
    }

   public void setLoadMoreEnable(boolean loadMoreEnable) {  //设置没有更多数据,可以调用(传false)
        this.loadMoreEnable = loadMoreEnable;
        if (mAdapter != null) {
            mAdapter.setNextLoadEnable(loadMoreEnable);
        }
    }


    public void loadNoMoreView() {       //设置没有更多数据，带有文本提示
        mAdapter.loadComplete();
        setLoadMoreEnable(false);
        mAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.not_loading, this, false));
    }

    public void setNoLoadMore(boolean noLoadMore){   //设置不要加载更多的回调  true 不要
        this.noLoadMore = noLoadMore;
    }

    public void showMoreFailedView(){
        Toast.makeText(mContext, "网络异常", Toast.LENGTH_LONG).show();
        mAdapter.showLoadMoreFailedView();
    }

    private void headerImageHint() {
        if (headerView == null) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofInt(
                headerView.getLayoutParams().height, headerImageHeight);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                headerView.getLayoutParams().height = (int) animation.getAnimatedValue();
                headerView.requestLayout();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                headerView.resetView();
            }
        });
        animator.start();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = (BaseQuickAdapter) adapter;

        if (headerView == null && pullToRefreshEnable) {
            // RecycleView新建头部
            RelativeLayout headerLayout = new RelativeLayout(mContext);
            headerLayout.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            headerView = new HeaderView(mContext);
            headerView.setMaxHeight(headerImageMaxHeight);
            headerLayout.addView(headerView, RelativeLayout.LayoutParams.MATCH_PARENT, dp1);
            setHeaderView(headerView);

            mAdapter.addHeaderView(headerLayout);
        }

        mAdapter.setLoadingView(LayoutInflater.from(mContext).inflate(R.layout.def_loading, this, false));
        if(!noLoadMore){
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {  //更多监听回调
                @Override
                public void onLoadMoreRequested() {
                    if(mLoadDataListener != null){
                        mLoadDataListener.onLoadMore();
                    }
                }
            });
        }

        super.setAdapter(adapter);
    }

    public void setPullToRefreshEnable(boolean pullToRefreshEnable) {
        this.pullToRefreshEnable = pullToRefreshEnable;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (headerView == null) return;
        View view = (View) headerView.getParent();
        if (view.getTop() < 0 && headerView.getLayoutParams().height > headerImageHeight) {
            headerView.getLayoutParams().height += view.getTop();
            mHandler.obtainMessage(0, view.getTop(), 0, view).sendToTarget();
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            isTouching = true;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                isTouching = false;
                if (headerView.getLayoutParams().height > headerImageHeight) {
                    if (headerView != null && headerView.getLayoutParams().height > headerImageMaxHeight && mLoadDataListener != null  && !isLoadingData) {
                        refresh();
                        break;
                    }
                }

                headerImageHint();
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void forceRefresh() {
        if (headerView == null) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofInt(
                headerView.getLayoutParams().height, headerImageMaxHeight);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                headerView.getLayoutParams().height = (int) animation.getAnimatedValue();
                headerView.requestLayout();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                refresh();
            }
        });
        animator.start();
    }

    private void refresh() {
        isLoadingData = true;
        if (headerView != null && mLoadDataListener != null) {
            headerView.startRefresh();
            mLoadDataListener.onRefresh();
        }
    }

    public void setHeaderView(HeaderView headerView) {
        this.headerView = headerView;
        headerImageHeight = headerView.getHeight();
        if (headerImageHeight <= 0) {
            headerImageHeight = headerView.getLayoutParams().height;
        } else {
            this.headerView.getLayoutParams().height = headerImageHeight;
        }

    }

    private  class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateViewSize(msg);
                    break;
            }
        }
    }
    private  void updateViewSize (Message msg){
        if (msg.obj != null) {
            headerView.getLayoutParams().height += msg.arg1;
            View view = ((View) msg.obj);
            view.layout(view.getLeft(), 0, view.getRight(), view.getBottom());
        } else {
            headerImageScaleHeight = headerView.getLayoutParams().height
                    - headerImageHeight;

            if (headerImageScaleHeight > (headerImageMaxHeight - headerImageHeight) / 3) {
                headerView.getLayoutParams().height -= msg.arg1 / 3 * 2;
            } else if (headerImageScaleHeight > (headerImageMaxHeight - headerImageHeight) / 3 * 2) {
                headerView.getLayoutParams().height -= msg.arg1 / 3 * 3 ;
            } else {
                headerView.getLayoutParams().height -= msg.arg1 / 3 * 1.5 ;
            }
        }
        headerView.requestLayout();
    }

}
