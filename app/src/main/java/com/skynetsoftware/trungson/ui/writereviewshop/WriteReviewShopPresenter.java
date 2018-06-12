package com.skynetsoftware.trungson.ui.writereviewshop;

public class WriteReviewShopPresenter implements WriteReviewShopContract.Presenter {

    WriteReviewShopContract.View view;
    WriteReviewShopContract.Interactor interactor;


    public WriteReviewShopPresenter(WriteReviewShopContract.View view) {
        this.view = view;
        interactor = new WriteReviewShopRemoteImpl(this);
    }


    @Override
    public void onDestroyView() {
        view = null;
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
    public void writeReview(String idShop,  double star) {

        view.showProgress();
        interactor.writeReview(idShop,star);
    }

    @Override
    public void onSuccessReviews() {
        if (view == null) return;
        view.hiddenProgress();
        view.onSuccessReviews();
    }
}
