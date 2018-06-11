package com.skynetsoftware.trungson.ui.detailproduct;

import com.skynetsoftware.trungson.models.Product;

public class DetailProductPresenter implements DetailProductContract.Presenter {
    DetailProductContract.View view;
    DetailProductContract.Interactor interactor;

    public DetailProductPresenter(DetailProductContract.View view) {
        this.view = view;
        interactor = new DetailProductRemoteImpl(this);
    }

    @Override
    public void getDetail(String idProduct) {
        view.showProgress();
        interactor.getDetail(idProduct);
    }

    @Override
    public void toggleFav(String id, int type) {
        interactor.toggleFav(id,type);

    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onSuccessGetDetail(Product product) {
        if(view == null) return;
        view.hiddenProgress();
        if(product != null){
            view.onSuccessGetDetail(product);
        }else{
            view.onError("Không tìm thấy sản phẩm");
        }
    }

    @Override
    public void onErrorApi(String message) {
        if(view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if(view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if(view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
