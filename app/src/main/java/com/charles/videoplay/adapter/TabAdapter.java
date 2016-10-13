package com.charles.videoplay.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
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
import com.charles.videoplay.entity.VedioDetails;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.util.Screen;
import com.charles.videoplay.widget.MyRelativeLayout;

import java.util.List;

/**
 * Created by Charles on 2016/10/12.
 */

public class TabAdapter extends BaseQuickAdapter<VedioDetails> {

    private Context context;
    private int vedioWidth;
    private int vedioHeight;
    public TabAdapter(Context context,List<VedioDetails> data) {
        super(R.layout.item_tab_fragment,data);
        this.context = context;
        int buttom = Screen.dip2px(context, 53);
        vedioWidth = Screen.getInstance().widthPixels / 2;
        vedioHeight = (Screen.getInstance().heightPixels - buttom ) / 5;
    }

    @Override
    protected void convert(BaseViewHolder helper, VedioDetails item) {
        TextView  tvComment  = helper.getView(R.id.tvComment);
        TextView tvUserNick   =helper.getView(R.id.tvUserNick);
        ImageView  ivHeadPortrait  =  helper.getView(R.id.ivUserPic);
        ImageView ivImage = helper.getView(R.id.ivImage);

        ViewGroup.LayoutParams params = ivImage.getLayoutParams();
        params.width = vedioWidth;
        params.height = (int)(vedioHeight + Math.random()* 100);
        ivImage.setMaxHeight((int)(vedioHeight + Math.random()* 100));
        ivImage.setMaxWidth(vedioWidth);
        ImageLoadUtils.displayImage(context,item.getImgUrl(),ivImage,R.mipmap.vedio_default_background);
        ImageLoadUtils.setSimpleCircleHeadIcon(context, item.getUserHead(), R.mipmap.mine_default_head, ivHeadPortrait);
        tvUserNick.setText(item.getUserNick());
        tvComment.setText(item.getDes());
    }
}
