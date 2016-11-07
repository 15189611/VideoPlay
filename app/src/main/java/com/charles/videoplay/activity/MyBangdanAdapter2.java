package com.charles.videoplay.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.ijkplay.JCVideoPlayerStandard;
import com.charles.videoplay.util.BigDecimalMath;
import com.charles.videoplay.util.DataUtil;
import com.charles.videoplay.util.ImageLoadUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Charles on 2016/11/7.
 */

public class MyBangdanAdapter2 extends BaseQuickAdapter<BangdanVideos> {

    public MyBangdanAdapter2(List<BangdanVideos> data) {
        super(R.layout.item_bangdan_selection_fragment, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, BangdanVideos item) {
        ImageView user_pic = helper.getView(R.id.video_item_pic);
        TextView user_nick = helper.getView(R.id.video_item_userNick);
        TextView play_count = helper.getView(R.id.video_item_playcount);
        JCVideoPlayerStandard video_play = helper.getView(R.id.video_item_play);
        TextView des = helper.getView(R.id.video_item_des);
        TextView comments = helper.getView(R.id.video_item_comments);
        TextView fans = helper.getView(R.id.video_item_fans);
        LinearLayout share = helper.getView(R.id.video_item_share);

        ImageLoadUtils.displayImage(helper.convertView.getContext(), item.userHead, user_pic, R.mipmap.mine_default_head);

        video_play.setUp(item.mp4Url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
        ImageLoadUtils.displayImage(helper.convertView.getContext(), item.imgUrl, video_play.thumbImageView, R.mipmap.mine_default_head);
        user_nick.setText(item.userNick);

        Date date = new Date(item.time);
        String commentTime = DataUtil.getTodayTime(date, DataUtil.VIDEO_TIME);
        if (Double.valueOf(item.playCount) >= 10000) {
            double playCount = BigDecimalMath.div(Double.valueOf(item.playCount), Double.valueOf("10000"), 2);
            play_count.setText(commentTime + "  " + playCount + "万次播放");
        } else {
            play_count.setText(commentTime + "  " + item.playCount + "次播放");
        }
        des.setText(item.des);
        comments.setText(item.commentCount + "");
        fans.setText(item.favorCount + "");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(helper.convertView.getContext(), "分享", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
