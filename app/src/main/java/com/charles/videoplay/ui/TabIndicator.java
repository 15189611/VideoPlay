package com.charles.videoplay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.charles.videoplay.util.BussinessUtil;

import java.util.ArrayList;


/**
 * @author Charles
 */

public class TabIndicator extends LinearLayout implements OnClickListener {
    private int mTabSize;
    private int mTabIndex = -1;
    private OnTabClickListener listener;
    private LayoutParams mTabParams;
    private final static int ID_PREFIX = 100000;

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public TabIndicator(Context context) {
        super(context);
        initializeView();
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.listener = listener;
    }

    public void initializeData(ArrayList<Tab> tabs) {
        initializeData(tabs, false);
    }

    public void initializeData(ArrayList<Tab> tabs, boolean needSeperate) {
        removeAllViews();
        if (!BussinessUtil.isValidate(tabs)) {
            throw new IllegalArgumentException("the tabs should not be 0");
        }
        mTabSize = tabs.size();
        TabView tab;
        for (int i = 0; i < mTabSize; i++) {
            Tab t = tabs.get(i);
            tab = new TabView(getContext());
            tab.setId(ID_PREFIX + i);
            if (t.isCanClickable()) {
                tab.setOnClickListener(this);
            }
            tab.initializeData(t);
            addView(tab, mTabParams);

            if (needSeperate && i != mTabSize - 1) {
                addView(new VerticalLineView(getContext()));
            }
        }
    }

    public void onMessageDataChanged(int index, int count) {
        TabView tabView = (TabView) (findViewById(ID_PREFIX + index));
        if (tabView != null) {
            tabView.notifyMessageDataChanged(count);
        }
    }

    public void onFlagDataChanged(int index, int count) {
        TabView tabView = (TabView) (findViewById(ID_PREFIX + index));
        if (tabView != null) {
            tabView.notifyFlagDataChanged(count);
        }
    }

    public void onDataChanged(int index, int messageCount, int flagCount) {
        TabView tabView = (TabView) (findViewById(ID_PREFIX + index));
        if (tabView != null) {
            tabView.notifyDataChaged(messageCount, flagCount);
        }
    }

    public void resetData(){
        for (int i = 0; i < mTabSize; i++) {
            View view = getChildAt(i);
            if (view != null && view instanceof TabView) {
                ((TabView)view).notifyDataChaged(0, 0);
            }
        }
    }

    private void initializeView() {
        setOrientation(LinearLayout.HORIZONTAL);
        mTabParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        mTabParams.weight = 1.0f;
    }

    @Override
    public void onClick(View v) {
        int index = v.getId() - ID_PREFIX;
        if (listener != null && mTabIndex != index) {
            if (listener.onCheckClickable(index)){
                listener.onTabClick(v.getId() - ID_PREFIX);
                v.setSelected(true);
                if (mTabIndex != -1) {
                    View old = findViewById(ID_PREFIX + mTabIndex);
                    old.setSelected(false);
                }
                mTabIndex = index;
            }
        }
    }

    public void updateTabSelect(int index) {
        try {
            if (mTabSize > 0 && mTabSize > index) {
                for (int i = 0; i < mTabSize; i++) {
                    View iView = getChildAt(i);
                    if (iView != null) {
                        if (index == i) {
                            iView.setSelected(true);
                            if (listener != null){
                                mTabIndex = index;
                                listener.onTabClick(index);
                            }
                        } else {
                            iView.setSelected(false);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public interface OnTabClickListener {
        boolean onCheckClickable(int position);
        void onTabClick(int inpositiondex);
    }

    public void setCurrentTab(int i) {
        if (i == mTabIndex) {
            return;
        }
        View view = findViewById(ID_PREFIX + i);
        onClick(view);
    }

    public int getCurrentTab(){
        return mTabIndex;
    }

}
