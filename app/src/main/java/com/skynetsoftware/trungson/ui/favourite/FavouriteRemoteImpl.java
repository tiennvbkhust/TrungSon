package com.skynetsoftware.trungson.ui.favourite;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FavouriteRemoteImpl extends Interactor implements FavouriteContract.Interactor {
    FavouriteContract.SearchListener listener;

    public FavouriteRemoteImpl(FavouriteContract.SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getFavourites() {
        Profile profilee = AppController.getInstance().getmProfileUser();
        if (profilee == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getListFavouritesShop( profilee.getId()).enqueue(new CallBackBase<ApiResponse<List<Product>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onGetFavouritesSuccess(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });
    }

    @Override
    public void doToggleFavourite(String idShop, int type) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().toggleFavourite(profile.getId(), idShop, type).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }
}
