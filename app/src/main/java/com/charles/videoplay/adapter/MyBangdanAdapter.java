package com.charles.videoplay.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.charles.videoplay.R;
import com.charles.videoplay.adapter.viewholder.BaseScrollViewHolder;
import com.charles.videoplay.adapter.viewholder.MyViewHolder;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.videolistutil.items.ListItem;
import com.charles.videoplay.videolistutil.scroll_utils.ItemsProvider;

import java.util.List;


/**
 * Created by Charles on 2016/11/7.
 */

public  class MyBangdanAdapter extends RecyclerView.Adapter<BaseScrollViewHolder>
        implements ItemsProvider {

    private RecyclerView recycleView;
    private Activity activity;
    private List<BangdanVideos> items;

    public MyBangdanAdapter(Activity activity , RecyclerView recyclerView , List<BangdanVideos> items){
        this.activity = activity;
        this.recycleView = recyclerView;
        this.items = items;
    }

    @Override
    public BaseScrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity)
                .inflate(R.layout.item_bangdan_selection_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseScrollViewHolder holder, int position) {
        BangdanVideos bangdanVideos = items.get(position);
        holder.onBind(activity,position,bangdanVideos);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ListItem getListItem(int position) {
        RecyclerView.ViewHolder holder = recycleView.findViewHolderForAdapterPosition(position);
        if (holder instanceof ListItem) {
            return (ListItem) holder;
        }
        return null;
    }

    @Override
    public int listItemSize() {
        return items.size();
    }

}
