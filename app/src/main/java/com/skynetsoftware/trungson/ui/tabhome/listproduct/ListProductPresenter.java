package com.skynetsoftware.trungson.ui.tabhome.listproduct;

import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BasePresenter;

import java.util.List;

public class ListProductPresenter implements ListProductContract.Presenter {
    ListProductContract.View view;
    ListProductContract.Interactor interactor;

    public ListProductPresenter(ListProductContract.View view) {
        this.view = view;
        interactor = new ListProductRemoteImpl(this);
    }

    @Override
    public void getListProduct() {
        view.showProgress();
        interactor.getListProduct();
    }

    @Override
    public void onDestroyView() {
        view = null ;
    }

    @Override
    public void onSuccessGetListProduct(List<Product> list) {
        if(view ==null ) return;
        view.hiddenProgress();
        if(list!=null)
        view.onSuccessGetListProduct(list);
    }

    @Override
    public void onErrorApi(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onError(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
