package com.skynetsoftware.trungson.ui.notification;

import com.skynetsoftware.trungson.models.Notification;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationRemoteImpl extends Interactor implements NotificationContract.Interactor {
    NotificationContract.Presenter presenter;

    public NotificationRemoteImpl(NotificationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void doGetAllService(String idShop) {
        getmService().getListNotification(idShop, AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<List<Notification>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Notification>>> call, Response<ApiResponse<List<Notification>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {

                        presenter.onSuccessGetServices(response.body().getData());
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Notification>>> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }
}
