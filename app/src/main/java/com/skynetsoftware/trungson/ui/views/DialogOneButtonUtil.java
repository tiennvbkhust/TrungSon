package com.skynetsoftware.trungson.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;


/**
 * Created by thaopt on 8/28/17.
 */

public class DialogOneButtonUtil extends Dialog {
    private TextView mTitleTextView, mContentTextView;
    private int mType = 0;
    private Context mContext;

    public DialogOneButtonUtil(@NonNull Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_one_button);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
        initView();
    }

    public DialogOneButtonUtil(@NonNull Context context, boolean isOurStandingJob) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
        initView();
    }

    private void initView() {
        mTitleTextView = (TextView) findViewById(R.id.title_dialog_one_button);
        mContentTextView = (TextView) findViewById(R.id.content_dialog_one_button);
        findViewById(R.id.btn_dialog_show_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType == 2) {
                   if(mListener!=null) mListener.okClick();
                    ((Activity) mContext).finish();
                } else {
                    
                }
                dismiss();
            }
        });
    }

    public void setText(String title, String content) {
        mTitleTextView.setText(title);
        mContentTextView.setText(content);
    }

    public void setContentHtml(Spannable spannable) {
        mContentTextView.setText(spannable);
    }

    public void setType(int type) {
        this.mType = type;
    }

    //callback

    public interface DialogOneButtonClickListener {
        void okClick();
    }

    private DialogOneButtonClickListener mListener;

    public void setDialogOneButtonClick(DialogOneButtonClickListener listener) {
        this.mListener = listener;
    }
}
