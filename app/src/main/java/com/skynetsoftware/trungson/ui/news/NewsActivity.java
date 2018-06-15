package com.skynetsoftware.trungson.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.News;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.tabcart.ListProductCartAdapter;
import com.skynetsoftware.trungson.ui.detailbooking.DetailBookingActivity;
import com.skynetsoftware.trungson.ui.history.DetailActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity implements ListProductCartAdapter.OnListenerClickItemEvent, NewsContract.View {
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    private NewsContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private List<News> listBookings;

    @Override
    protected int initLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new NewsPresenter(this);
        presenter.getListHistories();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText("Tin tức");
        rcvproduct.setLayoutManager(new LinearLayoutManager(this));
        rcvproduct.setHasFixedSize(true);
        rcvproduct.setBackgroundColor(ContextCompat.getColor(this,R.color.colorB));
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.rcvproduct;
    }


    @OnClick(R.id.imgBtn_back_toolbar)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onClickNoAccept(int position) {

    }

    @Override
    public void onClickAccept(int position) {
        Intent i = new Intent(NewsActivity.this, DetailActivity.class);
        i.putExtra(AppConstant.MSG, listBookings.get(position).getId());
        Bundle b= new Bundle();
        b.putParcelable(AppConstant.MSG,listBookings.get(position));
        i.putExtra(AppConstant.BUNDLE,b);
        startActivity(i);
    }

    @Override
    public void onSucessGetListHistories(List<News> list) {
        this.listBookings = list;
        rcvproduct.setAdapter(new NewsAdapter(this, list, this));
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();

    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }


}
