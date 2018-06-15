package com.skynetsoftware.trungson.ui.favourite;


import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouritePresenter implements FavouriteContract.Presenter {
    FavouriteContract.View view;
    FavouriteContract.Interactor interactor;

    public FavouritePresenter(FavouriteContract.View view) {
        this.view = view;
        interactor = new FavouriteRemoteImpl(this);
    }

    @Override

    public void getFavourites() {

        view.showProgress();
        interactor.getFavourites();
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void toggleFavourite(String idShop, boolean value) {
        interactor.doToggleFavourite(idShop, value ? 1 : 2);
    }


    @Override
    public void onSuccessToggleFavourite() {
        if (view == null) return;
        view.hiddenProgress();
        view.onSuccessToggleFavourite();
    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);

    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
    }

    @Override
    public void onGetFavouritesSuccess(List<Product> list) {
        if (view == null) return;
        view.hiddenProgress();

        if(list != null)
        view.onGetFavouritesSuccess(list);

    }
}
