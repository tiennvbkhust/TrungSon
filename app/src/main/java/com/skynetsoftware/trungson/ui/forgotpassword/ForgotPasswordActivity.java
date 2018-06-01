package com.skynetsoftware.trungson.ui.forgotpassword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skynetsoftware.trungson.MainActivity;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.SnackBarCallBack;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements SnackBarCallBack {

    @BindView(R.id.edtEmail)
    EditText edtCode;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @Override
    protected int initLayout() {
        return R.layout.acitivity_forgotpassword;
    }

    @Override
    protected void initVariables() {
        setSnackBarCallBack(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.btnsubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnsubmit:
                showToast("This message is fake to test system", AppConstant.POSITIVE);
                break;
        }
    }

    @Override
    public void onClosedSnackBar() {
        finish();
    }
}
