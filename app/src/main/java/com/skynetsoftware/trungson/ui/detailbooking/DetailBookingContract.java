package com.skynetsoftware.trungson.ui.detailbooking;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface DetailBookingContract  {
    interface View extends BaseView{
        void onSuccessGetInforCard(String number,String name,String date,String cvv);

        void onSuccessGetDetailBooking(Booking booking);
    }

    interface Presenter extends BasePresenter,DetailBookingListener{
        void getDetailBooking(String id);
        void getInforCard();

    }

    interface Interactor  {
        void getDetailBooking(String id);
        void getInforCard();


    }

    interface DetailBookingListener extends OnFinishListener{
        void onSuccessGetDetailBooking(Booking booking);
        void onSuccessGetInforCard(String number,String name,String date,String cvv);

    }
}
