package com.skynetsoftware.trungson.ui.cart;

public class CartPresenter implements CartContract.Presenter {

    CartContract.View view;
    CartContract.Interactor interactor;

    public CartPresenter(CartContract.View view) {
        this.view = view;
        interactor = new CartRemoteImpl(this);
    }

    @Override
    public void updateInfor(String name, String address, String city, String phone, String note, String promo) {
        view.showProgress();
        interactor.updateInfor( name,  address,  city,  phone,  note,  promo );
    }
    @Override
    public void onDestroyView() {
        view =  null;

    }

    @Override
    public void onSuccessUpdateInfor() {
        if(view== null) return;
        view.hiddenProgress();
        view.onSuccessUpdateInfor();
    }

    @Override
    public void onErrorApi(String message) {
        if(view== null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if(view== null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if(view== null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
