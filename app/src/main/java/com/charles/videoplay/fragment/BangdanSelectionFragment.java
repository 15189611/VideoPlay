package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.charles.videoplay.base.BaseFragment;


/**
 * Created by Charles on 2016/10/25.
 */

public class BangdanSelectionFragment extends BaseFragment {

    private String title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            title = arguments.getString("name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getBaseActivity());
        textView.setText(title);
        return textView;
    }

    @Override
    protected void fetchObjectData() {

    }

    public static BangdanSelectionFragment newInstance(String name,int bid){
        BangdanSelectionFragment bangDanFragment = new BangdanSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("bid",bid);
        bundle.putString("name",name);
        bangDanFragment.setArguments(bundle);
        return bangDanFragment;
    }

}
