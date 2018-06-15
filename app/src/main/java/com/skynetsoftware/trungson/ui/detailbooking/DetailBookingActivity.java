package com.skynetsoftware.trungson.ui.detailbooking;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.tabpayment.ListProductPaymentAdapter;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailBookingActivity extends BaseActivity implements DetailBookingContract.View {
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.spTypePayment)
    TextView spTypePayment;
    @BindView(R.id.edtNumberCard)
    EditText edtNumberCard;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.layoutCard)
    LinearLayout layoutCard;
    @BindView(R.id.tvNumberProduct)
    TextView tvNumberProduct;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvState)
    TextView tvStateProduct;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.layoutBottom)
    LinearLayout layoutBottom;
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    private DetailBookingContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;

    @Override
    protected int initLayout() {
        return R.layout.activity_detail_booking;
    }

    @Override
    protected void initVariables() {
        presenter = new DetailBookingPresenter(this);
        dialogLoading = new ProgressDialogCustom(this);
        presenter.getInforCard();
        if (getIntent() != null && getIntent().getExtras() != null) {
            presenter.getDetailBooking(getIntent().getExtras().getString(AppConstant.MSG));
        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText("Thông tin chi tiết");
        rcvproduct.setLayoutManager(new LinearLayoutManager(this));
        rcvproduct.setHasFixedSize(true);
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @Override
    public void onSuccessGetInforCard(String number, String name, String date, String cvv) {
        edtName.setText(name);
        edtNumberCard.setText(number);
    }

    @OnClick(R.id.imgBtn_back_toolbar)
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onSuccessGetDetailBooking(Booking booking) {
        rcvproduct.setAdapter(new ListProductPaymentAdapter(this, booking.getProduct()));
        tvAddress.setText("Địa chỉ: " + booking.getAddress());
        tvDate.setText("Thời gian: " + booking.getDate_booking());
        tvName.setText("Người nhận: " + booking.getName());
        tvNumberProduct.setText(booking.getQuantity() + " sản phẩm");
        tvTotal.setText("Tổng tiền: " + String.format("%,.0f", booking.getPrice()));
        if (booking.getMethod_payment() == 1) {
            spTypePayment.setText("Thanh toán trực tiếp");
            layoutCard.setVisibility(View.GONE);
        } else {
            spTypePayment.setText("Thanh toán qua thẻ");
            layoutCard.setVisibility(View.VISIBLE);

        }
        tvPhone.setText("SDT: " + booking.getPhone());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvStateProduct.setText(
                    Html.fromHtml(
                            String.format(getString(R.string.product_state_format), "Còn hàng"),
                            Html.FROM_HTML_MODE_COMPACT));
            if (booking.getActive() == 1) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format_ots), "Đã đặt"),
                        Html.FROM_HTML_MODE_COMPACT));
            } else if (booking.getActive() == 2) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đang giao"),
                        Html.FROM_HTML_MODE_COMPACT));
            } else if (booking.getActive() == 3) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đã giao"),
                        Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đã hủy"),
                        Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            if (booking.getActive() == 1) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format_ots), "Đã đặt")));
            } else if (booking.getActive() == 2) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đang giao")));
            } else if (booking.getActive() == 3) {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đã giao")));
            } else {
                tvStateProduct.setText(Html.fromHtml(String.format(getString(R.string.product_state_format), "Đã hủy")));
            }
        }
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

    }
}
