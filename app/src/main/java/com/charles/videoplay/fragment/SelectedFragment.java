package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.adapter.IndexGridViewAdapter;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.IndexVideoList;
import com.charles.videoplay.entity.VideoType;
import com.charles.videoplay.entity.VideoTypeInfo;
import com.charles.videoplay.recyclerview.LayoutManager.ChLinearLayoutManager;
import com.charles.videoplay.recyclerview.View.PullRefreshRecycleView;
import com.charles.videoplay.util.ImageLoadUtils;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/10.
 */

public class SelectedFragment extends BaseFragment {
    @Bind(R.id.rvVideoList)
    PullRefreshRecycleView recycleView;

    private IndexVideoList videoList;
    private View inflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            videoList = (IndexVideoList) arguments.getSerializable("videoList");
            Logger.i(videoList.getData().size()+"");
        }
    }

    public static SelectedFragment newInstance(IndexVideoList videoList){
        SelectedFragment selectedFragment = new SelectedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoList",videoList);
        selectedFragment.setArguments(bundle);
        return selectedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_selection, container, false);
        ButterKnife.bind(this,inflate);
        initData();
        return inflate;
    }

    private void initData() {
        SelectionAdapter adapter = new SelectionAdapter(videoList.getData());
        ChLinearLayoutManager layoutManager = new ChLinearLayoutManager(getBaseActivity());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
    }

    private class SelectionAdapter extends BaseQuickAdapter<VideoType>{
        private IndexGridViewAdapter adapter;

        public SelectionAdapter(List<VideoType> data) {
            super(R.layout.item_selection_video_type, data);
            adapter = new IndexGridViewAdapter(getBaseActivity());
        }

        @Override
        protected void convert(BaseViewHolder helper, VideoType item) {
            TextView tvVideoTypeName = helper.getView(R.id.tvVideoTypeName);
            ImageView typeIcon = helper.getView(R.id.typeIcon);
            TextView tvVideoTypeList = helper.getView(R.id.tvVideoTypeList);
            MyGridView gvVideoTypes = helper.getView(R.id.gvVideoTypes);

            List<VideoTypeInfo> vinfoList = item.getVinfoList();
            if(vinfoList != null){
                ImageLoadUtils.displayImage(getBaseActivity(),item.getImgUrl(),typeIcon, R.mipmap.mine_default_head);
                tvVideoTypeName.setText(item.getName());
                adapter.setVedioDetails(vinfoList);
                gvVideoTypes.setAdapter(adapter);
            }
        }

    }

    @Override
    protected void fetchObjectData() {
        Logger.i("精选可见");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(inflate);
    }
}
