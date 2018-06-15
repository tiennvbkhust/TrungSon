package com.skynetsoftware.trungson.ui.history;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface HistoryContract  {
    interface View extends BaseView{
        void onSucessGetListHistories(List<Booking> list);

    }
    interface Presenter extends BasePresenter,HistoryListener{
        void getListHistories ();

    }

    interface Interactor {
        void getListHistories ();

    }
    interface HistoryListener extends OnFinishListener {
        void onSucessGetListHistories(List<Booking> list);
    }
}
