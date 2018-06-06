package com.wei.wanandroid.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wei.wanandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends LazyFragment {


    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "--- onCreateView ---");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }

    @Override
    public void onFirstInit() {
        Log.e(TAG, "--- onFirstInit() ---");
    }

    @Override
    public void onLazyResume() {
        Log.e(TAG, "--- onLazyResume() ---");
    }

    @Override
    public void onLazyPause() {
        Log.e(TAG, "--- onLazyPause() ---");
    }
}
