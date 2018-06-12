package com.skynetsoftware.trungson.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Notification;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.DetailNotificationActivity.DetailNotificationActivity;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity implements ICallback, NotificationContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private NotificationContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    @BindView(R.id.rcvNotification)
    RecyclerView rcvNotification;
    Profile profileModel;
    List<Notification> listGroupServices = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new NotificationPresenter(this);
        profileModel = AppController.getInstance().getmProfileUser();
        if (profileModel == null) {
            showDialogExpired();
            return;
        }
        presenter.getAllService(profileModel.getId());
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText(R.string.title_notifications);
        rcvNotification.setLayoutManager(new LinearLayoutManager(this));
        rcvNotification.setHasFixedSize(true);
        swipe.setOnRefreshListener(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.swipe;
    }


    @OnClick(R.id.imgBtn_back_toolbar)
    public void onViewClicked() {
        onBackPressed();
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
        swipe.setRefreshing(false);
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorAuthorization() {

    }


    @Override
    public void onSuccessGetServices(List<Notification> listGroupServices) {
        if (listGroupServices != null) {
            this.listGroupServices.clear();
            this.listGroupServices.addAll(listGroupServices);
        }
        rcvNotification.setAdapter(new NotificationAdapter(this.listGroupServices, this, this));
    }


    @Override
    public void onRefresh() {
        presenter.getAllService(profileModel.getId());
    }

    // Click to item to fire this
    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(NotificationActivity.this, DetailNotificationActivity.class);
        i.putExtra(AppConstant.MSG, this.listGroupServices.get(pos).getId());
        startActivityForResult(i, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            onRefresh();
        }
    }
}
