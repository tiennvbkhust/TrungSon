package com.skynetsoftware.trungson.ui.tabproduct.product;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Filter;
import com.skynetsoftware.trungson.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductPresenter implements ProductContract.Presenter {
    ProductContract.View view;
    ProductContract.Interactor interactor;

    public ProductPresenter(ProductContract.View view) {
        this.view = view;
        interactor = new ProductRemoteImpl(this);
    }

    @Override
    public void getListProducts(String idCategory) {
        if(view == null) return;
        view.showProgress();
        Filter filter = AppController.getInstance().getmFilter();
        if(filter!=null){
            interactor.getListProducts(idCategory,filter.getMinPrice(),filter.getMaxPrice(),AppController.getInstance().getTypeSort());
        }else{
            interactor.getListProducts(idCategory,0,10000000,1);
        }
    }

    @Override
    public void onDestroyView() {
        view = null;

    }

    @Override
    public void onSuccessGetListProducts(List<Product> list) {
        if(view == null  )return;
        view.hiddenProgress();
        if(list!=null)
        view.onSuccessGetListProducts(list);
        else
            view.onSuccessGetListProducts(new ArrayList<Product>());
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

    }
}
