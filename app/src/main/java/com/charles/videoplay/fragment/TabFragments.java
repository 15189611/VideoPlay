package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Charles on 2016/10/9.
 */

public class TabFragments extends Fragment {

    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = getArguments().getString("title");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(title);
        return textView;
    }

    public static TabFragments newInstance(String title)
    {
        TabFragments tabFragments = new TabFragments();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        tabFragments.setArguments(bundle);
        return tabFragments;
    }

}
