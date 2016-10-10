package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.util.Logger;

/**
 * Created by Charles on 2016/10/10.
 */

public class SelectedFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("精选页面");
        return textView;
    }

    public static SelectedFragment newInstance(){
        SelectedFragment selectedFragment = new SelectedFragment();
        Bundle bundle = new Bundle();
        selectedFragment.setArguments(bundle);
        return selectedFragment;
    }

    @Override
    protected void fetchObjectData() {
        Logger.i("精选可见");
    }
}
