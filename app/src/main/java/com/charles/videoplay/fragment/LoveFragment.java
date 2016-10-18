package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.charles.videoplay.R;
import com.charles.videoplay.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 2016/10/9.
 */

public class LoveFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = LoveFragment.class.getName();

    @Bind(R.id.sw)
    Switch aSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_love, container, false);
        ButterKnife.bind(this,layout);
        aSwitch.setOnCheckedChangeListener(this);
        aSwitch.setChecked(false);
        return layout;
    }

    public static LoveFragment newInstance(){
        LoveFragment loveFragment = new LoveFragment();
        Bundle bundle = new Bundle();
        loveFragment.setArguments(bundle);
        return loveFragment;
    }

    @Override
    protected void fetchObjectData() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i("Charles", "切换=="+ isChecked);
    }
}
