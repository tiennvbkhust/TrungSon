package com.skynetsoftware.trungson.ui.updateInfor;

import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface UpdateInforContract  {
    interface  View extends BaseView {
        void onSuccessUpdate();

    }
    interface  Presenter extends BasePresenter,UpdateInforListenr{
        void update(String type, String text);
    }
    interface Interactor {
        void doUpdate(String type, String text);
    }
    interface UpdateInforListenr extends OnFinishListener {
        void onSuccessUpdate();
    }
}
