package com.skynetsoftware.trungson.ui.tabproduct;

import com.skynetsoftware.trungson.models.Category;

import java.util.List;

public class ListProductsPresenter implements ListProductsContract.Presenter {
    ListProductsContract.View view;
    ListProductsContract.Interactor interactor;

    public ListProductsPresenter(ListProductsContract.View view) {
        this.view = view;
        interactor = new ListProductsRemoteImpl(this);
    }

    @Override
    public void getListCategories() {
        view.showProgress();
        interactor.getListCategories();
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onSuccessGetListCategories(List<Category> list) {
        if(view == null )return;
        view.hiddenProgress();
        if(list != null)
            view.onSuccessGetListCategories(list);
    }

    @Override
    public void onErrorApi(String message) {
        if(view == null )return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if(view == null )return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onErrorAuthorization() {
        if(view == null )return;
        view.hiddenProgress();
    }
}
