package com.skynetsoftware.trungson.ui.updateInfor;

public class UpdateInforPresenter implements UpdateInforContract.Presenter {
    UpdateInforContract.View view;
    UpdateInforContract.Interactor interactor;

    public UpdateInforPresenter(UpdateInforContract.View view) {
        this.view = view;
        interactor = new UpdateInforRemoteImpl(this);
    }

    @Override
    public void update(String type, String text) {
        if(type == null || text == null ||type.isEmpty() || text.isEmpty()){
            onError("Đã có lỗi xảy ra.");
            return;
        }
        view.showProgress();
        interactor.doUpdate(type,text);
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
