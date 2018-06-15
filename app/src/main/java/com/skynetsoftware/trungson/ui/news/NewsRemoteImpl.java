package com.skynetsoftware.trungson.ui.news;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.News;
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

public class NewsRemoteImpl extends Interactor implements NewsContract.Interactor {
    NewsContract.HistoryListener listener;

    public NewsRemoteImpl(NewsContract.HistoryListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void getListHistories() {

        getmService().getNews().enqueue(new CallBackBase<ApiResponse<List<News>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<News>>> call, Response<ApiResponse<List<News>>> response) {
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
            public void onRequestFailure(Call<ApiResponse<List<News>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
