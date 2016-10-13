package com.charles.videoplay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.entity.VideoTypeInfo;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.util.Screen;
import com.charles.videoplay.widget.MyRelativeLayout;

import java.util.List;

/**
 * Created by Charles on 2016/10/12.
 */

public class IndexGridViewAdapter2 extends BaseQuickAdapter<VideoTypeInfo>{

    private Context context;
    private int vedioWidth;
    private int height ;
    public IndexGridViewAdapter2(Context context, List<VideoTypeInfo> data) {
        super(R.layout.item_grid_vedio,data);
        this.context = context ;
        vedioWidth = Screen.getInstance().widthPixels / 2;
        height = Screen.getInstance().heightPixels / 2;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoTypeInfo item) {
        final MyRelativeLayout rlVedioBg = helper.getView(R.id.rlVedioBg);
        rlVedioBg.getLayoutParams().width = vedioWidth;
        ImageView ivVideoBg = helper.getView(R.id.ivVideoBg);
        ImageView  ivHeadPortrait  =  helper.getView(R.id.ivHeadPortrait);
        TextView   tvUserNick   =helper.getView(R.id.tvUserNick);
        TextView tvPlayCount   =helper.getView(R.id.tvPlayCount);
        TextView  tvComment  = helper.getView(R.id.tvComment);

         Glide.with(context).load(item.getImgUrl()).into(new ViewTarget<MyRelativeLayout,GlideDrawable>(rlVedioBg) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    rlVedioBg.setBacDrawble(resource.getCurrent());
                }
            });
       // ImageLoadUtils.displayImage(context,item.getImgUrl(),ivVideoBg,R.mipmap.vedio_default_background);

        ImageLoadUtils.setSimpleCircleHeadIcon(context, item.getUserHead(), R.mipmap.mine_default_head, ivHeadPortrait);

        tvUserNick.setText(item.getUserNick());
        tvPlayCount.setText(item.getPlayCount()+"次播放");
        tvComment.setText(item.getDes());
    }
}
