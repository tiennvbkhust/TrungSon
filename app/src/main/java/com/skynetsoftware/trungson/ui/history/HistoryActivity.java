package com.skynetsoftware.trungson.ui.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.tabcart.ListProductCartAdapter;
import com.skynetsoftware.trungson.ui.detailbooking.DetailBookingActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity implements ListProductCartAdapter.OnListenerClickItemEvent, HistoryContract.View {
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    private HistoryContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private List<Booking> listBookings;

    @Override
    protected int initLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new HistoryPresenter(this);
        presenter.getListHistories();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText("Lịch sử đơn hàng");
        rcvproduct.setLayoutManager(new LinearLayoutManager(this));
        rcvproduct.setHasFixedSize(true);
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
        Intent i = new Intent(HistoryActivity.this, DetailBookingActivity.class);
        i.putExtra(AppConstant.MSG, listBookings.get(position).getId());
        startActivity(i);
    }

    @Override
    public void onSucessGetListHistories(List<Booking> list) {
        this.listBookings = list;
        rcvproduct.setAdapter(new HistoryAdapter(this, list, this));
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
