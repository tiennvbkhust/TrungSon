package com.skynetsoftware.trungson.ui.tabproduct.product;

import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ProductContract {
    interface View extends BaseView {
        void onSuccessGetListProducts(List<Product> list);
    }

    interface Presenter extends BasePresenter,ProductListener {
       void getListProducts(String idCategory);
    }

    interface Interactor {
        void getListProducts(String idCategory,double minPrice,double maxPrice, int typeSort);

    }

    interface ProductListener extends OnFinishListener{
        void onSuccessGetListProducts(List<Product> list);
    }
}
