package com.skynetsoftware.trungson.ui.checkin;

import com.skynetsoftware.trungson.models.Place;

public class CheckinPresenter implements CheckinContract.Presenter {
    public static final int TYPE_CHECKIN = 1;
    public static final int TYPE_CHECKOUT = 2;
    CheckinContract.View view;
    CheckinContract.Interactor interactor;

    public CheckinPresenter(CheckinContract.View view) {
        this.view = view;
        interactor = new CheckinRemoteImpl(this);
    }

    @Override
    public void checkinHere(Place place) {
        if (view == null) return;
        view.showProgress();
        interactor.checkinHere(place.getAddress(), place.getLatLng().latitude, place.getLatLng().longitude, TYPE_CHECKIN);
    }

    @Override
    public void checkOutHere(Place place) {
        if (view == null) return;
        view.showProgress();
        interactor.checkinHere(place.getAddress(), place.getLatLng().latitude, place.getLatLng().longitude, TYPE_CHECKOUT);
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onSuccessCheckin(int type) {
        if (view == null) return;
        view.hiddenProgress();
        if (type == TYPE_CHECKIN)
            view.onSuccessCheckin();
        else
            view.onSuccessCheckOut();
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
