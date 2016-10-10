package com.charles.videoplay.recyclerview.LayoutManager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.charles.videoplay.recyclerview.Listener.OverScrollListener;


/**
 * Created by Charles
 * <br/>
 * 增加了{@link OverScrollListener}的GridLayoutManage
 */
public class ChGridLayoutManager extends GridLayoutManager {

    private OverScrollListener mListener;

    public ChGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ChGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ChGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);

        mListener.overScrollBy(dy - scrollRange);

        return scrollRange;
    }

    /**
     * 设置滑动过度监听
     *
     * @param listener
     */
    public void setOverScrollListener(OverScrollListener listener) {
        mListener = listener;
    }

}
