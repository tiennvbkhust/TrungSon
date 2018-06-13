package com.skynetsoftware.trungson.ui.cart.tabpayment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PaymentInforFragment extends BaseFragment implements PaymentContract.View {
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.spTypePayment)
    Spinner spTypePayment;
    @BindView(R.id.edtNumberCard)
    EditText edtNumberCard;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtDate)
    EditText edtDate;
    @BindView(R.id.edtCVV)
    EditText edtCVV;
    @BindView(R.id.layoutBottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layoutCard)
    LinearLayout layoutCard;
    private PaymentContract.Presenter presenter;

    public static PaymentInforFragment newInstance() {

        Bundle args = new Bundle();

        PaymentInforFragment fragment = new PaymentInforFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tvNumberProduct)
    TextView tvNumberProduct;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    List<Product> list;
    private ProgressDialogCustom dialogCustom;
    double price = 0;

    @Override
    protected int initLayout() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        rcvproduct.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvproduct.setHasFixedSize(true);
        spTypePayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    layoutCard.setVisibility(View.GONE);

                } else {
                    layoutCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initVariables() {
        list = AppController.getInstance().getListProducts();
        rcvproduct.setAdapter(new ListProductPaymentAdapter(getContext(), list));
        dialogCustom = new ProgressDialogCustom(getContext());
        presenter = new PaymentPresenter(this);
        presenter.getInforCard();
    }

    @Override
    public void onResume() {
        super.onResume();
        countProducts();
    }

    public void countProducts() {
        List<Product> list = AppController.getInstance().getListProducts();
        int total = 0;
        price = 0;
        for (Product product : list) {
            total += product.getNumberOfProduct();
            price += (product.getNumberOfProduct() * product.getPrice());
        }
        tvNumberProduct.setText(String.format("%d sản phẩm", total));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format), price), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format), price)));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btnNext)
    public void onViewClicked() {
        presenter.saveInfoCard(edtDate.getText().toString(), edtNumberCard.getText().toString(),
                edtName.getText().toString(), edtCVV.getText().toString());
        presenter.paidCart(spTypePayment.getSelectedItemPosition(), price);
    }

    @Override
    public void onSuccessGetInforCard(String number, String name, String date, String cvv) {
        edtCVV.setText(cvv);
        edtDate.setText(date);
        edtName.setText(name);
        edtNumberCard.setText(number);
    }

    @Override
    public void onSuccessPaid(String booking) {
        ((CartActivity) getActivity()).showToast("Đặt hàng thành công", AppConstant.POSITIVE);
        countProducts();
        ((CartActivity) getActivity()).countProducts();
        rcvproduct.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void showProgress() {
        dialogCustom.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogCustom.hideDialog();

    }

    @Override
    public void onErrorApi(String message) {
        ((CartActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        ((CartActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }
}
