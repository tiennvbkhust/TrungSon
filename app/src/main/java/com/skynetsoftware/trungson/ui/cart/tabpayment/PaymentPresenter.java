package com.skynetsoftware.trungson.ui.cart.tabpayment;

import com.skynetsoftware.trungson.application.AppController;

public class PaymentPresenter implements PaymentContract.Presenter {
    PaymentContract.View view;
    PaymentContract.Interactor interactor;

    public PaymentPresenter(PaymentContract.View view) {
        this.view = view;
        this.interactor = new PaymentRemoteImpl(this);
    }

    @Override
    public void getInforCard() {
        interactor.getInforCard();
    }

    @Override
    public void saveInfoCard(String date, String number, String name, String cvv) {
        interactor.saveInfoCard(date, number, name, cvv);
    }

    @Override
    public void paidCart(int typePayment, double price) {
        view.showProgress();
        interactor.paidCart(typePayment, price);
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) {
            return;
        }
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if (view == null) {
            return;
        }
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onSuccessGetInforCard(String number, String name, String date, String cvv) {
        if (view == null) return;
        view.onSuccessGetInforCard(number, name, date, cvv);
    }

    @Override
    public void onSuccessPaid(String booking) {
        if (view == null) return;
        view.hiddenProgress();
        AppController.getInstance().getListProducts().clear();
        view.onSuccessPaid(booking);
    }
}
