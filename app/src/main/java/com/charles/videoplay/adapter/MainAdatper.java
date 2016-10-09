package com.charles.videoplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 2016/10/9.
 */

public class MainAdatper extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MainAdatper(FragmentManager fm, List<Fragment> list) {
        super(fm);
        if(list == null){
            list = new ArrayList<>();
        }else{
            this.list = list ;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {

        return list.size() > 0 ? list.size() : 0;
    }

}
