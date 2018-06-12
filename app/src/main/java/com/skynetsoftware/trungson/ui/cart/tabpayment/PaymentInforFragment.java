package com.skynetsoftware.trungson.ui.cart.tabpayment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.cart.tabcart.ListProductCartAdapter;
import com.skynetsoftware.trungson.ui.detailproduct.DetailProductActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentInforFragment extends BaseFragment  {
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
    @Override
    protected int initLayout() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this,view);
        rcvproduct.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvproduct.setHasFixedSize(true);
    }

    @Override
    protected void initVariables() {
        list = AppController.getInstance().getListProducts();
        rcvproduct.setAdapter(new ListProductPaymentAdapter(getContext(), list));

    }

    @Override
    public void onResume() {
        super.onResume();
        countProducts();
    }

    public void countProducts() {
        List<Product> list = AppController.getInstance().getListProducts();
        int total = 0;
        double price = 0;
        for (Product product : list) {
            total += product.getNumberOfProduct();
            price += (product.getNumberOfProduct() * product.getPrice());
        }
        tvNumberProduct.setText(String.format("%d sản phẩm",total));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format),price),Html.FROM_HTML_MODE_COMPACT));
        }else{
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format),price)));
        }
    }


}
