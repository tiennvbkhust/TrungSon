package com.skynetsoftware.trungson.ui.cart.tabcart;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartFragment extends BaseFragment {
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;

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
    }

    @Override
    protected void initVariables() {

    }


}
