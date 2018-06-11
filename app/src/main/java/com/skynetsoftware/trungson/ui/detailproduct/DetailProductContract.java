package com.skynetsoftware.trungson.ui.detailproduct;

import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

public interface DetailProductContract  {
    interface View extends BaseView{
        void onSuccessGetDetail(Product product);

    }
    interface Presenter extends BasePresenter,DetailProductListener{
        void getDetail(String idProduct);
        void toggleFav(final String id, int type);
    }
    interface Interactor{
        void getDetail(String idProduct);
        void toggleFav(final String id, int type);
    }
    interface DetailProductListener extends OnFinishListener{
        void onSuccessGetDetail(Product product);
    }
}
