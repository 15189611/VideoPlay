package com.charles.videoplay.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.entity.VedioDetails;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.util.Screen;
import com.charles.videoplay.widget.MyRelativeLayout;

import java.util.List;

/**
 * Created by Charles on 2016/10/12.
 */

public class TabAdapter extends BaseQuickAdapter<VedioDetails> {

    private Context context;
    private int vedioWidth;
    public TabAdapter(Context context,List<VedioDetails> data) {
        super(R.layout.item_grid_vedio,data);
        this.context = context;
        vedioWidth = Screen.getInstance().widthPixels / 2;
    }

    @Override
    protected void convert(BaseViewHolder helper, VedioDetails item) {
        final MyRelativeLayout rlVedioBg = helper.getView(R.id.rlVedioBg);
        rlVedioBg.getLayoutParams().width = vedioWidth;
        ImageView ivVideoBg = helper.getView(R.id.ivVideoBg);
        ImageView  ivHeadPortrait  =  helper.getView(R.id.ivHeadPortrait);
        TextView tvUserNick   =helper.getView(R.id.tvUserNick);
        TextView tvPlayCount   =helper.getView(R.id.tvPlayCount);
        TextView  tvComment  = helper.getView(R.id.tvComment);

        Glide.with(context).load(item.getImgUrl()).into(new ViewTarget<MyRelativeLayout,GlideDrawable>(rlVedioBg) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                rlVedioBg.setBacDrawble(resource.getCurrent());
            }
        });
        ImageLoadUtils.setSimpleCircleHeadIcon(context, item.getUserHead(), R.mipmap.mine_default_head, ivHeadPortrait);

        tvUserNick.setText(item.getUserNick());
        Drawable drawable;
        if (item.getFavorFlag() == 1) {
            drawable = context.getResources().getDrawable(R.mipmap.solid_heart);
        } else {
            drawable = context.getResources().getDrawable(R.mipmap.hollow);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tvPlayCount.setText(item.getPlayCount()+"次播放");
        tvComment.setText(item.getDes());
    }
}
