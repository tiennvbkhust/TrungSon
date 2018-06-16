package com.skynetsoftware.trungson.ui.checkin;

import com.skynetsoftware.trungson.models.Place;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface CheckinContract  {
    interface View extends BaseView{
        void onSuccessCheckin();
        void onSuccessCheckOut();

    }
    interface Presenter extends BasePresenter , CheckinListener{
        void checkinHere(Place place);
        void checkOutHere(Place place);
    }

    interface Interactor {
        void checkinHere(String address,double lat,double lng,int typeChecinout);
    }

    interface CheckinListener extends OnFinishListener{
        void onSuccessCheckin(int typeCheckinOut);
    }
}
