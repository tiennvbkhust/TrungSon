package com.skynetsoftware.trungson.ui.login;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.sromku.simple.fb.SimpleFacebook;

public class LoginPresenter implements LoginContract.Presenter, OnFinishListener {
    LoginContract.View view;
    LoginContract.Interactor interactor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.interactor = new LoginRemoteImpl(this);
    }

    @Override
    public void login(String username, String password) {
        if (username.isEmpty()) {
            view.onError("Vui lòng nhập email hoặc số điện thoại");
            return;
        }
        if (password.isEmpty()) {
            view.onError("Vui lòng nhập Password");
            return;
        }

        view.showProgress();
        interactor.doLogin(username, password, AppConstant.TYPE_USER);
    }

    @Override
    public void onSuccessLogin(Profile profile) {
        if(view == null) return;
        view.hiddenProgress();
        AppController.getInstance().setmProfileUser(profile);
        view.onSuccesLoginFacebook(profile);
    }


    @Override
    public void loginViaFacebook(SimpleFacebook fb) {

        view.showProgress();
        interactor.doLoginFB(fb);
    }

    @Override
    public void loginViaGoogle(String idGG, String email, String name) {
        view.showProgress();
        interactor.doLoginGGG(idGG,name,email);
    }

    @Override
    public void onDestroyView() {
        view = null;
    }


    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
