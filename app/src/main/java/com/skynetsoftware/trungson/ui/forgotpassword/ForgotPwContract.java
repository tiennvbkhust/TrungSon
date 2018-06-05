package com.skynetsoftware.trungson.ui.forgotpassword;


import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;

public interface ForgotPwContract {
    interface View extends BaseView {
        void signUpSuccess();
    }

    interface Presenter extends BasePresenter {
        void signUp(String email);
        void signUpSuccess();
    }

    interface Interactor {
        void doSignUp(String email, int type);
    }
}
