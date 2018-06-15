package com.skynetsoftware.trungson.ui.news;

import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.News;

import java.util.List;

public class NewsPresenter implements NewsContract.Presenter {
    NewsContract.View view;
    NewsContract.Interactor interactor;

    public NewsPresenter(NewsContract.View view) {
        this.view = view;
        this.interactor = new NewsRemoteImpl(this);
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
    public void onSucessGetListHistories(List<News> list) {
        if (view == null) return;
        view.hiddenProgress();
        view.onSucessGetListHistories(list);
    }
}
