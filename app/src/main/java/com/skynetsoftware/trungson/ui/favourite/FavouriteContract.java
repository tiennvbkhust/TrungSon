package com.skynetsoftware.trungson.ui.favourite;



import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface FavouriteContract {
    interface View extends BaseView {
        void onGetFavouritesSuccess(List<Product> list);
        void onSuccessToggleFavourite();

    }
    interface Presenter extends BasePresenter,SearchListener{
        void getFavourites();
        void toggleFavourite(String idShop, boolean value);

    }
    interface Interactor {

        void getFavourites();
        void doToggleFavourite(String idShop, int type);


    }
    interface  SearchListener extends OnFinishListener {
        void onGetFavouritesSuccess(List<Product> list);
        void onSuccessToggleFavourite();

    }
}
