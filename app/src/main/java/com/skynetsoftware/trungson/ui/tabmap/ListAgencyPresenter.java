package com.skynetsoftware.trungson.ui.tabmap;

import com.google.android.gms.maps.model.LatLng;
import com.skynetsoftware.trungson.models.Shop;

import java.util.List;

public class ListAgencyPresenter implements ListAgencyContract.Presenter {
    ListAgencyContract.View view;
    ListAgencyContract.Interactor interactor;

    public ListAgencyPresenter(ListAgencyContract.View view) {
        this.view = view;
        interactor = new ListAgencyRemoteImpl(this);
    }

    @Override
    public void getListAgency(LatLng latLng) {
        if(view==null) return;
        view.showProgress();
        interactor.getListAgency(latLng);
    }

    @Override
    public void onDestroyView() {
        view = null ;
    }

    @Override
    public void onSuccessGetListAgency(List<Shop> list) {
        if(view ==null ) return;
        view.hiddenProgress();
        if(list!=null)
        view.onSuccessGetListAgency(list);
    }

    @Override
    public void onErrorApi(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onError(String message) {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if(view ==null ) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }
}
