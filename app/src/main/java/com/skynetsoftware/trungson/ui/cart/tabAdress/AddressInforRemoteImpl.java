package com.skynetsoftware.trungson.ui.cart.tabAdress;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;

public class AddressInforRemoteImpl extends Interactor implements AddressInforContract.Interactor {
    AddressInforContract.AddressInforListener listener;

    public AddressInforRemoteImpl(AddressInforContract.AddressInforListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return null;
    }

    @Override
    public void getInfor() {
        if (AppController.getInstance().getmProfileUser() == null) {
            listener.onErrorAuthorization();
            return;
        }
        String avatar = AppController.getInstance().getmProfileUser().getAvatar();
        String name = AppController.getInstance().getmSetting().getString("name");
        if (name == null) name = AppController.getInstance().getmProfileUser().getName();
        String address = AppController.getInstance().getmSetting().getString("address");
        if (address == null) address = AppController.getInstance().getmProfileUser().getAddress();

        String city = AppController.getInstance().getmSetting().getString("city");
        String phone = AppController.getInstance().getmSetting().getString("phone");
        if (phone == null) phone = AppController.getInstance().getmProfileUser().getPhone();

        String note = AppController.getInstance().getmSetting().getString("note");

        listener.onSuccessGetInfor(name, address, city, phone, note, "", avatar);


    }

    @Override
    public void updateInfor(String name, String address, String city, String phone, String note, String promo, String avatar) {
        AppController.getInstance().getmSetting().put("name", name);
        AppController.getInstance().getmSetting().put("address", address);
        AppController.getInstance().getmSetting().put("city", city);
        AppController.getInstance().getmSetting().put("phone", phone);
        AppController.getInstance().getmSetting().put("note", note);
        AppController.getInstance().getmSetting().put("promo", promo);
    }
}
