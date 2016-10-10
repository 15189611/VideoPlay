package com.charles.videoplay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charles.videoplay.R;
import com.charles.videoplay.activity.MainActivity;
import com.charles.videoplay.base.BaseActivity;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.VideoType;
import com.charles.videoplay.entity.VideoTypeInfo;
import com.charles.videoplay.entity.IndexVideoList;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.listener.ResponseListener;
import com.charles.videoplay.net.IndexRequest;
import com.charles.videoplay.util.JsonParser;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.widget.NoScrollViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public class IndexFragment extends BaseFragment {
    private static final String TAG = IndexFragment.class.getName();

    @Bind(R.id.tl_layout)
    TabLayout mTabLayout;
    @Bind(R.id.vp_index)
    NoScrollViewPager viewPager;

    private View layout;
    private BaseActivity mainActivity;
    private IndexPagerAdapter adapter;
    private List<Fragment> fragmentList ;

    private IndexVideoList videoList;
    private List<VideoType> videoTypes;
    private List<String> tabs ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_index,container, false);
        ButterKnife.bind(this,layout);
        initView();
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        tabs = new ArrayList<>();
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        adapter = new IndexPagerAdapter(getChildFragmentManager(),fragmentList,tabs);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void fetchObjectData() {
        try {
            IndexRequest.getHomeVideoTypes(new ResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            videoList = JsonParser.deserializeByJson(response, IndexVideoList.class);
                            if(videoList.getErrcode() == 0){
                                if (videoList.getData() != null) {
                                    videoTypes = videoList.getData();
                                    initTabs(videoTypes);
                                }
                            }
                        }

                        @Override
                        public void onFailure(AppException e) {
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTabs(List<VideoType> videoTypes) {
        if(videoTypes == null || videoTypes.size() == 0 ){
            return;
        }

        tabs.clear();
        if(videoTypes.size() >=7){
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else{
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        for (int i = 0; i < videoTypes.size()+1; i ++){
            if(i == 0){
                tabs.add(i,"精选");
                fragmentList.add(SelectedFragment.newInstance(videoList));
            }else{
                tabs.add(i,videoTypes.get(i-1).getName());
                fragmentList.add(TabFragments.newInstance(videoTypes.get(i-1).getName()));
            }
        }
        viewPager.setAdapter(adapter);
    }


    private class IndexPagerAdapter extends FragmentStatePagerAdapter{
        private List<String> tabDatas;
        private List<Fragment> fragments;

        public IndexPagerAdapter(FragmentManager fm ,List<Fragment> fragments,List<String> tabDatas) {
            super(fm);
            this.tabDatas = tabDatas;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size() >0 ? fragments.size() : 0;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabDatas.get(position);
        }
    }


    public static IndexFragment newInstance(){
        IndexFragment indexFragment = new IndexFragment();
        Bundle bundle = new Bundle();
        indexFragment.setArguments(bundle);
        return indexFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(layout);
    }

}
