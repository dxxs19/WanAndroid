package com.wei.wanandroid.activity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wei.qrcodescanner.ScanerActivity;
import com.wei.utillibrary.FileUtil;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.fragment.FragActivity;
import com.wei.wanandroid.activity.http.OkHttp3Activity;
import com.wei.wanandroid.activity.image.FrescoActivity;
import com.wei.wanandroid.activity.image.GlideActivity;
import com.wei.wanandroid.activity.keeplive.KeepLiveManager;
import com.wei.wanandroid.activity.listview.ListViewActivity;
import com.wei.wanandroid.activity.memoryopt.LeakCanaryActivity;
import com.wei.wanandroid.activity.ndk.JNIActivity;
import com.wei.wanandroid.activity.recyclerview.RecyclerViewActivity;
import com.wei.wanandroid.activity.rx.RxJavaActivity;
import com.wei.wanandroid.activity.webview.WebActivity;
import com.wei.wanandroid.bean.EventMessage;
import com.wei.wanandroid.service.MyIntentService;
import com.wei.wanandroid.service.MyService;
import com.wei.wanandroid.widgets.CusImgView;
import com.wei.wanandroid.widgets.PasswordInputDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *
 * @author Administrator
 */
public class MainActivity extends BaseActivity
{
    @BindView(R.id.imgView_move)
    CusImgView mMoveImgView;
    @BindView(R.id.btn_test)
    Button mTestBtn;
    @BindView(R.id.tv_content)
    TextView mContentTv;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Unbinder mUnbinder;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "--- onCreate ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initMediaPlayer();
        testSharedPreferences();
//        testNotification();
//        testIntentService();
        testHandlerThread();
//        testAnimator();
        testAnyThing();
        testDelayLoad();
        testOnePixelKeeplive();
        testAsyncTask();
//        testCusDialog("请输入开门密码！");
        EventBus.getDefault().register(this);
    }

    private void testCusDialog(String tips)
    {
        PasswordInputDialog passwordInputDialog = new PasswordInputDialog(this, R.style.Dialog, result -> {
            Log.e(TAG, "密码为：" + result);
            if ("123456".equals(result))
            {
                Log.e(TAG, "密码正确，正尝试开门......");
            }
            else
            {
                testCusDialog("密码错误，请重新输入！");
            }
        });
        passwordInputDialog.setTips(tips);
        passwordInputDialog.show();
    }

    private void testAsyncTask()
    {
        StringBuilder stringBuilder = new StringBuilder()
                .append("CPU_COUNT : ")
                .append(CPU_COUNT)
                .append(", CORE_POOL_SIZE : ")
                .append(CORE_POOL_SIZE)
                .append(", MAXIMUM_POOL_SIZE : ")
                .append(MAXIMUM_POOL_SIZE);
        Log.e(TAG, stringBuilder.toString());
        CusAsyncTask cusAsyncTask = new CusAsyncTask();
        // 并行运行
        cusAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 100L);
        // 串行运行
//        cusAsyncTask.execute(100L);
        CusAsyncTask cusAsyncTask2 = new CusAsyncTask();
        // 并行运行
        cusAsyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 50L);
        // 串行运行
