package com.charles.videoplay.adapter.viewholder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charles.videoplay.R;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.ijkplay.JCUtils;
import com.charles.videoplay.ijkplay.JCVideoPlayerStandard;
import com.charles.videoplay.util.BigDecimalMath;
import com.charles.videoplay.util.DataUtil;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.videolistutil.items.ListItem;

import java.util.Date;

import butterknife.Bind;

import static com.charles.videoplay.ijkplay.JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static com.charles.videoplay.ijkplay.JCVideoPlayer.CURRENT_STATE_NORMAL;
import static com.charles.videoplay.ijkplay.JCVideoPlayer.WIFI_TIP_DIALOG_SHOWED;

/**
 * Created by Charles on 2016/11/7.
 */

public class MyViewHolder extends BaseScrollViewHolder implements ListItem {

    private Activity activity;

    @Bind(R.id.video_item_userNick)
    TextView user_nick;
    @Bind(R.id.video_item_pic)
    ImageView user_pic;
    @Bind(R.id.video_item_playcount)
    TextView play_count;
    @Bind(R.id.video_item_play)
    JCVideoPlayerStandard video_play;
    @Bind(R.id.video_item_des)
    TextView des;
    @Bind(R.id.video_item_comments)
    TextView comments;
    @Bind(R.id.video_item_fans)
    TextView fans;
    @Bind(R.id.video_item_share)
    LinearLayout share;

    public MyViewHolder(View view) {
        super(view);
    }

    @Override
    public void onBind(Activity activity,int position, BangdanVideos item) {
        this.activity = activity;
        setData(activity,item);
    }


    private void setData(final Activity activity,  BangdanVideos item) {
        ImageLoadUtils.displayImage(activity,item.userHead,user_pic, R.mipmap.mine_default_head);
        video_play.setUp(item.mp4Url,JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
        ImageLoadUtils.displayImage(activity,item.imgUrl,video_play.thumbImageView, R.mipmap.mine_default_head);
        user_nick.setText(item.userNick);

        Date date = new Date(item.time);
        String commentTime = DataUtil.getTodayTime(date, DataUtil.VIDEO_TIME);
        if(Double.valueOf(item.playCount) >= 10000){
            double playCount = BigDecimalMath.div(Double.valueOf(item.playCount), Double.valueOf("10000"), 2);
            play_count.setText(commentTime + "  " + playCount + "万次播放");
        }else{
            play_count.setText(commentTime + "  " + item.playCount + "次播放");
        }
        des.setText(item.des);
        comments.setText(item.commentCount+"");
        fans.setText(item.favorCount+"");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"分享",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.i("Charles2", "可见=="+newActiveViewPosition);
        if(activity != null && video_play != null){
            if (video_play.currentState == CURRENT_STATE_NORMAL) {
                if (!video_play.url.startsWith("file") && !JCUtils.isWifiConnected(activity) && !WIFI_TIP_DIALOG_SHOWED) {
                    video_play.showWifiDialog();
                    return;
                }
                video_play.startPlayLogic();
            } else if (video_play.currentState == CURRENT_STATE_AUTO_COMPLETE) {
                video_play.onClickUiToggle();
            }
        }
    }

    @Override
    public void deactivate(View currentView, int position) {
    }

}
