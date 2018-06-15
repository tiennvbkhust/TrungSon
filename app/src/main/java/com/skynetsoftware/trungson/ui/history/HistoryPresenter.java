package com.skynetsoftware.trungson.ui.history;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BasePresenter;

import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter {
    HistoryContract.View view;
    HistoryContract.Interactor interactor;

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
        this.interactor = new HistoryRemoteImpl(this);
    }

    @Override
    public void onDestroyView() {
        view = null;

    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }

    @Override
    public void getListHistories() {
        view.showProgress();
        interactor.getListHistories();
    }

    @Override
    public void onSucessGetListHistories(List<Booking> list) {
        if (view == null) return;
        view.hiddenProgress();
        view.onSucessGetListHistories(list);
    }
}
