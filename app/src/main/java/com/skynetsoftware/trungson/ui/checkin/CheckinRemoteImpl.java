package com.skynetsoftware.trungson.ui.checkin;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class CheckinRemoteImpl extends Interactor implements CheckinContract.Interactor {
    private CheckinContract.CheckinListener listener;

    public CheckinRemoteImpl(CheckinContract.CheckinListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void checkinHere(String address, double lat, double lng, final int typeChecinout) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().checkIn(profile.getId(), address, lat, lng, typeChecinout).enqueue(new CallBackBase<ApiResponse<Message>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessCheckin(typeChecinout);
                    } else {
                        listener.onError(response.body().getMessage());

                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Message>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
