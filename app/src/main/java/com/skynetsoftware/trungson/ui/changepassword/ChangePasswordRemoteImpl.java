package com.skynetsoftware.trungson.ui.changepassword;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ChangePasswordRemoteImpl extends Interactor implements ChangePasswordContract.Interactor {
    ChangePasswordContract.UpdateInforListenr listenr;

    public ChangePasswordRemoteImpl(ChangePasswordContract.UpdateInforListenr listenr) {
        this.listenr = listenr;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void doUpdate(String newPass, String oldPass) {
       Profile profile =  AppController.getInstance().getmProfileUser();

        if(profile==null) {
            listenr.onErrorAuthorization();
            return;
        }

        getmService().changePassword(newPass,oldPass,profile.getId(),2).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        listenr.onSuccessUpdate();
                    } else {
                        listenr.onError(response.body().getMessage());
                    }
                } else {
                    listenr.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listenr.onErrorApi(t.getMessage());

            }
        });
    }
}
