package com.skynetsoftware.trungson.ui.productOfAgency;

import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListProductsRemoteImpl extends Interactor implements ListProductsContract.Interactor {
    ListProductsContract.ListProductsListener listener;

    public ListProductsRemoteImpl(ListProductsContract.ListProductsListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListCategories() {
        getmService().getListCategoy().enqueue(new CallBackBase<ApiResponse<List<Category>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetListCategories(response.body().getData());
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
