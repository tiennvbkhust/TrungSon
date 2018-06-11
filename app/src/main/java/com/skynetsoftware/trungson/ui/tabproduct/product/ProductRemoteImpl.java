package com.skynetsoftware.trungson.ui.tabproduct.product;

import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductRemoteImpl extends Interactor implements ProductContract.Interactor {
    ProductContract.ProductListener listener;

    public ProductRemoteImpl(ProductContract.ProductListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListProducts(String idCategory, double minPrice, double maxPrice, int typeSort) {
        getmService().getListProductWithFilter(idCategory,String.valueOf(maxPrice),String.valueOf(minPrice),typeSort).enqueue(new CallBackBase<ApiResponse<List<Product>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetListProducts(response.body().getData());
                    } else {
                        listener.onError(response.message());
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
}
