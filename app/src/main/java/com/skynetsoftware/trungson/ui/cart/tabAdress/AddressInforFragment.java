package com.skynetsoftware.trungson.ui.cart.tabAdress;

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

public class AddressInforFragment extends BaseFragment{
    public static AddressInforFragment newInstance() {

        Bundle args = new Bundle();

        AddressInforFragment fragment = new AddressInforFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_address_info;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this,view);

    }

    @Override
    protected void initVariables() {

    }

}
