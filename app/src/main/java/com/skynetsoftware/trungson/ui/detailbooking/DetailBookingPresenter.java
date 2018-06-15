package com.skynetsoftware.trungson.ui.detailbooking;

import com.skynetsoftware.trungson.models.Booking;

public class DetailBookingPresenter implements DetailBookingContract.Presenter {
    DetailBookingContract.View view;
    DetailBookingContract.Interactor interactor;

    public DetailBookingPresenter(DetailBookingContract.View view) {
        this.view = view;
        interactor = new DetailBookingRemoteImpl(this);
    }

    @Override
    public void getDetailBooking(String id) {
        view.showProgress();
        interactor.getDetailBooking(id);
    }

    @Override
    public void onDestroyView() {
        view = null;

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

    }

    @Override
    public void getInforCard() {
        interactor.getInforCard();
    }

    @Override
    public void onSuccessGetDetailBooking(Booking booking) {
        if(view == null) return;
        view.hiddenProgress();
        view.onSuccessGetDetailBooking(booking);
    }

    @Override
    public void onSuccessGetInforCard(String number, String name, String date, String cvv) {
        if (view == null) return;
        view.onSuccessGetInforCard(number, name, date, cvv);
    }

}
