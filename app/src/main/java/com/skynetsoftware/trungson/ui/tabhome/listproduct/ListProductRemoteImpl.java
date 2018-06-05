package com.skynetsoftware.trungson.ui.tabhome.listproduct;

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

public class ListProductRemoteImpl extends Interactor implements ListProductContract.Interactor {
    ListProductContract.ListProductionListener listener;

    public ListProductRemoteImpl(ListProductContract.ListProductionListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListProduct() {
        getmService().getListProduct().enqueue(new CallBackBase<ApiResponse<List<Product>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    if(response.body().getCode() == AppConstant.CODE_API_SUCCESS){
                        listener.onSuccessGetListProduct(response.body().getData());
                    }else{
                        listener.onError(response.body().getMessage());
                    }
                }else{
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
