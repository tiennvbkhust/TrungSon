package com.skynetsoftware.trungson.ui.tabproduct.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.interfaces.Updateable;
import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.detailproduct.DetailProductActivity;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductFragment extends BaseFragment implements ProductContract.View, ICallback, Updateable {
    @BindView(R.id.rcvproduct)
    RecyclerView rcvproduct;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.empty_view)
    TextView emptyView;
    ProductContract.Presenter presenter;
    Category category;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter adapter;

    public static ProductFragment newInstance(Category ca) {
        Bundle args = new Bundle();
        args.putParcelable("category", ca);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        rcvproduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvproduct.setHasFixedSize(true);

    }

    @Override
    protected void initVariables() {
        presenter = new ProductPresenter(this);
        category = getArguments().getParcelable("category");
        //  presenter.getListProducts(category.getId());
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.getListProducts(category.getId());
    }

    @Override
    public void onSuccessGetListProducts(List<Product> list) {
        this.productList.clear();
        this.productList.addAll(list);
        if (adapter == null)
            adapter = new ProductAdapter(this.productList, getContext(), this);
        rcvproduct.setAdapter(adapter);
        if (list.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            rcvproduct.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            rcvproduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void showProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenProgress() {
        loading.setVisibility(View.GONE);

    }

    @Override
    public void onErrorApi(String message) {
        ((MainActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        ((MainActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }


    @Override
    public void onDestroyView() {
        // presenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(getActivity(), DetailProductActivity.class);
        i.putExtra(AppConstant.MSG, productList.get(pos).getId());
        startActivity(i);
    }

    @Override
    public void update() {
//        if (presenter != null)
//            presenter.getListProducts(category.getId());
//        else
//            LogUtils.e("NULLLLLLLLLLLLLLLLLLLLLL");
    }


}
