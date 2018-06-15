package com.skynetsoftware.trungson.ui.main;


import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.io.File;
import java.util.List;

public interface MainContract {
    interface View extends BaseView {
        void getInforSuccess();
    }

    interface Presenter extends BasePresenter, OnHomeRequestFinish {


        void getInfor();
    }

    interface Interactor {


        void getInfor();

    }

    interface OnHomeRequestFinish extends OnFinishListener {


        void getInforSuccess();
    }
}
