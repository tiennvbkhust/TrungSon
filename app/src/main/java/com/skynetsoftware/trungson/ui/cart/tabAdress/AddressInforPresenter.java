package com.skynetsoftware.trungson.ui.cart.tabAdress;

public class AddressInforPresenter implements AddressInforContract.Presenter {

    private AddressInforContract.View view;
    private AddressInforContract.Interactor interactor;

    public AddressInforPresenter(AddressInforContract.View view) {
        this.view = view;
        interactor = new AddressInforRemoteImpl(this);
    }

    @Override
    public void getInfor() {
        view.showProgress();
        interactor.getInfor();
    }

    @Override
    public void updateInfor(String name, String address, String city, String phone, String note, String promo, String avatar) {
        view.showProgress();
        interactor.updateInfor( name,  address,  city,  phone,  note,  promo,  avatar);

    }

    @Override
    public void onDestroyView() {
        view =  null;

    }

    @Override
    public void onSuccessGetInfor(String name, String address, String city, String phone, String note, String promo, String avatar) {
        if(view== null) return;
        view.hiddenProgress();
        view.onSuccessGetInfor(name,address,city,phone,note,promo,avatar);
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
