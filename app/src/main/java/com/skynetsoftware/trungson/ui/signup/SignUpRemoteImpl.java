package com.skynetsoftware.trungson.ui.signup;


import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpRemoteImpl extends Interactor implements SignUpContract.Interactor {
    SignUpContract.Presenter presenter;

    public SignUpRemoteImpl(SignUpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doSignUp(String phone) {
        getmService().sendCode( phone).enqueue(new CallBackBase<ApiResponse<String>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        presenter.signUpSuccess(response.body().getData());
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<String>> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }
}
