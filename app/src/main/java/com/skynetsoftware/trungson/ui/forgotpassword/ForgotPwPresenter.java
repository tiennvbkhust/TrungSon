package com.skynetsoftware.trungson.ui.forgotpassword;

import android.util.Patterns;


import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.regex.Pattern;

public class ForgotPwPresenter implements ForgotPwContract.Presenter {
    ForgotPwContract.View view;
    ForgotPwContract.Interactor interactor;

    public ForgotPwPresenter(ForgotPwContract.View view) {
        this.view = view;
        interactor = new ForgotPwImpl(this);
    }

    @Override
    public void signUp( String email) {
        if (view == null) return;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onError("Bạn phải nhập đúng định dạng địa chỉ Email");
            return;
        } else if (!Pattern.compile(AppConstant.EMAIL_STRING).matcher(email).matches()) {
            view.onError("Bạn phải nhập đúng định dạng địa chỉ Email");
            return;
        }

        view.showProgress();
        interactor.doSignUp(email, AppConstant.TYPE_USER);

    }

    @Override
    public void signUpSuccess() {
        if (view == null) return;
        view.hiddenProgress();
        view.signUpSuccess();
    }

    @Override
    public void onDestroyView() {
        view = null;

    }

    @Override
    public void onErrorApi(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {

    }
}
