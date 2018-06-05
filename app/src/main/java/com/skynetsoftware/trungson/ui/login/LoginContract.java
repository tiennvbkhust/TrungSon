package com.skynetsoftware.trungson.ui.login;


import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;
import com.sromku.simple.fb.SimpleFacebook;

public interface LoginContract  {
    interface View extends BaseView {
        void onSuccessLogin(Profile profile);
        void onSuccesLoginFacebook(Profile profile);

    }

    interface Presenter extends BasePresenter,OnFinishListener {
        void login(String username, String password);
        void onSuccessLogin(Profile profile);
        void loginViaFacebook(SimpleFacebook facebook);
        void loginViaGoogle(String idGG,String email,String name);
    }

    interface Interactor {
        void doLogin(String username, String password, int type);
        void doLoginGGG(String idGG,String email,String name);
        void doLoginFB(SimpleFacebook facebook);
        void loginApi(String username,String email,String fbID,int type);

    }
}
