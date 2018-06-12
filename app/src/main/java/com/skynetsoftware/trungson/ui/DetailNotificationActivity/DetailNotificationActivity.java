package com.skynetsoftware.trungson.ui.DetailNotificationActivity;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Notification;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailNotificationActivity extends BaseActivity implements DetailNotificationContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.card)
    CardView card;
    private DetailNotificationContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;


    @Override
    protected int initLayout() {
        return R.layout.activity_detail_notification;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new DetailNotificationPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            presenter.getDetail(getIntent().getExtras().getString(AppConstant.MSG));
        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText(R.string.detail_notification_title);

        swipe.setOnRefreshListener(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
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
    public void onRefresh() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            presenter.getDetail(getIntent().getExtras().getString(AppConstant.MSG));
        }
    }


    @Override
    public void onSuccessGetDetail(Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.setText(Html.fromHtml(notification.getName(), Html.FROM_HTML_MODE_COMPACT));

        } else {
            tvContent.setText(Html.fromHtml(notification.getName()));
        }
        tvTitle.setText(notification.getTitle());
        tvTime.setText(notification.getTime());

        setResult(RESULT_OK);
    }


}
