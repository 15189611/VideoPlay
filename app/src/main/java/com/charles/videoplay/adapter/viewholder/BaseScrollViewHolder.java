package com.charles.videoplay.adapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.charles.videoplay.entity.BangdanVideos;

import butterknife.ButterKnife;

/**
 * @author Charles
 */
public abstract class BaseScrollViewHolder<T extends BangdanVideos> extends RecyclerView.ViewHolder {

    public BaseScrollViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBind(Activity activity,int position, T iItem);
}
