package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charles.videoplay.R;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.recyclerview.LayoutManager.ChLinearLayoutManager;
import com.charles.videoplay.recyclerview.View.PullRefreshRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Charles on 2016/10/25.
 */

public class BangdanSelectionFragment extends BaseFragment {

    @Bind(R.id.rvBangDan)
    PullRefreshRecycleView recycleView;

    private String title;
    private int bid;
    private List<BangdanVideos> mBangdanVideos = new ArrayList<>();

     private MyBangdanAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            title = arguments.getString("name");
            bid = arguments.getInt("bid");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bangdanselection, container, false);
        ButterKnife.bind(this,layout);
        initData();
        return layout;
    }

    private void initData() {
        ChLinearLayoutManager layoutManager = new  ChLinearLayoutManager(getBaseActivity());
        recycleView.setLayoutManager(layoutManager);
        adapter = new MyBangdanAdapter(mBangdanVideos);
        recycleView.setAdapter(adapter);
    }

    @Override
    protected void fetchObjectData() {
        UserRequest.newInstance().GetBangVideos(getBaseActivity(), "GetBangVideos", bid, 0,new ResponseListener<List<BangdanVideos>>() {
            @Override
            public void onSuccess(List<BangdanVideos> bangdanVideoses) {
                if(bangdanVideoses != null && bangdanVideoses.size() > 0){
                    mBangdanVideos.addAll(bangdanVideoses);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(AppException e) {
            }
        });
    }

    private class MyBangdanAdapter extends BaseQuickAdapter<BangdanVideos>{

        public MyBangdanAdapter(List<BangdanVideos> data) {
            super(R.layout.item_bangdan, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BangdanVideos item) {
            helper.setText(R.id.item_bangdan_nick,item.userNick);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public static BangdanSelectionFragment newInstance(String name,int bid){
        BangdanSelectionFragment bangDanFragment = new BangdanSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("bid",bid);
        bundle.putString("name",name);
        bangDanFragment.setArguments(bundle);
        return bangDanFragment;
    }


}
