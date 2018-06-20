package com.skynetsoftware.trungson.ui.checkin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Place;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.AlertDialogCustom;
import com.skynetsoftware.trungson.ui.views.DialogTwoButtonUtils;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchMapAdressActivity extends BaseActivity implements OnMapReadyCallback , CheckinContract.View {

    private static final int PLACE_PICKER_REQUEST = 10;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.address)
    TextView tvaddress;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvToolbar;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    Place place;
    private CheckinContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;

    @Override
    protected int initLayout() {
        return R.layout.activity_search_map_adress;
    }

    @Override
    protected void initVariables() {
        tvToolbar.setText("Check in");
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new CheckinPresenter(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }
                });

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapLoaded() {
                if (myLatlng != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 17));
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                loading.setVisibility(View.VISIBLE);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(SearchMapAdressActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (addresses.size() == 0) {
                        loading.setVisibility(View.INVISIBLE);
                        return;
                    }
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    place = new Place();
                    place.setLatLng(mMap.getCameraPosition().target);
                    place.setName(knownName);
                    String[] splitAddress = address.split(",");
                    if(splitAddress.length>2)
                    place.setAddress(splitAddress[0]+", "+splitAddress[1]);
                    else
                        place.setAddress(address);
                    loading.setVisibility(View.INVISIBLE);
                    tvaddress.setText(place.getAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @OnClick({ R.id.lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.lay:
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putParcelable(AppConstant.MSG, this.place);
                i.putExtra(AppConstant.BUNDLE, b);
                setResult(RESULT_OK, i);
                final DialogTwoButtonUtils dialogTwoButtonUtils =  new DialogTwoButtonUtils(this);
                dialogTwoButtonUtils.setText("Bạn có chắc chắn muốn checkin tại đây",place.getAddress());
                dialogTwoButtonUtils.setDialogTwoButtonOnClick(new DialogTwoButtonUtils.DialogTwoButtonOnClick() {
                    @Override
                    public void cancel() {
                        dialogTwoButtonUtils.dismiss();
                    }

                    @Override
                    public void okClick() {
                        dialogTwoButtonUtils.dismiss();
                        presenter.checkinHere(place);
                    }
                });
                dialogTwoButtonUtils.show();
             //   finish();
                break;
        }
    }
    @OnClick({R.id.imgBtn_back_toolbar})
    public void onViewBaClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_back_toolbar:
                onBackPressed();
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(data, this);
                passPlaceBack(place);
                return;
            }
        }
    }

    private void passPlaceBack(com.google.android.gms.location.places.Place place) {
        this.place = new Place();
        this.place.setLatLng(place.getLatLng());
        this.place.setName(place.getName().toString());
        this.place.setAddress(place.getAddress().toString());
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.MSG, this.place);
        i.putExtra(AppConstant.BUNDLE, b);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public void onSuccessCheckin() {
        AlertDialogCustom.showDialogSuccess(this,R.drawable.ic_winner,"Hệ thống đã ghi nhận việc checkin ngày công của bạn").show();
        AppController.getInstance().getmProfileUser().setState(2);

    }

    @Override
    public void onSuccessCheckOut() {

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
        showToast(message,AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        showToast(message,AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }
}
