package com.charles.videoplay.recyclerview.LayoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.charles.videoplay.recyclerview.Listener.OverScrollListener;


/**
 * Created by Charles
 * <br/>
 * 增加了{@link OverScrollListener}的StaggeredGridLayoutManager
 */
public class ChStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    private OverScrollListener mListener;


    public ChStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ChStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);
        if(mListener != null){
            mListener.overScrollBy(dy - scrollRange);
        }

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
