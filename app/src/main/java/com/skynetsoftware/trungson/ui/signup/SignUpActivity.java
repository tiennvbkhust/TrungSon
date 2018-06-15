package com.skynetsoftware.trungson.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.privacy.PrivacyActivity;
import com.skynetsoftware.trungson.ui.verifyaccount.VerifyAccountActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpContract.View{
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.tvLinkToPrivacy)
    TextView tvLinkToPrivacy;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    SignUpContract.Presenter presenter;
    ProgressDialogCustom dialogLoading;

    @Override
    protected int initLayout() {
        return R.layout.acitivity_signup;
    }

    @Override
    protected void initVariables() {
        presenter = new SignUpPresenter(this);
        dialogLoading = new ProgressDialogCustom(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvLinkToPrivacy.setText(Html.fromHtml(getString(R.string.signup_privacy), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvLinkToPrivacy.setText(Html.fromHtml(getString(R.string.signup_privacy)));
        }
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.tvLinkToPrivacy, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLinkToPrivacy:
                startActivity(new Intent(SignUpActivity.this, PrivacyActivity.class));
                break;
            case R.id.btnSubmit:
                presenter.signUp(edtName.getText().toString(),edtEmail.getText().toString(),edtPhone.getText().toString());

                break;
        }
    }

    @Override
    public void signUpSuccess(String code) {
        Intent i =  new Intent(SignUpActivity.this, VerifyAccountActivity.class);
        i.putExtra("name",edtName.getText().toString());
        i.putExtra("email",edtEmail.getText().toString());
        i.putExtra("phone",edtPhone.getText().toString());
        i.putExtra("code",code);
        startActivity(i);
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        showToast(message,AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        showToast(message,AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }
}
