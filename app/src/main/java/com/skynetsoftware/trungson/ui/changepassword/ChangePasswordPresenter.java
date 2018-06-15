package com.skynetsoftware.trungson.ui.changepassword;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    ChangePasswordContract.View view;
    ChangePasswordContract.Interactor interactor;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        this.view = view;
        interactor = new ChangePasswordRemoteImpl(this);
    }

    @Override
    public void update(String newpass, String oldpass) {
        if(oldpass == null || newpass == null ||oldpass.isEmpty() || newpass.isEmpty()){
            onError("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        view.showProgress();
        interactor.doUpdate(newpass,oldpass);
    }

    @Override
    public void onDestroyView() {
        view = null ;
    }

    @Override
    public void onSuccessUpdate() {
        if(view == null) return;
        view.hiddenProgress();
        view.onSuccessUpdate();
    }

    @Override
    public void onErrorApi(String message) {
        if(view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if(view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if(view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
