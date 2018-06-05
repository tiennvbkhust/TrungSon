package com.skynetsoftware.trungson.ui.signup;

import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface SignUpContract {
    interface View extends BaseView {
        void signUpSuccess(String code);
    }

    interface Presenter extends BasePresenter {
        void signUp(String fullname, String email, String phone);
        void signUpSuccess(String code);
    }

    interface Interactor {
        void doSignUp(String phone);
    }


}
