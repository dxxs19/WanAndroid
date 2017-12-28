package com.wei.wanandroid.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * @author: WEI
 * @date: 2017/12/28
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobHandlerService extends JobService
{
    private final String TAG = getClass().getSimpleName();
    private JobScheduler mJobScheduler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"--- onStartCommand ---服务被创建");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(startId++,
                    new ComponentName(getPackageName(), JobHandlerService.class.getName()));
            //每隔5秒运行一次
            builder.setPeriodic(5000);
            builder.setRequiresCharging(true);
            //设置设备重启后，是否重新执行任务
            builder.setPersisted(true);
            builder.setRequiresDeviceIdle(true);

            if (mJobScheduler.schedule(builder.build()) <= JobScheduler.RESULT_FAILURE) {
                Log.e(TAG, "工作失败");
            } else {
                Log.e(TAG, "工作成功");
            }
        }
        return START_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(this, "服务启动", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "--- onStartJob ---开始工作");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Log.e(TAG, "--- onStopJob ---");
        if (!isServiceRunning(this, "com.wei.wanandroid.service")) {
            startService(new Intent(this, KeepAliveService.class));
        }
        return false;
    }

    // 服务是否运行
    public boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists)
        {// 获取运行服务再启动
            Log.e(TAG, info.processName);
            if (info.processName.equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

}
