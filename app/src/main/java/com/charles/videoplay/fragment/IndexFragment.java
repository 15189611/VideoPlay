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
import com.charles.videoplay.entity.DataHomeVideoType;
import com.charles.videoplay.entity.ResponseVideoType;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.listener.ResponseListener;
import com.charles.videoplay.net.IndexRequest;
import com.charles.videoplay.util.JsonUtil;
import com.charles.videoplay.util.Logger;
import com.charles.videoplay.widget.NoScrollViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
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
    private List<String> tabDatas = Arrays.asList("精选","美女","娱乐","涨姿势","配音","体育","动画","可爱","游戏");
    private ResponseVideoType dataHomeVideoType;

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
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        initTabs(tabDatas);
    }

    private void initTabs(List<String> tabs) {
        if(tabs.size() >=7){
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else{
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        for (int i = 0 ;i < tabs.size() ;i ++){
            if(i == 0){
                fragmentList.add(SelectedFragment.newInstance());
            }else{
                fragmentList.add(TabFragments.newInstance(tabDatas.get(i)));
            }
        }
        adapter = new IndexPagerAdapter(getChildFragmentManager(),fragmentList,tabs);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void fetchObjectData() {
        try {
            IndexRequest.getHomeVideoTypes(new ResponseListener<String>() {
                                               @Override
                                               public void onSuccess(String s) {
                                                   Gson gson = new Gson();
                                                   ResponseVideoType videoType = gson.fromJson(s, ResponseVideoType.class);
                                                   if(videoType.getData() != null){
                                                       Logger.i(videoType.getData().get(1).getName());
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
