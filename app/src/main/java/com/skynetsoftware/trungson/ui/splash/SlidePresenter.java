package com.skynetsoftware.trungson.ui.splash;

import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.utils.AppConstant;

public class SlidePresenter implements SlideContract.Presenter {
    SlideContract.View view;
    SlideContract.Interactor interactor;

    public SlidePresenter(SlideContract.View view) {
        this.view = view;
        interactor = new SlideRemoteImpl(this);
    }

    @Override
    public void getInfor() {
        String profileStr = AppController.getInstance().getmSetting().getString(AppConstant.KEY_PROFILE, "");
        if (profileStr.isEmpty()) {
            view.onError("not found");
        } else {
            view.showProgress();
            interactor.doGetInfor(profileStr);
        }

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
        view.onError("not found");
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError("not found");

    }

    @Override
    public void onError(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError("not found");

    }

    @Override
    public void onErrorAuthorization() {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError("not found");

    }
}
