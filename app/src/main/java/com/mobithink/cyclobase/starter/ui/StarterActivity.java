package com.mobithink.cyclobase.starter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.mobithink.cyclobase.managers.RetrofitManager;
import com.mobithink.cyclobase.starter.model.SignInPayload;
import com.mobithink.cyclobase.webservices.TechnicalService;
import com.mobithink.cyclobase.webservices.UserService;
import com.mobithink.velo.carbon.R;
import com.mobithink.cyclobase.core.ui.AbstractActivity;
import com.mobithink.cyclobase.ui.HomeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarterActivity extends AbstractActivity  {

    private static final int VIBRATION_DURATION = 120;

    private static final int ERROR_EMPTY_LOGIN = 1;
    private static final int ERROR_EMPTY_PASSWORD = 2;
    private static final int NO_ERROR = 0;

    @BindView(R.id.password_textinputlayout)
    TextInputLayout passwordTextInputLayout;

    @BindView(R.id.login_edittext)
    EditText loginEditText;

    @BindView(R.id.password_edittext)
    EditText passwordEditText;

    @OnClick(R.id.validate_button)
    public void onClicOnValidateButton(){

        clearHighlightErrors();

        int error = controlField();

        if (error == NO_ERROR) {
            signIn();
        } else {

            Vibrator vibrator = (Vibrator) Objects.requireNonNull(this).getSystemService(Context.VIBRATOR_SERVICE);

            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(VIBRATION_DURATION);
                }
            }
            highlightError(error);
        }
    }

    private void signIn() {

        showProgressDialog();

        UserService userService = RetrofitManager.build().create(UserService.class);

        Call<Void> call = userService.signIn(new SignInPayload(loginEditText.getText().toString(), passwordEditText.getText().toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        hideProgressDialog();
                        startActivity(new Intent(StarterActivity.this, HomeActivity.class));
                        break;
                    default:
                        hideProgressDialog();
                        showError("Accès non authorisé");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideProgressDialog();

                showError("Fail");
            }
        });
        /*new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                    }, 1500);*/

        //Todo fake
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.starter_activity);
        ButterKnife.bind(this);
        setDarkStatusIcon();

    }


    private void clearHighlightErrors() {
        loginEditText.setError(null);
        passwordEditText.setError(null);
    }

    private void highlightError(int errorCode) {
        switch (errorCode) {
            case ERROR_EMPTY_LOGIN:
                loginEditText.setError(getString(R.string.mandatory_field_message));
                break;
            case ERROR_EMPTY_PASSWORD:
                passwordEditText.setError(getString(R.string.mandatory_field_message));
                break;
        }
    }

    private int controlField() {
        if (loginEditText.getText().length() == 0) {
            return ERROR_EMPTY_LOGIN;
        }

        if (passwordEditText.getText().length() == 0) {
            return ERROR_EMPTY_PASSWORD;
        }

        return NO_ERROR;

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

}
