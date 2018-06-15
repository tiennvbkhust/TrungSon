package com.skynetsoftware.trungson.ui.news;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.News;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface NewsContract  {
    interface View extends BaseView{
        void onSucessGetListHistories(List<News> list);

    }
    interface Presenter extends BasePresenter,HistoryListener{
        void getListHistories();

    }

    interface Interactor {
        void getListHistories();

    }
    interface HistoryListener extends OnFinishListener {
        void onSucessGetListHistories(List<News> list);
    }
}
