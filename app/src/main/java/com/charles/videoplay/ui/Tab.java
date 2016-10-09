package com.charles.videoplay.ui;


import android.support.v4.app.Fragment;

/**
 * @author Charles
 */

public class Tab {
    private int iconResId;
    private int flagCount;
    private int messageCount;
    private boolean canClickable;
    private String infoText;

    private Class<? extends Fragment> fragment;

    public Tab(int iconResId, String infoText, Class<? extends Fragment> fragment) {
        this.iconResId = iconResId;
        this.infoText = infoText;
        this.fragment = fragment;
        this.canClickable = true;
    }

    public Tab(int iconResId, String infoText, boolean canClickable, Class<? extends Fragment> fragment) {
        this.iconResId = iconResId;
        this.infoText = infoText;
        this.fragment = fragment;
        this.canClickable = canClickable;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public Class<? extends Fragment> getFragment() {
        return fragment;
    }

    public void setFragment(Class<? extends Fragment> fragment) {
        this.fragment = fragment;
    }

    public boolean isCanClickable() {
        return canClickable;
    }

    public void setCanClickable(boolean canClickable) {
        this.canClickable = canClickable;
    }
}
