package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charles.videoplay.R;
import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.BangdanList;
import com.charles.videoplay.entity.BangdanVideos;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;
import com.charles.videoplay.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public class BangDanFragment extends BaseFragment {

    private static final String TAG = BangDanFragment.class.getName();

    @Bind(R.id.bd_layout)
    TabLayout mTabLayout;
    @Bind(R.id.bd_index)
    NoScrollViewPager viewPager;

    private List<BangdanList> mBandanList = new ArrayList<>();
    private List<BangdanVideos> mBangdanVideos = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private BangdanPagerAdapter  bangdanPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bangdan, container, false);
        ButterKnife.bind(this,layout);
        initView();
        return layout;
    }

    private void initView() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        bangdanPagerAdapter = new BangdanPagerAdapter(getChildFragmentManager(),mBandanList,fragmentList);
        viewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(viewPager);
    }

    private class BangdanPagerAdapter extends FragmentPagerAdapter {

        private List<BangdanList> bangdanLists;
        private List<Fragment> fragmentList;

        public BangdanPagerAdapter(FragmentManager fm, List<BangdanList> bangdanLists , List<Fragment> fragmentList) {
            super(fm);
            this.bangdanLists = bangdanLists;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size() > 0 ?fragmentList.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return bangdanLists.get(position).name;
        }
    }

    @Override
    protected void fetchObjectData() {
        UserRequest.newInstance().GetBangList(getBaseActivity(), "GetBangList", new ResponseListener<List<BangdanList>>() {
            @Override
            public void onSuccess(List<BangdanList> bandanLists) {
                if(bandanLists == null){
                    return;
                }
                for (BangdanList list : bandanLists){
                    fragmentList.add(BangdanSelectionFragment.newInstance(list.name,list.bid));
                }
                mBandanList.addAll(bandanLists);
                viewPager.setAdapter(bangdanPagerAdapter);
            }

            @Override
            public void onFailure(AppException e) {
            }
        });

        UserRequest.newInstance().GetBangVideos(getBaseActivity(), "GetBangVideos", 1, 0,new ResponseListener<List<BangdanVideos>>() {
            @Override
            public void onSuccess(List<BangdanVideos> bangdanVideoses) {
                if(bangdanVideoses != null && bangdanVideoses.size() > 0){
                    mBangdanVideos.addAll(bangdanVideoses);
                }
            }

            @Override
            public void onFailure(AppException e) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public static BangDanFragment newInstance(){
        BangDanFragment bangDanFragment = new BangDanFragment();
        Bundle bundle = new Bundle();
        bangDanFragment.setArguments(bundle);
        return bangDanFragment;
    }

}
