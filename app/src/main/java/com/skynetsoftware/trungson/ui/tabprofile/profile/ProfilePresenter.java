package com.skynetsoftware.trungson.ui.tabprofile.profile;

import android.net.Uri;


import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.io.File;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    ProfileContract.Interactor interactor;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        interactor = new ProfileRemoteImpl(this);
    }

    @Override
    public void getInfor() {
        view.showProgress();
        String profileStr = AppController.getInstance().getmSetting().getString(AppConstant.KEY_PROFILE, "");
        if (profileStr.isEmpty()) {
            view.onError("not found");
        } else {
            view.showProgress();
            interactor.doGetInfor(profileStr);
        }
    }

    @Override
    public void uploadAvatar(File file) {
        view.showProgress();
        interactor.doUpdateAvatar(file, ApiUtil.prepareFilePart("img", Uri.fromFile(file)));
    }

    @Override
    public void update(String type,double lat,double lng) {
        if(type == null ||  type.isEmpty() ){
            onError("Đã có lỗi xảy ra.");
            return;
        }
        view.showProgress();
        interactor.doUpdate(type,lat,lng);
    }

    @Override
    public void onDestroyView() {
        view = null ;
    }

    @Override
    public void onSuccessUpdate() {
        if(view == null) return;
        view.hiddenProgress();
        view.onSuccessUpdate();
    }

    @Override
    public void getInforSuccess(Profile profile) {
        if(view ==null ) return;
        view.hiddenProgress();
        AppController.getInstance().setmProfileUser(profile);
        view.onSuccessGetInfor();
    }

    @Override
    public void notFoundInfor() {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }

    @Override
    public void onSuccessUpdatedAvatar() {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onSuccessUpdatedAvatar();
    }
    @Override
    public void onErrorApi(String message) {
        if(view ==null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if(view ==null) return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onErrorAuthorization() {
        if(view ==null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
