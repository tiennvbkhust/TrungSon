package com.skynetsoftware.trungson.ui.DetailNotificationActivity;


import com.skynetsoftware.trungson.models.Notification;

public class DetailNotificationPresenter implements DetailNotificationContract.Presenter {
    DetailNotificationContract.View view;
    DetailNotificationContract.Interactor interactor;

    public DetailNotificationPresenter(DetailNotificationContract.View view) {
        this.view = view;
        interactor = new DetailNotificationRemoteImpl(this);
    }

    @Override
    public void getDetail(String id) {
        view.showProgress();
        interactor.doGetDetail(id);
    }

    @Override
    public void onSuccessGetDetail(Notification notification) {
        if (view == null) return;
        view.hiddenProgress();
        view.onSuccessGetDetail(notification);
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
        view.onErrorApi(message);
    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
