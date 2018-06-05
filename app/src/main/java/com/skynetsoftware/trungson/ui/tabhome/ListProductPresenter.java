package com.skynetsoftware.trungson.ui.tabhome;

import com.skynetsoftware.trungson.models.Shop;

import java.util.List;

public class ListProductPresenter implements ListProductContract.Presenter {
    ListProductContract.Interactor interactor;
    ListProductContract.View view;


    public ListProductPresenter(ListProductContract.View view) {
        this.view = view;
        interactor = new ListProductRemoteImpl(this);
    }

    @Override
    public void getListShop() {
        view.showProgress();
        interactor.getListShop();
    }

    @Override
    public void onDestroyView() {
        view = null;
    }


    @Override
    public void onSuccessGetListShop(List<Shop> listShop) {
        if (view == null) return;
        view.hiddenProgress();
        if (listShop != null)
            view.onSuccessGetList(listShop);
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
        view.onErrorAuthorization();
    }
}
