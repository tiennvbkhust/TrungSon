package com.skynetsoftware.trungson.ui.tabprofile.profile;

import com.google.gson.Gson;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileRemoteImpl extends Interactor implements ProfileContract.Interactor {
    public ProfileRemoteImpl(ProfileContract.OnFinishProfileListener listener) {
        this.listener = listener;
    }

    ProfileContract.OnFinishProfileListener listener;


    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void doGetInfor(String profileInfor) {
        Profile profile = new Gson().fromJson(profileInfor,Profile.class);
        if(profile == null ){
            listener.notFoundInfor();
            return;
        }
        getmService().getProfile(profile.getId(),2).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        listener.getInforSuccess(response.body().getData());
                    } else {
                        listener.notFoundInfor();
                    }
                } else {
                    listener.notFoundInfor();
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                listener.notFoundInfor();

            }
        });
    }
    @Override
    public void doUpdate(String type, double lat,double lng) {
        Profile profile =  AppController.getInstance().getmProfileUser();

        if(profile==null) {
            listener.onErrorAuthorization();
            return;
        }

        profile.setType(2);
        profile.setAddress(type);
        profile.setLat(lat);
        profile.setLng(lng);
        Map<String, RequestBody> map  = new HashMap<>();
        map.put("phone",ApiUtil.createPartFromString(profile.getPhone()));
        map.put("type",ApiUtil.createPartFromString(String.valueOf(profile.getType())));
        map.put("email",ApiUtil.createPartFromString(profile.getEmail()));
        map.put("id",ApiUtil.createPartFromString(profile.getId()));
        map.put("name",ApiUtil.createPartFromString(profile.getName() ));
        map.put("address",ApiUtil.createPartFromString(profile.getAddress() ));
        map.put("lat",ApiUtil.createPartFromString(String.valueOf(profile.getLat())));
        map.put("lng",ApiUtil.createPartFromString(String.valueOf(profile.getLng())));

        getmService().updateInfor(map).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        listener.onSuccessUpdate();
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
    @Override
    public void doUpdateAvatar(File file, MultipartBody.Part part) {
        RequestBody idRequest = ApiUtil.createPartFromString(AppController.getInstance().getmProfileUser().getId());
        RequestBody typeRequest = ApiUtil.createPartFromString("2");
        Map<String, RequestBody> map = new HashMap<>();
        map.put("id",idRequest);
        map.put("type",typeRequest);
        getmService().uploadAvatar(part,map).enqueue(new CallBackBase<ApiResponse<String>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        listener.onSuccessUpdatedAvatar();
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<String>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });


    }


}
