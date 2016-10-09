package com.charles.videoplay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charles.videoplay.R;
import com.charles.videoplay.util.BussinessUtil;


/**
 * Charles
 */

public class TabView extends RelativeLayout {

    private TextView mTabInfoLabel;
    private ImageView mTabIconImg;
    private ImageView mTabFlagIV;
    private TextView mTabCountTV;

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public TabView(Context context) {
        super(context);
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tab, this);
        mTabIconImg = (ImageView) findViewById(R.id.mTabIconImg);
        mTabInfoLabel = (TextView) findViewById(R.id.mTabInfoLabel);
        mTabFlagIV = (ImageView) findViewById(R.id.mTabFlagIV);
        mTabCountTV = (TextView) findViewById(R.id.mTabCountTV);
    }

    public void initializeData(Tab tab) {
        mTabIconImg.setImageResource(tab.getIconResId());
        if (BussinessUtil.isValid(tab.getInfoText())) {
            mTabInfoLabel.setVisibility(View.VISIBLE);
            mTabInfoLabel.setText(tab.getInfoText());
        } else {
            mTabInfoLabel.setVisibility(View.GONE);
        }
        updateCount(tab.getMessageCount(), tab.getFlagCount());
    }

    public void notifyFlagDataChanged(int count) {
        updateCount(0, count);
    }

    public void notifyMessageDataChanged(int count) {
        updateCount(count, 0);
    }

    public void notifyDataChaged(int messageCount, int flagCount) {
        updateCount(messageCount, flagCount);
    }

    private void updateCount(int messageCount, int flagCount) {
        if (messageCount > 0) {
            if (messageCount > 99) {
                mTabCountTV.setText("99+");
            } else {
                mTabCountTV.setText(String.valueOf(messageCount));
            }
            mTabCountTV.setVisibility(View.VISIBLE);

            mTabFlagIV.setVisibility(View.GONE);
        } else {
            mTabCountTV.setText("");
            mTabCountTV.setVisibility(View.GONE);

            if (flagCount > 0) {
                mTabFlagIV.setVisibility(View.VISIBLE);
            } else {
                mTabFlagIV.setVisibility(View.GONE);
            }
        }
    }

}
