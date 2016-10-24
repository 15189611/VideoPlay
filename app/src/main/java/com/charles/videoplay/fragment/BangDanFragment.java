package com.charles.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charles.videoplay.base.BaseFragment;
import com.charles.videoplay.entity.BandanList;
import com.charles.videoplay.http.AppException;
import com.charles.videoplay.http.apiservice.UserRequest;
import com.charles.videoplay.http.responselistener.ResponseListener;

import java.util.List;

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
        UserRequest.newInstance().GetBangList(getBaseActivity(), "GetBangList", new ResponseListener<List<BandanList>>() {
            @Override
            public void onSuccess(List<BandanList> bandanLists) {
                Log.i("Charles2", "榜单大小="+bandanLists.size());
            }

            @Override
            public void onFailure(AppException e) {

            }
        });

        UserRequest.newInstance().GetBangVideos(getBaseActivity(), "GetBangVideos", 0, new ResponseListener<List<BandanList>>() {
            @Override
            public void onSuccess(List<BandanList> bandanLists) {

            }

            @Override
            public void onFailure(AppException e) {

            }
        });
    }

}
