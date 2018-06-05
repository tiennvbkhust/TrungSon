package com.skynetsoftware.trungson.ui.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.forgotpassword.ForgotPasswordActivity;
import com.skynetsoftware.trungson.ui.signup.SignUpActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.sromku.simple.fb.SimpleFacebook;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View{
    private static final int RC_SIGN_IN = 1000;
    @BindView(R.id.edtEmailPhone)
    EditText edtEmailPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    private ProgressDialogCustom dialogCustom;
    private LoginContract.Presenter presenter;
    private SimpleFacebook simpleFacebook;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected int initLayout() {
        return R.layout.acitivity_login;
    }

    @Override
    protected void initVariables() {
        dialogCustom = new ProgressDialogCustom(this);
        presenter = new LoginPresenter(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.tvForgotPassword, R.id.submit, R.id.submitFB, R.id.submitGoogle, R.id.tvLinkToSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

                break;
            case R.id.submit:
                presenter.login(edtEmailPhone.getText().toString(),edtPassword.getText().toString());
                break;
            case R.id.submitFB:
                presenter.loginViaFacebook(simpleFacebook);
                break;
            case R.id.submitGoogle:
                signInGG();
                break;
            case R.id.tvLinkToSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }
    private void signInGG() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onSuccessLogin(Profile profile) {
        startActivity(new Intent( LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSuccesLoginFacebook(Profile profile) {
        startActivity(new Intent( LoginActivity.this, MainActivity.class));
        finish();
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
        dialogCustom.showDialog();
    }

    @Override
    public void hiddenProgress() {
dialogCustom.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
        showToast(message,AppConstant.NEGATIVE);

    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        showToast(message,AppConstant.NEGATIVE);
    }

    @Override
    public void onErrorAuthorization() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.simpleFacebook != null) {
            this.simpleFacebook.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
        presenter.loginViaGoogle(account.getId(),account.getEmail(),account.getDisplayName());
        } catch (ApiException e) {
            showToast(e.getMessage(),AppConstant.NEGATIVE);
            Log.w("", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.simpleFacebook = SimpleFacebook.getInstance(this);
    }
}
