package com.skynetsoftware.trungson.ui.cart.tabcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.detailproduct.DetailProductActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartFragment extends BaseFragment implements ListProductCartAdapter.OnListenerClickItemEvent {
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    List<Product> list;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        rcvproduct.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvproduct.setHasFixedSize(true);
    }

    @Override
    protected void initVariables() {
        list = AppController.getInstance().getListProducts();
        LogUtils.e(new Gson().toJson(list));
        rcvproduct.setAdapter(new ListProductCartAdapter(getContext(), list, this));
    }

    @Override
    public void onClickNoAccept(int position) {
        ((CartActivity) getActivity()).countProducts();
    }

    @Override
    public void onClickAccept(int position) {
        if (AppController.getInstance().getListProducts().size() == 0) return;
        Intent i = new Intent(getActivity(), DetailProductActivity.class);
        i.putExtra(AppConstant.MSG, AppController.getInstance().getListProducts().get(position).getId());
        startActivity(i);
    }
}
