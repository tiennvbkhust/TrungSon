package com.skynetsoftware.trungson.ui.tabhome.listproduct;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListProductFragment extends BaseFragment implements ListProductContract.View, ICallback {
    ListProductContract.Presenter presenter;
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    ListProductAdapter listProductAdapter;
    List<Product> listProducts;

    public static ListProductFragment newInstance() {
        Bundle args = new Bundle();
        ListProductFragment fragment = new ListProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_list_product;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        rcvproduct.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvproduct.setHasFixedSize(true);
    }

    @Override
    protected void initVariables() {
        presenter = new ListProductPresenter(this);
        presenter.getListProduct();
    }

    @Override
    public void onSuccessGetListProduct(List<Product> list) {
        listProducts = list;
        listProductAdapter = new ListProductAdapter(listProducts, getContext(), this);
        rcvproduct.setAdapter(listProductAdapter);
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        super.onDestroyView();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {
        ((MainActivity) (getActivity())).showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onError(String message) {
        ((MainActivity) (getActivity())).showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onCallBack(int pos) {

    }
}
