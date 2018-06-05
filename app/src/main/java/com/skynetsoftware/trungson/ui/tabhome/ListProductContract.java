package com.skynetsoftware.trungson.ui.tabhome;

import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract   {
    interface View extends BaseView{
        void onSuccessGetList(List<Shop> listShop);

    }
    interface Presenter extends BasePresenter,ListProductListener{
       void getListShop();
    }
    interface Interactor {
        void getListShop();
    }
    interface ListProductListener extends OnFinishListener{
        void onSuccessGetListShop(List<Shop> listShop);
    }
}
