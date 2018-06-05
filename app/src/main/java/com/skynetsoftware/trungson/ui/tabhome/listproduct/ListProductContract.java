package com.skynetsoftware.trungson.ui.tabhome.listproduct;

import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView{
        void onSuccessGetListProduct(List<Product> list);

    }
    interface Presenter extends BasePresenter,ListProductionListener{
       void getListProduct();
    }

    interface Interactor {
        void getListProduct();

    }
    interface ListProductionListener extends OnFinishListener{
        void onSuccessGetListProduct(List<Product> list);
    }
}
