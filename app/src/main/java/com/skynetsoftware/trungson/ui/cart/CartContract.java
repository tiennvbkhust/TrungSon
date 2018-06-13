package com.skynetsoftware.trungson.ui.cart;

import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface CartContract {
    interface View extends BaseView {
        void onSuccessUpdateInfor();

    }

    interface Presenter extends BasePresenter,CartListener {
        void updateInfor(String name,String address,String city,String phone, String note,String promo);

    }
    interface Interactor {
        void updateInfor(String name,String address,String city,String phone, String note,String promo);

    }
    interface CartListener extends OnFinishListener{
        void onSuccessUpdateInfor();

    }
}
