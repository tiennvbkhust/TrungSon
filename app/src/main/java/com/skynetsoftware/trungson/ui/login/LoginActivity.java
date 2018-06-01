package com.skynetsoftware.trungson.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.forgotpassword.ForgotPasswordActivity;
import com.skynetsoftware.trungson.ui.signup.SignUpActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.edtEmailPhone)
    EditText edtEmailPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @Override
    protected int initLayout() {
        return R.layout.acitivity_login;
    }

    @Override
    protected void initVariables() {
        showToast("This message is fake to test system", AppConstant.NEGATIVE);

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.tvForgotPassword, R.id.submit, R.id.submitFB, R.id.submitGoogle, R.id.tvLinkToSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

                break;
            case R.id.submit:
                break;
            case R.id.submitFB:
                break;
            case R.id.submitGoogle:
                break;
            case R.id.tvLinkToSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }
}
