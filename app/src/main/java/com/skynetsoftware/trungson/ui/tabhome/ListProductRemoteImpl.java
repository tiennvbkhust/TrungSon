package com.skynetsoftware.trungson.ui.tabhome;

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

public class ListProductRemoteImpl extends Interactor implements ListProductContract.Interactor {
    ListProductContract.ListProductListener listener;

    public ListProductRemoteImpl(ListProductContract.ListProductListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getListShop() {
        getmService().getListShop().enqueue(new CallBackBase<ApiResponse<List<Shop>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Shop>>> call, Response<ApiResponse<List<Shop>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS)
                        listener.onSuccessGetListShop(response.body().getData());
                    else
                        listener.onError(response.body().getMessage());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Shop>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
