package com.skynetsoftware.trungson.ui.cart.tabAdress;

import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface AddressInforContract  {
    interface View extends BaseView{
        void onSuccessGetInfor(String name,String address,String city,String phone, String note,String promo,String avatar);
        void onSuccessUpdateInfor();
    }
    interface Presenter extends BasePresenter,AddressInforListener{
        void getInfor();
        void updateInfor(String name,String address,String city,String phone, String note,String promo,String avatar);
    }

    interface Interactor {
        void getInfor();
        void updateInfor(String name,String address,String city,String phone, String note,String promo,String avatar);
    }
    interface AddressInforListener extends OnFinishListener{
        void onSuccessGetInfor(String name,String address,String city,String phone, String note,String promo,String avatar);
        void onSuccessUpdateInfor();
    }
}
