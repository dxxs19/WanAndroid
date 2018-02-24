package com.wei.utillibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author: WEI
 * @date: 2017/11/6
 */

public class ToastUtils {

    public static void showToast(final Context context, final String msg, final int period) {
        TipsHandler tipsHandler = new TipsHandler(context);
        Message message = new Message();
        message.what = 1;
        message.arg1 = period;
        message.obj = msg;
        tipsHandler.sendMessage(message);
    }

    private static class TipsHandler extends Handler {
        private final WeakReference<Context> mContextWeakReference;

        public TipsHandler(Context context) {
            mContextWeakReference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mContextWeakReference.get();
            if (context == null) {
                super.handleMessage(msg);
                return;
            }
            int period = msg.arg1;
            switch (msg.what)
            {
                case 1:
                    Toast.makeText(context, msg.obj.toString(), (period <= 0 ? Toast.LENGTH_SHORT : period)).show();
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

}
