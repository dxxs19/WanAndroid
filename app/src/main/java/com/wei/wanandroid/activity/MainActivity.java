package com.wei.wanandroid.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.taobao.sophix.SophixManager;
import com.wei.utillibrary.FileUtil;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.http.OkHttp3Activity;
import com.wei.wanandroid.activity.image.FrescoActivity;
import com.wei.wanandroid.activity.image.GlideActivity;
import com.wei.wanandroid.activity.ndk.JNIActivity;
import com.wei.wanandroid.service.MyService;
import com.wei.wanandroid.widgets.CusImgView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity
{
    @BindView(R.id.imgView_move)
    CusImgView mMoveImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "--- onCreate ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 三者区别
        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();
        Vector vector = new Vector();

//        AIDLController.invokeAIDL(this);
//        startService()
//        registerReceiver()
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoveImgView.smoothScrollTo(-400, 0);
//        mMoveImgView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
//        ObjectAnimator.ofFloat(mMoveImgView, "translationX", 0, 400).setDuration(3000).start();
        TextUtils.equals(null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_lzlmain, menu);
        // 每次打开菜单都会回调onPrepareOptionsMenu，但onCreateOptionsMenu只回调一次
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "最后的菜单");
        // true 显示；false 不显示。因为 getParent() == null, 所以实际上 super.onPrepareOptionsMenu(menu) == true
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e(TAG, "onPrepareOptionsMenu, parent == " + getParent());
        // true 显示；false 不显示。因为 getParent() == null, 所以实际上 super.onPrepareOptionsMenu(menu) == true
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected, " + item.getItemId());
        switch (item.getItemId())
        {
            case R.id.action_fresco:
                Log.e(TAG, item.getTitle() + "");
                startActivity(new Intent(this, FrescoActivity.class));
//                Message message = new Message();
//                message.obj = System.currentTimeMillis();
//                if (mHandler == null)
//                {
//                    mHandler = new MyHandler();
//                }
//                mHandler.sendMessage(message);
                break;

            case R.id.action_jni:
                startActivity(new Intent(this, JNIActivity.class));
                break;

            case R.id.action_deviceid:
                startActivity(new Intent(this, OkHttp3Activity.class));
                break;

            case R.id.action_settings_notice:
                startActivity(new Intent(this, GlideActivity.class));
                break;

            case Menu.FIRST + 1:
                Log.e(TAG, "最后添加的菜单");
                new LooperThread().start();
                break;

            default:
        }
        return true;
    }

    // ****************************************** 单线程模型中Message、Handler、MessageQueue、Looper之间的关系 start ******************************************//
//   1. new LooperThread().start();
//   2. mHandler.sendMessage(message);
    static class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("MainActivity", "收到消息 ：" + msg.obj + "");
        }
    }

    MyHandler mHandler;
    class LooperThread extends Thread
    {
        @Override
        public void run() {
            // 初始化一个Looper，放到 ThreadLocal 中，与线程绑定。主线程已经有Looper了，所以不需要这一行代码
            Looper.prepare();
            mHandler = new MyHandler();
            // 先从 ThreadLocal 中拿到当前线程的Looper，然后 通过 Looper 拿到 MessageQueue ，接着循环地
            // 从 MessageQueue 中拿到Message并通过dispatchMessage把Message分发出去。消息最终在Handler的
            // 回调方法 handleMessage 中执行。
            Looper.loop();
        }
    }
    // ****************************************** 单线程模型中Message、Handler、MessageQueue、Looper之间的关系 end ******************************************//

    public void setPermission(View view)
    {
        Log.e(TAG, "--- setPermission ---");
        Intent mAccessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mAccessibleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mAccessibleIntent);

        FileUtil.saveFile("hello , this is a test !");
        FileUtil.saveFile("I love beautyleg !");
    }

}
