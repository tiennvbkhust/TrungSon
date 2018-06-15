package com.skynetsoftware.trungson.ui.changepassword;


import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface ChangePasswordContract  {
    interface  View extends BaseView {
        void onSuccessUpdate();

    }
    interface  Presenter extends BasePresenter,UpdateInforListenr{
        void update(String textOld, String text);
    }
    interface Interactor {
        void doUpdate(String textOld, String text);
    }
    interface UpdateInforListenr extends OnFinishListener {
        void onSuccessUpdate();
    }
}
