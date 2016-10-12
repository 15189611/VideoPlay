package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charles.videoplay.R;
import com.charles.videoplay.adapter.TabAdapter;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.VedioDetails;
import com.charles.videoplay.entity.VideoDetailsList;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.listener.ResponseListener;
import com.charles.videoplay.net.IndexRequest;
import com.charles.videoplay.recyclerview.LayoutManager.ChStaggeredGridLayoutManager;
import com.charles.videoplay.recyclerview.View.PullRefreshRecycleView;
import com.charles.videoplay.util.JsonParser;
import com.charles.videoplay.util.Logger;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public class TabFragments extends BaseFragment {

    @Bind(R.id.rvTabView)
    PullRefreshRecycleView recycleView;
    
    private String title;
    private int vid;
    private List<VedioDetails> datas;
    private TabAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = getArguments().getString("title");
            vid = getArguments().getInt("vid");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this,inflate);
        return inflate;
    }

    private void initData() {
        ChStaggeredGridLayoutManager manager = new ChStaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        adapter = new TabAdapter(getBaseActivity(),datas);
        recycleView.setAdapter(adapter);

    }

    public static TabFragments newInstance(String title , int vid)
    {
        TabFragments tabFragments = new TabFragments();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("vid",vid);
        tabFragments.setArguments(bundle);
        return tabFragments;
    }

    @Override
    protected void fetchObjectData() {
        try {
            IndexRequest.getVideoListOfType(getBaseActivity(), 0, vid, new ResponseListener<String>() {
                @Override
                public void onSuccess(String s) {
                    VideoDetailsList vedioDetails = JsonParser.deserializeByJson(s, VideoDetailsList.class);
                    if(vedioDetails.getErrcode() == 0){
                        datas = vedioDetails.getData();
                        initData();
                        Logger.i(vedioDetails.getData().size() +"");
                    }
                }

                @Override
                public void onFailure(AppException e) {
                }
            });
        } catch (Exception e) {
        }
    }
}
