package com.skynetsoftware.trungson.ui.main;



import java.io.File;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    MainContract.Interactor interactor;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.interactor = new MainRemoteImpl(this);
    }


    @Override
    public void getInfor() {
        interactor.getInfor();
    }

    @Override
    public void onDestroyView() {
        view = null;
    }


    @Override
    public void getInforSuccess() {
        if(view ==null) return;
        view.getInforSuccess();
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

    }
}
