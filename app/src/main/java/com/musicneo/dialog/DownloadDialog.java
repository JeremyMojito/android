package com.musicneo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.musicneo.R;


public class DownloadDialog extends Dialog {
    private View customView;
    private Button use_gg;
    private ImageView cancel;
    private Context mContext;
    private OnConfirmClickListener mOnConfirmClickListener;
    private OnCancelClickListener mOnCancelClickListener;
    private SeekBar mProgressBar;

    public DownloadDialog(Context context ,int max) {
        super(context, R.style.loadingDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        mProgressBar = customView.findViewById(R.id.mProgressBar);
        mProgressBar.setMax(max);
        mProgressBar.setProgress(0);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(false);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        initView();
        setCancelable(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        showInput(password);
//        handler.sendEmptyMessageDelayed(BOND,100);
    }


    private final int BOND = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BOND:
                    InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
            }
        }

    };

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void initView(){

    }

    /**
     * 询问弹框确定事件
     *
     * @param listener
     */
    public DownloadDialog setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.mOnConfirmClickListener = listener;
        return this;
    }


    public DownloadDialog setOnCancelClickListener(OnCancelClickListener listener) {
        this.mOnCancelClickListener = listener;
        return this;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(View view);
    }


    public interface OnCancelClickListener {
        void onCancelClickClick(View view);
    }

    public void updateLoading(int mstep){
        mProgressBar.setProgress(mstep);
    }
}
