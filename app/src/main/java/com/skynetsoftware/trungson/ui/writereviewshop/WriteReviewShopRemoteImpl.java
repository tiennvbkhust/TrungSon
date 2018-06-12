package com.skynetsoftware.trungson.ui.writereviewshop;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class WriteReviewShopRemoteImpl extends Interactor implements WriteReviewShopContract.Interactor {
    WriteReviewShopContract.ReviewShopListener listener;

    public WriteReviewShopRemoteImpl(WriteReviewShopContract.ReviewShopListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void writeReview(String idShop, double star) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if(profile == null){
            listener.onErrorAuthorization();
            return;
        }
        getmService().writeReview(idShop,star).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessReviews();
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
