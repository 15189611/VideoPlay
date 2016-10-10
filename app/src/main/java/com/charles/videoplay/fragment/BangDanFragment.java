package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charles.videoplay.base.BaseFragment;

/**
 * Created by Administrator on 2016/10/9.
 */

public class BangDanFragment extends BaseFragment {

    private static final String TAG = BangDanFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(TAG);
        return textView;
    }

    public static BangDanFragment newInstance(){
        BangDanFragment bangDanFragment = new BangDanFragment();
        Bundle bundle = new Bundle();
        bangDanFragment.setArguments(bundle);
        return bangDanFragment;
    }

    @Override
    protected void fetchObjectData() {

    }
}
