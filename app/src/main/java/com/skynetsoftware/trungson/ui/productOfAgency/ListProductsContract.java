package com.skynetsoftware.trungson.ui.productOfAgency;

import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductsContract  {
    interface View extends BaseView{
        void onSuccessGetListCategories(List<Category> list);

    }

    interface Presenter extends BasePresenter,ListProductsListener{
       void getListCategories();
    }

    interface Interactor {
        void getListCategories();

    }

    interface ListProductsListener extends OnFinishListener{
        void onSuccessGetListCategories(List<Category> list);

    }
}
