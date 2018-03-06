package com.wei.wanandroid.activity.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Administrator
 */
@RuntimePermissions
public class RuntimePermissionActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuntimePermissionActivityPermissionsDispatcher.getGrantWithCheck(RuntimePermissionActivity.this);
            }
        });
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE})
    void getGrant()
    {
        Log.e(TAG, "showCamera");
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE})
    void showDeniedTips()
    {
        showMsg("用户拒绝授权", 500);
    }

    @OnShowRationale( { Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE} )
    void showRationaleForRecord(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("不给", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("挑战需要拍照权限，应用将要申请相机权限")
                .show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE})
    void showNeverAsk()
    {
        showMsg("不再提醒", 500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RuntimePermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
