package com.skynetsoftware.trungson.ui.tabprofile.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.changepassword.ChangePasswordActivity;
import com.skynetsoftware.trungson.ui.updateInfor.UpdateInforActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;

public class ProfilesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ProfileContract.View {
    @BindView(R.id.imgBtn_back_toolbar)
    ImageView imgBtnBackToolbar;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.name_txt)
    TextView nameTxt;
    @BindView(R.id.phone_txt)
    TextView phoneTxt;
    @BindView(R.id.name_txt_1)
    TextView nameTxt1;
    @BindView(R.id.email_txt)
    TextView emailTxt;
    @BindView(R.id.phone_txt_1)
    TextView phoneTxt1;
    @BindView(R.id.pass_txt)
    TextView passTxt;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Profile profile;
    private ProgressDialogCustom dialogLoading;
    private ProfileContract.Presenter presenter;
    private int REQUEST_CODE_CHOOSE = 1000;


    @Override
    protected int initLayout() {
        return R.layout.activity_profiles;
    }

    @Override
    protected void initVariables() {
        presenter = new ProfilePresenter(this);
        dialogLoading = new ProgressDialogCustom(this);
        presenter.getInfor();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText(R.string.profile_title);
        swipe.setOnRefreshListener(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.imgBtn_back_toolbar)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onRefresh() {
        presenter.getInfor();
    }

    @Override
    public void onSuccessGetInfor() {
        setResult(RESULT_OK);
        profile = AppController.getInstance().getmProfileUser();
        nameTxt.setText(profile.getName());
        emailTxt.setText(profile.getEmail());
        phoneTxt.setText(profile.getPhone());
        passTxt.setText(profile.getPassword());
        nameTxt1.setText(profile.getName());
        phoneTxt1.setText(profile.getPhone());
        if (profile.getAvatar() != null && !profile.getAvatar().isEmpty())
            Picasso.with(this).load(profile.getAvatar()).into(profileImage);
        if ((profile.getFbid() != null && !profile.getFbid().isEmpty()) || (profile.getGgid() != null && !profile.getGgid().isEmpty())) {
            findViewById(R.id.pass_txt).setVisibility(View.GONE);
        }
    }

    private void choosePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
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
                            .start(ProfilesActivity.this, PhotoPicker.REQUEST_CODE);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> photos =
                    data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            File fileImage = new File(photos.get(0));
            if (!fileImage.exists()) {
                Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
                return;
            }
            CropImage.activity(Uri.fromFile(fileImage))
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                presenter.uploadAvatar(file);
                Picasso.with(this).load(file).into(profileImage);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            presenter.getInfor();
            return;
        }


    }


    @OnClick(R.id.profile_image)
    public void onViewUploadClicked() {
        choosePhoto();
    }

    @Override
    public void onSuccessUpdatedAvatar() {
        presenter.getInfor();
        setResult(RESULT_OK);
    }

    @Override
    public void onSuccessUpdate() {
        presenter.getInfor();
        Toast.makeText(this, R.string.update_infor_success_txt, Toast.LENGTH_SHORT).show();
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
        swipe.setRefreshing(false);
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }

    int PLACE_PICKER_REQUEST = 1;

    @OnClick({R.id.name_txt_1, R.id.pass_txt, R.id.email_txt, R.id.phone_txt_1})
    public void onViewClicked(View view) {
        if (profile == null) return;
        Intent intent = new Intent(ProfilesActivity.this, UpdateInforActivity.class);
        switch (view.getId()) {
            case R.id.name_txt_1:
                intent.putExtra("type", "name");
                intent.putExtra("text", profile.getName());
                startActivityForResult(intent, 1001);
                break;
            case R.id.email_txt:
                intent.putExtra("type", "email");
                intent.putExtra("text", profile.getEmail());
                startActivityForResult(intent, 1001);
                break;
            case R.id.phone_txt_1:
//                intent.putExtra("type", "phone");
//                intent.putExtra("text", profile.getPhone());
//                startActivityForResult(intent, 1001);
                break;
            case R.id.pass_txt:
                Intent i = new Intent(ProfilesActivity.this, ChangePasswordActivity.class);
                startActivityForResult(i, 1001);
                break;

        }
    }
}
