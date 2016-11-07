package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charles.videoplay.R;
import com.charles.videoplay.adapter.MyBangdanAdapter;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.ijkplay.JCVideoPlayer;
import com.charles.videoplay.recyclerview.LayoutManager.ChLinearLayoutManager;
import com.charles.videoplay.videolistutil.calculator.SingleListViewItemActiveCalculator;
import com.charles.videoplay.videolistutil.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Charles on 2016/10/25.
 */

public class BangdanSelectionFragment extends BaseFragment {

/*    @Bind(R.id.rvBangDan)
    PullRefreshRecycleView recycleView;*/

    @Bind(R.id.rvBangDan)
    RecyclerView recycleView;

    private String title;
    private int bid;
    private int mPage;
    private List<BangdanVideos> mBangdanVideos = new ArrayList<>();
    private MyBangdanAdapter adapter;

    private int mScrollState;
    private SingleListViewItemActiveCalculator mCalculator;

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
        adapter = new MyBangdanAdapter(getActivity(),recycleView,mBangdanVideos);

        mCalculator = new SingleListViewItemActiveCalculator(adapter,
                new RecyclerViewItemPositionGetter(layoutManager, recycleView));

        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemCount() > 0){
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mScrollState);
            }
        });
       /* recycleView.setLoadDataListener(new LoadDataListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getBangVideos(false,mPage);
            }

            @Override
            public void onLoadMore() {
                mPage++;
                getBangVideos(true,mPage);
            }
        });*/
    }

    @Override
    protected void fetchObjectData() {
       /* recycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recycleView.forceRefresh();
            }
        }, 200);*/
        mPage = 0;
        getBangVideos(false,mPage);
    }

    private void getBangVideos(final boolean isLoadMore , final int Page) {
        UserRequest.newInstance().GetBangVideos(getBaseActivity(), "GetBangVideos", bid, Page, new ResponseListener<List<BangdanVideos>>() {
            @Override
            public void onSuccess(List<BangdanVideos> bangdanVideoses) {
                if(!isLoadMore){
                    mBangdanVideos.clear();
                   // recycleView.refreshComplete();
                }else {
                  //  recycleView.loadMoreComplete();
                }

                if(bangdanVideoses != null && bangdanVideoses.size() > 0){
                    mBangdanVideos.addAll(bangdanVideoses);
                    adapter.notifyDataSetChanged();
                }else {
                    if (Page == 0) {
                        mBangdanVideos.clear();
                        adapter.notifyDataSetChanged();
                    }
                 //   recycleView.loadNoMoreView();
                }

            }

            @Override
            public void onFailure(AppException e) {
                if (isLoadMore) {
                  //  recycleView.loadMoreComplete();
                } else {
                 //   recycleView.refreshComplete();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
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
