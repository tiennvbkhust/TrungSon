package com.skynetsoftware.trungson.ui.cart.tabpayment;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface PaymentContract {
    interface View extends BaseView {
        void onSuccessGetInforCard(String number,String name,String date,String cvv);
        void onSuccessPaid(String booking);

    }

    interface Presenter extends BasePresenter ,PaymentListener{
       void getInforCard();
       void saveInfoCard(String date,String number,String name,String cvv);
       void paidCart(int typePayment,double price);
    }

    interface Interactor {
        void getInforCard();
        void saveInfoCard(String date,String number,String name,String cvv);
        void paidCart(int typePayment,double price);


    }

    interface PaymentListener extends OnFinishListener {
        void onSuccessGetInforCard(String number,String name,String date,String cvv);
        void onSuccessPaid(String booking);
    }
}
