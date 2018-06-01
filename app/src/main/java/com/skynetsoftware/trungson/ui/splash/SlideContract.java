package com.skynetsoftware.trungson.ui.splash;


import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface SlideContract  {
    interface View extends BaseView {
        void onSuccessGetInfor();

    }
    interface Presenter extends BasePresenter,OnFinishListener {
       void getInfor();
       void getInforSuccess(Profile profile);
       void notFoundInfor();
    }

    interface Interactor {
        void doGetInfor(String profileInfor);
    }
}
