package com.skynetsoftware.trungson.ui.verifyaccount;

import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface VerifyAccountContract {

    interface View extends BaseView {
        void onSuccessSignUp();

        void onSuccessSendCode(String code);

    }

    interface Presenter extends BasePresenter, VerifyListener {
        void signUp(String name, String email, String phone, String password);

        void sendCode(String phone);

    }

    interface Interactor {
        void signUp(String name, String email, String phone, String password);

        void sendCodeTo(String phone);

    }

    interface VerifyListener extends OnFinishListener {
        void onSuccessSignUp(Profile profile);

        void onSuccessSendCode(String code);
    }

}
