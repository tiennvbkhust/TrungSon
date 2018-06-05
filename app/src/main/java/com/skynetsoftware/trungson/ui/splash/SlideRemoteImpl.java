package com.skynetsoftware.trungson.ui.splash;

import com.google.gson.Gson;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;


import retrofit2.Call;
import retrofit2.Response;

public class SlideRemoteImpl extends Interactor implements SlideContract.Interactor {
    SlideContract.Presenter presenter;

    public SlideRemoteImpl(SlideContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void doGetInfor(String idUser) {
        Profile profile = new Gson().fromJson(idUser,Profile.class);
        if(profile == null ){
            presenter.notFoundInfor();
            return;
        }
        getmService().getProfile(profile.getId(), AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        presenter.getInforSuccess(response.body().getData());
                    } else {
                        presenter.notFoundInfor();
                    }
                } else {
                    presenter.notFoundInfor();
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.notFoundInfor();

            }
        });
    }
}
