package com.skynetsoftware.trungson.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;


/**
 * Created by thaopt on 9/19/17.
 */

public class DialogTwoButtonUtils extends Dialog {
    private TextView mTitleTextView, mContentTextView;
    private Context mContext;

    public DialogTwoButtonUtils(@NonNull Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_two_button);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
        initView();
    }

    private void initView() {
        mTitleTextView = (TextView) findViewById(R.id.title_dialog_one_button);
        mContentTextView = (TextView) findViewById(R.id.content_dialog_one_button);
        findViewById(R.id.btn_dialog_show_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoButtonOnClick.cancel();
            }
        });
        findViewById(R.id.btn_dialog_show_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoButtonOnClick.okClick();
            }
        });
    }

    public void setText(String title, String content) {
        mTitleTextView.setText(title);
        mContentTextView.setText(content);
    }

    //call back

    public interface DialogTwoButtonOnClick {
        void cancel();

        void okClick();
    }

    private DialogTwoButtonOnClick twoButtonOnClick;

    public void setDialogTwoButtonOnClick(DialogTwoButtonOnClick buttonOnClick) {
        this.twoButtonOnClick = buttonOnClick;
    }
}
