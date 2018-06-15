package com.skynetsoftware.trungson.ui.main;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainRemoteImpl extends Interactor implements MainContract.Interactor {
    MainContract.Presenter listener;

    public MainRemoteImpl(MainContract.Presenter presenter) {
        this.listener = presenter;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }




    @Override
    public void getInfor() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if(profile == null ){
            listener.onErrorAuthorization();
            return;
        }
        getmService().getProfile(profile.getId(),AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        AppController.getInstance().setmProfileUser(response.body().getData());
                        listener.getInforSuccess();
                    } else {
                        listener.onErrorAuthorization();

                    }
                } else {
                    listener.onErrorAuthorization();
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                listener.onErrorAuthorization();


            }
        });
    }
}
