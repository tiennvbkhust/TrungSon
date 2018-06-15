package com.skynetsoftware.trungson.ui.detailbooking;

import com.google.android.gms.common.api.Api;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class DetailBookingRemoteImpl extends Interactor implements DetailBookingContract.Interactor {
    DetailBookingContract.DetailBookingListener listener;

    public DetailBookingRemoteImpl(DetailBookingContract.DetailBookingListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {

        return ApiUtil.createNotTokenApi();
    }
    @Override
    public void getInforCard() {
        String num = AppController.getInstance().getmSetting().getString("card_num");
        String name = AppController.getInstance().getmSetting().getString("card_name");
        String date = AppController.getInstance().getmSetting().getString("card_date");
        String cvv = AppController.getInstance().getmSetting().getString("card_cvv");
        listener.onSuccessGetInforCard(num, name, date, cvv);
    }
    @Override
    public void getDetailBooking(String id) {
        getmService().getDetailBooking(id).enqueue(new CallBackBase<ApiResponse<Booking>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Booking>> call, Response<ApiResponse<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetDetailBooking(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Booking>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
