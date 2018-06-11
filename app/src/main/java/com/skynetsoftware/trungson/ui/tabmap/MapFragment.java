package com.skynetsoftware.trungson.ui.tabmap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.productOfAgency.ListProductsOfAgencyActivity;
import com.skynetsoftware.trungson.ui.views.StartSnapHelper;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.skynetsoftware.trungson.utils.CommomUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// @ref : https://code.i-harness.com/en/q/1274ea7
public class MapFragment extends BaseFragment implements OnMapReadyCallback, ICallback, OnSuccessListener<Location>, ListAgencyContract.View {
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.rcvAgency)
    RecyclerView rcvAgency;
    private GoogleMap mMap;
    @BindView(R.id.mapView)
    MapView mMapView;
    Marker myMarker;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private HomeFragment.CallBackHomeFragment callBackHomeFragment;
    private ListAgencyContract.Presenter presenter;
    private List<Shop> listAgencies;
    HashMap<Marker, Shop> markerHashMap = new HashMap<>();
    LatLngBounds.Builder latLngBounds;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_maps;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container,
                false);
        ButterKnife.bind(this, v);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return v;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this);
        presenter = new ListAgencyPresenter(this);
        myLatlng = new Gson().fromJson(AppController.getInstance().getmSetting().getString(AppConstant.LATLNG), LatLng.class);
        addMyLocation();

        rcvAgency.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvAgency.setHasFixedSize(true);
        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(rcvAgency);
        return v;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
            addMyLocation();
        }
    }

    private void addMyLocation() {
        if (myLatlng != null && mMap != null) {
            presenter.getListAgency(myLatlng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 15));
            if (myMarker == null) {
                myMarker = mMap.addMarker(new MarkerOptions().position(myLatlng).icon(CommomUtils.bitmapDescriptorFromVector(getContext(), R.drawable.dot)));
            } else {
                myMarker.remove();
                myMarker = null;
                myMarker = mMap.addMarker(new MarkerOptions().position(myLatlng).icon(CommomUtils.bitmapDescriptorFromVector(getContext(), R.drawable.dot)));

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMyLocation();
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if(latLngBounds!=null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),15));
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (markerHashMap.get(marker )!= null) {
                    Intent i =new Intent(getActivity(), ListProductsOfAgencyActivity.class);
                    i.putExtra("idAgency",markerHashMap.get(marker).getId());
                    startActivity(i);
                }
                return false;
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBackHomeFragment = (HomeFragment.CallBackHomeFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBackHomeFragment = null;
    }

    @OnClick({R.id.imgHome, R.id.tvSearch, R.id.btnMylocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                callBackHomeFragment.onClick(view);
                break;
            case R.id.tvSearch:
                break;
            case R.id.btnMylocation:
                if (myLatlng != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 15));
                }
                break;
        }
    }

    @Override
    public void onSuccessGetListAgency(List<Shop> list) {
        this.listAgencies = list;
        rcvAgency.setAdapter(new ListMapAgencyAdapter(this.listAgencies, getContext(), this));
        if (mMap != null) {
            latLngBounds = LatLngBounds.builder();
            for (Shop shop : this.listAgencies) {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(shop.getLat(),shop.getLng()))
                        .icon(CommomUtils.bitmapDescriptorFromVector(getContext(), R.drawable.ic_marker)));
                markerHashMap.put(marker, shop);
                latLngBounds.include(marker.getPosition());
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),15));
        }
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
    public void onCallBack(int pos) {
        Intent i =new Intent(getActivity(), ListProductsOfAgencyActivity.class);
        i.putExtra("idAgency",listAgencies.get(pos).getId());
        startActivity(i);
    }
}
