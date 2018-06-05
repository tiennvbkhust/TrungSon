package com.skynetsoftware.trungson.ui.signup;

import android.util.Patterns;


import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.regex.Pattern;

public class SignUpPresenter implements SignUpContract.Presenter {
    SignUpContract.View view;
    SignUpContract.Interactor interactor;

    public SignUpPresenter(SignUpContract.View view) {
        this.view = view;
        interactor = new SignUpRemoteImpl(this);
    }

    @Override
    public void signUp(String fullname, String email, String phone) {
        if (view == null) return;
        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() ) {
            view.onError("Vui lòng điền đầy đủ thông tin.");
            return;
        }


        if (Pattern.matches(AppConstant.NAME_PARTEN, fullname)) {
            view.onError("Tên không được có ký tự đặc biệt");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onError("Bạn phải nhập đúng định dạng địa chỉ Email");
            return;
        } else if (!Pattern.compile(AppConstant.EMAIL_STRING).matcher(email).matches()) {
            view.onError("Bạn phải nhập đúng định dạng địa chỉ Email");
            return;
        }
        if (Patterns.PHONE.matcher(phone).matches()) {
            if (phone.length() < 10 || phone.length() > 11) {
                view.onError("Bạn phải nhập đúng định dạng số điện thoại");
                return;
            }
        }

        view.showProgress();
        interactor.doSignUp(phone);

    }



    @Override
    public void signUpSuccess(String code) {
        if (view == null) return;
        view.hiddenProgress();
        view.signUpSuccess(code);
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
