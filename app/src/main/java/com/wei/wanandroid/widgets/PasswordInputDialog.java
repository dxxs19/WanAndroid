package com.wei.wanandroid.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wei.utillibrary.ToastUtils;
import com.wei.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: WEI
 * @date: 2018/5/29
 */
public class PasswordInputDialog extends Dialog
{
    private final static String TAG = "PasswordInputDialog";
    private Context mContext;
    private EditText mNo1EdtTxt, mNo2EdtTxt, mNo3EdtTxt, mNo4EdtTxt, mNo5EdtTxt, mNo6EdtTxt;
    private TextView mTipsTv;
    private List<EditText> mEditTexts;

    public PasswordInputDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PasswordInputDialog(@NonNull Context context, int themeResId, ResultCallback resultCallback) {
        super(context, themeResId);
        mResultCallback = resultCallback;
        initView(context);
    }

    protected PasswordInputDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context)
    {
        mContext = context;
        View view = View.inflate(mContext, R.layout.dialog_passinput, null);
        setContentView(view);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth(); // 获取屏幕宽、高用
        int height = wm.getDefaultDisplay().getHeight(); // 获取屏幕宽、高用
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (width * 0.9); // 宽度设置为屏幕的0.9
        p.height= (int) (height* 0.3);
        dialogWindow.setAttributes(p);
        mTipsTv = view.findViewById(R.id.tv_tips);
        mNo1EdtTxt = view.findViewById(R.id.no1);
        mNo2EdtTxt = view.findViewById(R.id.no2);
        mNo3EdtTxt = view.findViewById(R.id.no3);
        mNo4EdtTxt = view.findViewById(R.id.no4);
        mNo5EdtTxt = view.findViewById(R.id.no5);
        mNo6EdtTxt = view.findViewById(R.id.no6);
        mEditTexts = new ArrayList<>();
        mEditTexts.add(mNo1EdtTxt);
        mEditTexts.add(mNo2EdtTxt);
        mEditTexts.add(mNo3EdtTxt);
        mEditTexts.add(mNo4EdtTxt);
        mEditTexts.add(mNo5EdtTxt);
        mEditTexts.add(mNo6EdtTxt);
        initListener();
        showSoftInput();
    }

    private void showSoftInput()
    {
        focuse(mNo1EdtTxt);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void initListener()
    {
        int size = mEditTexts.size();
        for (int i = 0; i < size; i ++)
        {
            mEditTexts.get(i).addTextChangedListener(new NumTextWatcher(mEditTexts.get(i)));
        }
    }

    public void setTips(String tips) {
        mTipsTv.setText(tips);
    }

    class NumTextWatcher implements TextWatcher
    {
        EditText mCurrentEdtTxt, mNextEdtTxt;

        public NumTextWatcher(EditText currentEdtTxt) {
            mCurrentEdtTxt = currentEdtTxt;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mCurrentEdtTxt.hasFocus() && s.length() > 0)
            {
                int nextIndex = mEditTexts.indexOf(mCurrentEdtTxt) + 1;
                if (nextIndex <= mEditTexts.size() - 1)
                {
                    if (isInputAllPass())
                    {
                        closeDialogAndReturnResult();
                    }
                    else {
                        mNextEdtTxt = mEditTexts.get(nextIndex);
                        focuse(mNextEdtTxt);
                    }
                }
                else
                {
                    if (isInputAllPass()) {
                        closeDialogAndReturnResult();
                    }
                    else
                    {
                        ToastUtils.showToast(mContext, "请输入完整的6位密码！", Toast.LENGTH_SHORT);
                    }
                }
            }
        }
    }

    /**
     * 关闭弹框并回调结果
     */
    private void closeDialogAndReturnResult() {
        dismiss();
        Log.e(TAG, "输入完毕！");
        mResultCallback.onResult(getResult());
    }

    /**
     * 是否输入了6个数字的密码
     * @return
     */
    private boolean isInputAllPass()
    {
        return ( getResult().length() == 6 );
    }

    private String getResult()
    {
        int size = mEditTexts.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i ++)
        {
            EditText editText = mEditTexts.get(i);
            if ( !TextUtils.isEmpty( editText.getText() ) )
            {
                stringBuilder.append(editText.getText().toString());
            }
        }
        return stringBuilder.toString();
    }

    private void focuse(EditText targetView)
    {
        targetView.setFocusable(true);
        targetView.setFocusableInTouchMode(true);
        targetView.requestFocus();
    }

    ResultCallback mResultCallback;
    public interface ResultCallback
    {
        void onResult(String result);
    }
}
