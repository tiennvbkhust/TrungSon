package com.skynetsoftware.trungson.ui.chat.chatting;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChattingRemoteImpl extends Interactor implements ChattingContract.Interactor {
    ChattingContract.ChattingListener listener;

    public ChattingRemoteImpl(ChattingContract.ChattingListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getMessages(String idShop, String idUser) {
        String id1 ,id2;
        if (AppController.getInstance().getmProfileUser().getType() == AppConstant.TYPE_USER) {
            id1 = idUser;
            id2 = idShop;
        }else{
            id1 = idShop;
            id2 = idUser;
        }
        getmService().getListMessageBetween(id1, id2, AppController.getInstance().getmProfileUser().getType()).enqueue(new CallBackBase<ApiResponse<List<Message>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Message>>> call, Response<ApiResponse<List<Message>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetMessages(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Message>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

    @Override
    public void sendMessage(String idShop, String idUser, String content, String time) {
        String id1 ,id2;
        if (AppController.getInstance().getmProfileUser().getType() == AppConstant.TYPE_USER) {
            id1 = idUser;
            id2 = idShop;
        }else{
            id1 = idShop;
            id2 = idUser;
        }
        getmService().sendMessageTo(id1, id2, time, content, AppController.getInstance().getmProfileUser().getType()).enqueue(new CallBackBase<ApiResponse<Message>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessSendMessage(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Message>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
