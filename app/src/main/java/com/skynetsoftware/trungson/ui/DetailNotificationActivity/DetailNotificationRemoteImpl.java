package com.skynetsoftware.trungson.ui.DetailNotificationActivity;



import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Notification;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class DetailNotificationRemoteImpl extends Interactor implements DetailNotificationContract.Interactor {
    DetailNotificationContract.OnFinishDetailNotificationListener listener;

    public DetailNotificationRemoteImpl(DetailNotificationContract.OnFinishDetailNotificationListener listener) {
        this.listener = listener;
    }

    @Override
    public void doGetDetail(String id) {
        if(AppController.getInstance().getmProfileUser() == null ){
            listener.onErrorAuthorization();
            return;
        }

        getmService().getDetailNotification(id, AppConstant.TYPE_USER,AppController.getInstance().getmProfileUser().getId()).enqueue(new CallBackBase<ApiResponse<Notification>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Notification>> call, Response<ApiResponse<Notification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {

                        listener.onSuccessGetDetail(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Notification>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }
}
