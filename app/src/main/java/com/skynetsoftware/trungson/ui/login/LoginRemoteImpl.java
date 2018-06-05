package com.skynetsoftware.trungson.ui.login;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginRemoteImpl extends Interactor implements LoginContract.Interactor {
    LoginContract.Presenter presenter;

    public LoginRemoteImpl(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doLogin(String username, String password, int type) {
        getmService().login(username, password, type).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        presenter.onSuccessLogin(response.body().getData());
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.onError(t.getMessage());
            }
        });
    }

    @Override
    public void doLoginGGG(String idGG,String name,String email) {
        getmService().loginWithGG(name,email,idGG).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        presenter.onSuccessLogin(response.body().getData());
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }


    @Override
    public void doLoginFB(final SimpleFacebook simpleFacebook) {
        simpleFacebook.login(new OnLoginListener() {
            @Override
            public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                if (accessToken != null && !accessToken.isEmpty()) {
                    PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
                    pictureAttributes.setHeight(500);
                    pictureAttributes.setWidth(500);
                    pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);
                    com.sromku.simple.fb.entities.Profile.Properties properties = new com.sromku.simple.fb.entities.Profile.Properties.Builder()
                            .add(com.sromku.simple.fb.entities.Profile.Properties.ID)
                            .add(com.sromku.simple.fb.entities.Profile.Properties.EMAIL)
                            .add(com.sromku.simple.fb.entities.Profile.Properties.NAME)

                            .build();
                    simpleFacebook.getProfile(properties, new OnProfileListener() {
                        @Override
                        public void onComplete(com.sromku.simple.fb.entities.Profile response) {
                            super.onComplete(response);
                            LogUtils.e("id facebook :" + response.getId() + " \t email : " + response.getEmail() + "\t name : " + response.getName() + "\t avt : " + response.getPicture());
                            if(response.getEmail()==null || response.getEmail().isEmpty()){
                                presenter.onError("Thiếu thông tin email trên facebook");
                                return;
                            }
                            loginApi(response.getName(),response.getEmail(),response.getId(),AppConstant.TYPE_USER);
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            super.onException(throwable);
                            throwable.fillInStackTrace();
                            ToastUtils.showShort(throwable.getMessage());
                            presenter.onError(throwable.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("cancel");

            }

            @Override
            public void onException(Throwable throwable) {
                ToastUtils.showShort(throwable.getMessage());
                LogUtils.e(throwable.getMessage());
                presenter.onError(throwable.getMessage());

            }

            @Override
            public void onFail(String reason) {
                ToastUtils.showShort(reason);
                presenter.onError(reason);

            }
        });
    }

    @Override
    public void loginApi(String username,String email, String fbID, int type) {
        getmService().loginWithFB(username,email,fbID,1).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        presenter.onSuccessLogin(response.body().getData());
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }


    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }
}
