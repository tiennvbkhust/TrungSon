package com.skynetsoftware.trungson.ui.forgotpassword;


import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPwImpl extends Interactor implements ForgotPwContract.Interactor {
    ForgotPwContract.Presenter presenter;

    public ForgotPwImpl(ForgotPwContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doSignUp(String email, int type) {
        getmService().forgotPss( email, type).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        presenter.signUpSuccess();
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }
}
