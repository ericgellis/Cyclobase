package com.mobithink.velo.carbon.starter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.mobithink.velo.carbon.R;
import com.mobithink.velo.carbon.core.ui.AbstractActivity;
import com.mobithink.velo.carbon.home.ui.HomeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        startActivity(new Intent(StarterActivity.this, HomeActivity.class));
                    }
                    }, 1500);

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
