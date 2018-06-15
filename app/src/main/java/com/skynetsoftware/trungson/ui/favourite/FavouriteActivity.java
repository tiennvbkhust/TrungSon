package com.skynetsoftware.trungson.ui.favourite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.detailproduct.DetailProductActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavouriteActivity extends BaseActivity implements FavouriteContract.View, ICallback,SearchShopAdapter.CallBackService, SearchShopAdapter.IToggleCheckbox {

    @BindView(R.id.rcvShop)
    RecyclerView rcvShop;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;

    private ProgressDialogCustom dialog;
    private FavouriteContract.Presenter presenter;
    private List<Product> listShop;
    private SearchShopAdapter adapter;
    private Shop shopCurrentFocus;

    @Override
    protected int initLayout() {
        return R.layout.activity_favourite;
    }

    @Override
    protected void initVariables() {
        dialog = new ProgressDialogCustom(this);
        presenter = new FavouritePresenter(this);
        listShop = new ArrayList<>();
        adapter = new SearchShopAdapter(listShop, this, this, this,this);
        rcvShop.setAdapter(adapter);
        rcvShop.setVisibility(View.VISIBLE);
        presenter.getFavourites();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText("Danh sách yêu thích");
        rcvShop.setLayoutManager(new GridLayoutManager(this,2));
        rcvShop.setHasFixedSize(true);

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish();
            return;
        }

    }


    @Override
    public void onGetFavouritesSuccess(List<Product> list) {
        this.listShop.clear();
        listShop.addAll(list);
        adapter.updateChecked();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessToggleFavourite() {

    }


    @Override
    public Context getMyContext() {
        return null;
    }

    @Override
    public void showProgress() {
        dialog.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialog.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }

    // click item list to fire
    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(FavouriteActivity.this, DetailProductActivity.class);
        i.putExtra(AppConstant.MSG, listShop.get(pos).getId());
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.MSG, listShop.get(pos));
        i.putExtra(AppConstant.BUNDLE, b);
        startActivityForResult(i, 1000);
    }


    @Override
    public void toggle(int pos, boolean value) {
        presenter.toggleFavourite(listShop.get(pos).getId(), value);
    }
    @Override
    public void callBack(Product service) {
        Intent i =  new Intent(FavouriteActivity.this, DetailProductActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.MSG,service);
        i.putExtra(AppConstant.BUNDLE,b);
        startActivity(i);
    }


    @OnClick(R.id.imgBtn_back_toolbar)
    public void onViewClicked() {
        onBackPressed();
    }
}
