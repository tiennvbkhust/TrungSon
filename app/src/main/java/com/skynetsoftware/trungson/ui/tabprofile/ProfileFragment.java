package com.skynetsoftware.trungson.ui.tabprofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.splash.SplashActivity;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;

public class ProfileFragment extends BaseFragment {
    @BindView(R.id.imgAvatarProfile)
    CircleImageView imgAvatarProfile;
    @BindView(R.id.tvNameProfile)
    TextView tvNameProfile;
    @BindView(R.id.tvEmailProfile)
    TextView tvEmailProfile;
    Profile profile;

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initVariables() {
        profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            showDialogExpiredToken();
            return;
        }
        tvEmailProfile.setText(profile.getEmail());
        tvNameProfile.setText(profile.getName());
        if (profile.getAvatar() != null && !profile.getAvatar().isEmpty()) {
            Picasso.with(getContext()).load(profile.getAvatar()).fit().centerCrop().into(imgAvatarProfile);
        }
    }

    private void choosePhoto() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(getContext(), ProfileFragment.this, PhotoPicker.REQUEST_CODE);
                } else {

                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });


    }

    private boolean checkPermissionGranted() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults.length > 2 && grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionGranted();
                    return;
                } else {
                    choosePhoto();
                }
                return;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            ArrayList<String> photos =
                    data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            File fileImage = new File(photos.get(0));
            if (!fileImage.exists()) {
                ((MainActivity) getActivity()).showToast("File không tồn tại.", AppConstant.NEGATIVE);
                return;
            }
            CropImage.activity(Uri.fromFile(fileImage))
                    .start(getContext(),this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
//                presenter.uploadAvatar(file);
                Picasso.with(getContext()).load(file).into(imgAvatarProfile);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.imgAvatarProfile, R.id.layoutAddress, R.id.layoutInfor, R.id.layoutCart,  R.id.layoutContact, R.id.layoutLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAvatarProfile:
                choosePhoto();
                break;
            case R.id.layoutAddress:
                break;
            case R.id.layoutInfor:
                break;
            case R.id.layoutCart:
                break;

            case R.id.layoutContact:
                break;
            case R.id.layoutLogout:
                logout();
                break;
        }
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        AppController.getInstance().setmProfileUser(null);
        AppController.getInstance().getmSetting().remove(AppConstant.KEY_PROFILE);
        AppController.getInstance().getmSetting().remove(AppConstant.KEY_TOKEN);
        AppController.getInstance().setmProfileUser(null);
        startActivity(intent);
        getActivity().finishAffinity();
    }
}
