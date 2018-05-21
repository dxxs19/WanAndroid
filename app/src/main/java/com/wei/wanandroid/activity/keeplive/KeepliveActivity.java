package com.wei.wanandroid.activity.keeplive;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wei.wanandroid.WanApplication;
import com.wei.wanandroid.activity.BaseActivity;

public class KeepliveActivity extends BaseActivity
{
    private static KeepliveActivity sKeepliveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sKeepliveActivity = this;
        initWindow();
    }

    private void initWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.height = 1;
        layoutParams.width = 1;
        window.setAttributes(layoutParams);
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sKeepliveActivity = null;
    }

    public static void destroyOnePixelActivity() {
        if (sKeepliveActivity != null)
        {
            sKeepliveActivity.finish();
        }
    }

    public static void showOnePixelActivity() {
        Intent intent = new Intent(WanApplication.getAppContext(), KeepliveActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        WanApplication.getAppContext().startActivity(intent);
    }
}
