package com.charles.videoplay.adapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.ijkplay.JCUtils;
import com.charles.videoplay.ijkplay.JCVideoPlayerStandard;
import com.charles.videoplay.util.BigDecimalMath;
import com.charles.videoplay.util.DataUtil;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.videolistutil.items.ListItem;
import com.charles.videoplay.videolistutil.scroll_utils.ItemsProvider;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

import static com.charles.videoplay.ijkplay.JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static com.charles.videoplay.ijkplay.JCVideoPlayer.CURRENT_STATE_NORMAL;
import static com.charles.videoplay.ijkplay.JCVideoPlayer.WIFI_TIP_DIALOG_SHOWED;

/**
 * Created by Charles on 2016/11/15.
 */

public class BangdanSelectionAdapter extends BaseQuickAdapter<BangdanVideos> implements ItemsProvider {

    private RecyclerView recycleView;
    private Activity activity;
    private List<BangdanVideos> items;

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

    public BangdanSelectionAdapter(Activity activity , RecyclerView recyclerView , List<BangdanVideos> items){
        this(R.layout.item_bangdan_selection_fragment, items);
        this.activity = activity;
        this.recycleView = recyclerView;
        this.items = items;
    }


    public BangdanSelectionAdapter(int layoutResId, List<BangdanVideos> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BangdanVideos item) {
        user_nick = helper.getView(R.id.video_item_userNick);
        user_pic = helper.getView(R.id.video_item_pic);
        play_count = helper.getView(R.id.video_item_playcount);
        video_play = helper.getView(R.id.video_item_play);
        des = helper.getView(R.id.video_item_des);
        comments = helper.getView(R.id.video_item_comments);
        fans = helper.getView(R.id.video_item_fans);
        share = helper.getView(R.id.video_item_share);
        setData(activity,item);
    }

    private void setData(final Activity activity,  BangdanVideos item) {
        ImageLoadUtils.displayImage(activity,item.userHead,user_pic, R.mipmap.mine_default_head);
        video_play.setUp(item.mp4Url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
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
