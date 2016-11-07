package com.charles.videoplay.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.charles.videoplay.R;
import com.charles.videoplay.adapter.MainAdatper;
import com.charles.videoplay.base.BaseActivity;
import com.charles.videoplay.fragment.BangDanFragment;
import com.charles.videoplay.fragment.IndexFragment;
import com.charles.videoplay.fragment.LoveFragment;
import com.charles.videoplay.fragment.MineFragment;
import com.charles.videoplay.ijkplay.JCVideoPlayer;
import com.charles.videoplay.ui.Tab;
import com.charles.videoplay.ui.TabIndicator;
import com.charles.videoplay.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public class MainActivity extends BaseActivity implements TabIndicator.OnTabClickListener {
    @Bind(R.id.fm_content)
    NoScrollViewPager viewPager;
    @Bind(R.id.llToolbarParent)
    RelativeLayout toolbarParent;

    public static final int INDEX = 0;
    public static final int LOVE = 1;
    public static final int BANGDANG = 2;
    public static final int MYSELF = 3;

    private List<Fragment> fragmentList;
    private IndexFragment indexFragment;
    private LoveFragment loveFragment;
    private BangDanFragment bangDanFragment;
    private MineFragment mineFragment;

    private MainAdatper mainAdatper;
    private TabIndicator mMainIndicator;
    private FragmentManager fragmentManager;
    private int currentTabIndex;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initViewPager();
    }

    private void initViewPager() {
        fragmentList  = new ArrayList<>();
        indexFragment = IndexFragment.newInstance();
        loveFragment = LoveFragment.newInstance();
        bangDanFragment = BangDanFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        fragmentList.add(indexFragment);
        fragmentList.add(loveFragment);
        fragmentList.add(bangDanFragment);
        fragmentList.add(mineFragment);

        fragmentManager = getSupportFragmentManager();
        mainAdatper = new MainAdatper(fragmentManager,fragmentList);
        viewPager.setAdapter(mainAdatper);
        viewPager.setNoScroll(true);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new PageListener());

        mMainIndicator = (TabIndicator) findViewById(R.id.mMainIndicator);
        mMainIndicator.setOnTabClickListener(this);

        ArrayList<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(R.drawable.main_tab_index_selector, "首页", null));
        tabs.add(new Tab(R.drawable.main_tab_love_selector, "关注", null));
        tabs.add(new Tab(R.drawable.main_tab_bangdan_selector, "榜单", null));
        tabs.add(new Tab(R.drawable.main_tab_wode_selector, "我的", null));
        mMainIndicator.initializeData(tabs);
        mMainIndicator.setCurrentTab(0);
    }

    @Override
    protected void initView() {
        setTitle("首页");
        toolbarParent.setVisibility(View.GONE);
    }

    @Override
    protected void getRemoteData() {

    }

    @Override
    public boolean enableSwipeBack() {
        return false;
    }

    @Override
    public boolean onCheckClickable(int position) {
        return true;
    }

    @Override
    public void onTabClick(int positon) {
        JCVideoPlayer.releaseAllVideos();
        if (positon == INDEX) {
            viewPager.setCurrentItem(INDEX, false);
        } else if (positon == LOVE) {
            viewPager.setCurrentItem(LOVE, false);
        } else if (positon == BANGDANG) {
            viewPager.setCurrentItem(BANGDANG, false);
        } else if (positon == MYSELF) {
            viewPager.setCurrentItem(MYSELF, false);
        }
    }

    public class PageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentTabIndex = position;
            switch (position){
                case INDEX:
                    setIndexTitle();
                    break;
                case LOVE:
                    setLOVETitle();
                    break;
                case BANGDANG:
                    setBangDanTitle();
                    break;
                case MYSELF:
                    setMyselfTitle();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private void setMyselfTitle() {
        toolbarParent.setVisibility(View.VISIBLE);
        tvTitle.setText("我的");
    }

    private void setBangDanTitle() {
        toolbarParent.setVisibility(View.GONE);
        tvTitle.setText("榜单");
    }

    private void setLOVETitle() {
        toolbarParent.setVisibility(View.VISIBLE);
        tvTitle.setText("关注");
    }

    private void setIndexTitle() {
        toolbarParent.setVisibility(View.GONE);
        tvTitle.setText("首页");
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        clearFragments();
    }

    private void clearFragments() {
        if (fragmentManager != null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            if (null != fragments && fragments.size() != 0) {
                fragments.clear();
            }
        }
    }

}
