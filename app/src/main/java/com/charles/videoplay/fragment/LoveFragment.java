package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Charles on 2016/10/9.
 */

public class LoveFragment extends Fragment {

    private static final String TAG = LoveFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(TAG);
        return textView;
    }

    public static LoveFragment newInstance(){
        LoveFragment loveFragment = new LoveFragment();
        Bundle bundle = new Bundle();
        loveFragment.setArguments(bundle);
        return loveFragment;
    }

}
