package com.charles.videoplay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.charles.videoplay.R;
import com.charles.videoplay.entity.VideoTypeInfo;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.util.Screen;
import com.charles.videoplay.widget.MyRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 2016/10/11.
 */

public class IndexGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<VideoTypeInfo> videoTypes;
    private int vedioWidth;

    public IndexGridViewAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        vedioWidth = Screen.getInstance().widthPixels / 2;
    }

    public void setVedioDetails(List<VideoTypeInfo> videoTypes){
        if(videoTypes != null) {
            this.videoTypes = videoTypes;
        } else {
            this.videoTypes = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return videoTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return videoTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_grid_vedio, null);
            holder.rlVedioBg = (MyRelativeLayout) convertView.findViewById(R.id.rlVedioBg);
            holder.ivVideoBg = (ImageView) convertView.findViewById(R.id.ivVideoBg);
            holder.ivHeadPortrait = (ImageView) convertView.findViewById(R.id.ivHeadPortrait);
            holder.tvUserNick = (TextView) convertView.findViewById(R.id.tvUserNick);
            holder.tvPlayCount = (TextView) convertView.findViewById(R.id.tvPlayCount);
            holder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VideoTypeInfo vedio = videoTypes.get(position);
        holder.rlVedioBg.getLayoutParams().height = vedioWidth;

        if (vedio != null) {
            /*Glide.with(context).load(vedio.getImgUrl()).into(new ViewTarget<MyRelativeLayout,GlideDrawable>(holder.rlVedioBg) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    holder.rlVedioBg.setBacDrawble(resource.getCurrent());
                }
            });*/
            ImageLoadUtils.displayImage(context,vedio.getImgUrl(),holder.ivVideoBg,R.mipmap.vedio_default_background);

            ImageLoadUtils.setSimpleCircleHeadIcon(context, vedio.getUserHead(), R.mipmap.mine_default_head, holder.ivHeadPortrait);

            holder.tvUserNick.setText(vedio.getUserNick());
            Drawable drawable;
            if (vedio.getFavorFlag() == 1) {
                drawable = context.getResources().getDrawable(R.mipmap.solid_heart);
            } else {
                drawable = context.getResources().getDrawable(R.mipmap.hollow);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            holder.tvPlayCount.setText(vedio.getPlayCount()+"次播放");
            holder.tvComment.setText(vedio.getDes());
        }
        return convertView;
    }

    class ViewHolder{
        public MyRelativeLayout rlVedioBg;
        /** 视频缩列图  */
        private ImageView ivVideoBg;
        /** 头像 */
        private ImageView ivHeadPortrait;
        /** 视频作者 */
        private TextView tvUserNick;
        /** 视频播放次数 */
        private TextView tvPlayCount;
        /** 视频评论 */
        private TextView tvComment;
    }
}
