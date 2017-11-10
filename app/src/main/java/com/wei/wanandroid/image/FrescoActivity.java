package com.wei.wanandroid.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wei.processorlib.AutoCreat;
import com.wei.processorlib.MyRuntimePermissions;
import com.wei.wanandroid.R;

/**
 * @author WEI
 */
@MyRuntimePermissions
public class FrescoActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
    }
}