//        cusAsyncTask2.execute(50L);
    }

    static class CusAsyncTask extends AsyncTask<Long, Integer, Long>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Long... values)
        {
            long startTime = System.currentTimeMillis();
            for (int progress = 0; progress < values[0]; progress ++)
            {
                publishProgress(progress);
            }
            return System.currentTimeMillis() - startTime;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("onProgressUpdate ", values[0] + "");
        }

        @Override
        protected void onPostExecute(Long integer) {
            super.onPostExecute(integer);
            Log.e("onPostExecute ", "耗时 : " + integer + "ms");
        }
    }

    /**
     * 1像素保活
     */
    private void testOnePixelKeeplive() {
        KeepLiveManager.registerBroadCast(this);
    }

    /**
     * 延迟加载（等页面加载完毕之后再加载数据）
     */
    private void testDelayLoad()
    {
        // 优化的延迟加载。页面加载完后再执行。此时布局已经测量、布局、绘画完毕，可以获取控件的尺寸
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "--- 页面加载完毕了！ ---");
                Log.e(TAG, "mMoveImgView 宽高为 : " + mMoveImgView.getWidth() + ", " + mMoveImgView.getHeight());
            }
        });
        getMainLooper().dump(new Printer() {
            @Override
            public void println(String x) {
                Log.e(TAG, x);
            }
        }, "onCreate");
    }

    private void testAnyThing()
    {
//        sHandler.getLooper().quit();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_RIGHT);
    }

    /**
     * 插值器（Interpolator）决定 值 的变化模式（匀速、加速blabla）
     * 估值器（TypeEvaluator）决定 值 的具体变化数值
     */
    private void testAnimator()
    {
        ValueAnimator animator = ValueAnimator.ofInt(0, 3);
        animator.setDuration(3000);
        // 估值器
        animator.setEvaluator(new IntEvaluator());
        // 插值器
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(1000);
        animator.addUpdateListener(animation -> Log.e(TAG, animation.getAnimatedValue() + ""));
        animator.start();
    }

    private void testHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("testHandlerThread");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, msg.arg1 + ", " + msg.arg2 + ", " + msg.obj + ", " + msg.what);
                handlerThread.quit();
            }
        };
        handler.sendEmptyMessage(3);
    }

    private void testIntentService() {
        for (int i = 0; i < 3; i++) {
            MyIntentService.startActionBaz(this, "xiang", "wei");
//            MyIntentService.startActionFoo(this, "yi", "fei");
        }
    }

    private void testNotification() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification();
            }
        }, 3000);
    }

    private void testSharedPreferences() {
        SharedPreferences sharedPreferences = (SharedPreferences) getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", "value");
        editor.commit();
    }

    private void initMediaPlayer() {
        try {
            mediaPlayer.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法可以解决在部分手机无法弹出悬浮通知的问题
     */
    private void showNotification() {
        String title = "收到一条新消息！";
        String content = "内容内容内容";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, JNIActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = getNotification(this, pendingIntent, R.mipmap.ic_launcher, title, content, true, true);
        // 此行代码不能少，否则即使开了悬浮显示权限也无法悬浮显示
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        try {
            notificationManager.notify(1, notification);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private Notification getNotification(Context mContext, PendingIntent pendingIntent, int icon, String title, String content, boolean ongoing, boolean autoCancel) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(autoCancel)
                .setOngoing(ongoing)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setTicker(content)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_ALL | NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        return builder.build();
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
        Log.e(TAG, "onResume  mMoveImgView 宽高为 : " + mMoveImgView.getWidth() + ", " + mMoveImgView.getHeight());
//        startService(new Intent(this, MyService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        testKeeplive();
    }

    private void testKeeplive() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
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
        switch (item.getItemId()) {
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

            case R.id.action_rxjava:
                startActivity(new Intent(this, RxJavaActivity.class));
                break;

            case R.id.action_leak:
                startActivity(new Intent(this, LeakCanaryActivity.class));
                break;

            case R.id.action_recycler:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;

            case R.id.action_listview:
                startActivity(new Intent(this, ListViewActivity.class));
                break;

            case R.id.action_webview:
//                String url = "https://blog.csdn.net/lsyz0021/article/details/56677132";
                String url = "https://blog.csdn.net/lsyz0021/article/details/56677132";
                WebActivity.startWebActivity(this, "", url);
                break;

            case R.id.action_viewPager:
                startActivity(new Intent(this, FragActivity.class));
                break;

            case Menu.FIRST + 1:
                Log.e(TAG, "最后添加的菜单");
                new LooperThread().start();
                break;

            default:
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mUnbinder.unbind();
//        Debug.stopMethodTracing();
        KeepLiveManager.unRegisterBroadCast(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // ****************************************** 单线程模型中Message、Handler、MessageQueue、Looper之间的关系 start ******************************************//
//   1. new LooperThread().start();
//   2. mHandler.sendMessage(message);
    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("MainActivity", "收到消息 ：" + msg.obj + "");
        }
    }

    MyHandler mHandler;

    class LooperThread extends Thread {
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

    public void setPermission(View view) {
        Log.e(TAG, "--- setPermission ---");
        Intent mAccessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mAccessibleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mAccessibleIntent);

        FileUtil.saveFile("hello , this is a test !");
        FileUtil.saveFile("I love beautyleg !");

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * 0.8), 0);
        Log.e(TAG, "currentVolume : " + currentVolume + ", maxVolume : " + maxVolume + ", 80% * maxVolume : " + maxVolume * 0.8);
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
//        Log.e(TAG, "currentVolume : " + currentVolume);
    }

    @OnClick({R.id.btn_test, R.id.btn_qrscan})
    public void clickOpt(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_test:
                testCusDialog("请输入开门密码！");
                break;

            case R.id.btn_qrscan:
                ScanerActivity.qrScan(this);
//                Intent intent = new Intent(this, ScanerActivity.class);
//                startActivityForResult(intent, ScanerActivity.REQUEST_SCANER_RESULT);
                break;

                default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventMessage message) {
        Log.e(TAG, "message from service : " + message.mMsg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult " + requestCode + "　result " + resultCode);
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ScanerActivity.REQUEST_SCANER_RESULT && resultCode == Activity.RESULT_OK) {
            Bundle data = intent.getExtras();
            if (data != null) {
                String code = data.getString(ScanerActivity.KEY_SCAN_RESULT);
                Log.d(TAG, "扫描结果: " + code);
                mContentTv.setText(code);
            }
        }
    }
}
