package com.skynetsoftware.trungson.ui.updateInfor;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class UpdateInforRemoteImpl extends Interactor implements UpdateInforContract.Interactor {
    UpdateInforContract.UpdateInforListenr listenr;

    public UpdateInforRemoteImpl(UpdateInforContract.UpdateInforListenr listenr) {
        this.listenr = listenr;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void doUpdate(String type, String text) {
       Profile profile =  AppController.getInstance().getmProfileUser();

        if(profile==null) {
            listenr.onErrorAuthorization();
            return;
        }
        if (type.equals("phone")) {
           profile.setPhone(text);
        } else if (type.equals("email")) {
           profile.setEmail(text);
        } else {
           profile.setName(text);
        }
        profile.setType(2);
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
