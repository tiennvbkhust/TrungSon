package com.skynetsoftware.trungson.ui.writereviewshop;


import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface WriteReviewShopContract {
    interface View extends BaseView {
        void onSuccessReviews();

    }

    interface Presenter extends BasePresenter,ReviewShopListener{
        void writeReview(String idShop,  double star);
    }

    interface Interactor {
        void writeReview(String idShop, double star);


    }

    interface ReviewShopListener extends OnFinishListener {
        void onSuccessReviews();
    }

}
