package com.skynetsoftware.trungson.ui.history;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryRemoteImpl extends Interactor implements HistoryContract.Interactor {
    HistoryContract.HistoryListener listener;

    public HistoryRemoteImpl(HistoryContract.HistoryListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void getListHistories() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if(profile == null){
            listener.onErrorAuthorization();
            return;
        }
        getmService().getHistories(profile.getId()).enqueue(new CallBackBase<ApiResponse<List<Booking>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Booking>>> call, Response<ApiResponse<List<Booking>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetListHistories(response.body().getData());
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Booking>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
