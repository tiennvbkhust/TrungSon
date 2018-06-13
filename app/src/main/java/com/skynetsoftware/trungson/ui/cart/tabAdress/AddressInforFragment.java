package com.skynetsoftware.trungson.ui.cart.tabAdress;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddressInforFragment extends BaseFragment implements AddressInforContract.View {
    @BindView(R.id.edtName)
  public   EditText edtName;
    @BindView(R.id.edtAddress)
    public   EditText edtAddress;
    @BindView(R.id.edtCity)
    public  EditText edtCity;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tvPromo)
    TextView tvPromo;
    @BindView(R.id.edtCodePromo)
    public  EditText edtCodePromo;
    @BindView(R.id.edtPhone)
    public   EditText edtPhone;
    @BindView(R.id.edtNote)
    public   EditText edtNote;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.info)
    CardView info;
    private AddressInforContract.Presenter presenter;

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
        ButterKnife.bind(this, view);

    }

    @Override
    protected void initVariables() {
        presenter = new AddressInforPresenter(this);
        presenter.getInfor();
    }

    @Override
    public void onSuccessGetInfor(String name, String address, String city, String phone, String note, String promo, String avatar) {
        edtAddress.setText(address);
        edtCity.setText(city);
        edtName.setText(name);
        edtNote.setText(note);
        if (avatar != null && !avatar.isEmpty()) {
            Picasso.with(getContext()).load(avatar).into(circleImageView);
        }
        edtPhone.setText(phone);
    }

    @Override
    public void onSuccessUpdateInfor() {
        ((CartActivity) getActivity()).showToast("Đã cập nhận thông tin chuyển hàng", AppConstant.POSITIVE);
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {
        ((CartActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);


    }

    @Override
    public void onError(String message) {
        ((CartActivity) getActivity()).showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }


    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        super.onDestroyView();
    }
}
