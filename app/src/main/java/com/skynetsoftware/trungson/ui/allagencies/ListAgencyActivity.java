package com.skynetsoftware.trungson.ui.allagencies;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.productOfAgency.ListProductsOfAgencyActivity;
import com.skynetsoftware.trungson.ui.tabhome.listagency.ListAgencyAdapter;
import com.skynetsoftware.trungson.ui.tabhome.listagency.ListAgencyContract;
import com.skynetsoftware.trungson.ui.tabhome.listagency.ListAgencyPresenter;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.util.Utils.getContext;

public class ListAgencyActivity extends BaseActivity implements ListAgencyContract.View, OnSuccessListener<Location>, ICallback {
    @BindView(R.id.rcvAgency)
    RecyclerView rcvAgency;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitle_toolbar;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private ListAgencyContract.Presenter presenter;
    private ListAgencyAdapter listAgencyAdapter;
    private List<Shop> listAgencies;


    @Override
    protected int initLayout() {
        return R.layout.activity_all_agencies;
    }


    @Override
    protected void initVariables() {
        tvTitle_toolbar.setText("Danh sách cửa hàng");
        presenter = new ListAgencyPresenter(this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this);
        myLatlng = new Gson().fromJson(AppController.getInstance().getmSetting().getString(AppConstant.LATLNG), LatLng.class);

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        rcvAgency.setLayoutManager(new LinearLayoutManager(this));
        rcvAgency.setHasFixedSize(true);
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myLatlng != null) {
            presenter.getListAgency(myLatlng);
        }
    }

    @Override
    public void onSuccessGetListAgency(List<Shop> list) {
        this.listAgencies = list;
        listAgencyAdapter = new ListAgencyAdapter(this.listAgencies, getContext(), this);
        rcvAgency.setAdapter(listAgencyAdapter);
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();

        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

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

    @OnClick(R.id.imgBtn_back_toolbar)
    public void onCLick(){
        onBackPressed();
    }
    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
            presenter.getListAgency(myLatlng);
            AppController.getInstance().getmSetting().put(AppConstant.LATLNG, new Gson().toJson(myLatlng));
        }
    }

    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(ListAgencyActivity.this, ListProductsOfAgencyActivity.class);
        i.putExtra("idAgency", listAgencies.get(pos).getId());
        i.putExtra("url", listAgencies.get(pos).getAvatar());

        startActivity(i);
    }
}
