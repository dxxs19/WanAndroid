package com.wei.wanandroid.activity;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wei.wanandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment
{
    protected final String TAG = getClass().getSimpleName();

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.e(TAG, "--- onCreate ---");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context)
    {
        Log.e(TAG, "--- onAttach ---");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "--- onDetach ---");
        super.onDetach();
    }

}
