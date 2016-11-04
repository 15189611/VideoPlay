package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.adapter.IndexGridViewAdapter2;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.VideoType;
import com.charles.videoplay.entity.VideoTypeInfo;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.recyclerview.LayoutManager.ChLinearLayoutManager;
import com.charles.videoplay.recyclerview.Listener.LoadDataListener;
import com.charles.videoplay.recyclerview.View.PullRefreshRecycleView;
import com.charles.videoplay.util.ImageLoadUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/10.
 */

public class SelectedFragment extends BaseFragment implements LoadDataListener {
    @Bind(R.id.rvVideoList)
    PullRefreshRecycleView recycleView;

    private SelectionAdapter adapter;
    private View inflate;
    private List<VideoType> datas = new ArrayList<>();
    private List<VideoType> videoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            videoList = (List<VideoType>) arguments.getSerializable("videoList");
        }
    }

    public static SelectedFragment newInstance(List<VideoType> videoList){
        SelectedFragment selectedFragment = new SelectedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoList", (Serializable) videoList);
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
        recycleView.setNoLoadMore(true);
        datas.addAll(videoList);
        adapter = new SelectionAdapter(datas);
        ChLinearLayoutManager layoutManager = new ChLinearLayoutManager(getBaseActivity());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setLoadDataListener(this);
    }

    @Override
    public void onRefresh() {  //刷新
        getData();
    }

    private void getData() {
        UserRequest.newInstance().getIndexData(getBaseActivity(), "GetHomeVideoTypes", new ResponseListener<List<VideoType>>() {
            @Override
            public void onSuccess(List<VideoType> videoTypes) {
                recycleView.refreshComplete();
                if (videoTypes.size() > 0) {
                    datas.clear();
                    datas.addAll(videoTypes);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(AppException e) {
                recycleView.refreshComplete();
            }
        });
    }

    @Override
    public void onLoadMore() {
    }

    private class SelectionAdapter extends BaseQuickAdapter<VideoType>{
        private IndexGridViewAdapter2 adapter;

        public SelectionAdapter(List<VideoType> data) {
            super(R.layout.item_selection_video_type, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, VideoType item) {
            TextView tvVideoTypeName = helper.getView(R.id.tvVideoTypeName);
            ImageView typeIcon = helper.getView(R.id.typeIcon);
            TextView tvVideoTypeList = helper.getView(R.id.tvVideoTypeList);

            RecyclerView gvVideoTypes = helper.getView(R.id.gvVideoTypes);
            gvVideoTypes.setLayoutManager(new GridLayoutManager(getActivity(),2));
            gvVideoTypes.setHasFixedSize(true);

            List<VideoTypeInfo> vinfoList = item.getVinfoList();
            if(vinfoList != null){
                ImageLoadUtils.displayImage(getBaseActivity(),item.getImgUrl(),typeIcon, R.mipmap.mine_default_head);
                tvVideoTypeName.setText(item.getName());
                adapter = new IndexGridViewAdapter2(getBaseActivity(),vinfoList);
                gvVideoTypes.setAdapter(adapter);
            }

            tvVideoTypeList.setClickable(true);
            tvVideoTypeList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),helper.getLayoutPosition()+"",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void fetchObjectData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(inflate);
    }
}
