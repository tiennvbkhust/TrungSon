package com.skynetsoftware.trungson.ui.cart;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;

public class CartRemoteImpl extends Interactor implements CartContract.Interactor {
    CartContract.CartListener listener;

    public CartRemoteImpl(CartContract.CartListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void updateInfor(String name, String address, String city, String phone, String note, String promo) {
        AppController.getInstance().getmSetting().put("name",name);
        AppController.getInstance().getmSetting().put("address",address);
        AppController.getInstance().getmSetting().put("city",city);
        AppController.getInstance().getmSetting().put("phone",phone);
        AppController.getInstance().getmSetting().put("note",note);
        listener.onSuccessUpdateInfor();
    }
}
