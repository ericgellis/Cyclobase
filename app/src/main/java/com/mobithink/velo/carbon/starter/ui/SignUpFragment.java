package com.mobithink.velo.carbon.starter.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.mobithink.velo.carbon.R;
import com.mobithink.velo.carbon.core.ui.AbstractFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends AbstractFragment {

    public static final String TAG = SignUpFragment.class.getSimpleName();

    public static final int SCAN_REQUEST_CODE = 7;

    private static final int VIBRATION_DURATION = 120;

    private static final int ERROR_INCORRECT_EMAIL = 1;
    private static final int ERROR_TO_SHORT_PASSWORD = 2;
    private static final int ERROR_EMPTY_EMAIL = 3;
    private static final int ERROR_EMPTY_PASSWORD = 4;
    private static final int NO_ERROR = 0;

    @BindView(R.id.password_textinputlayout)
    TextInputLayout passwordTextInputLayout;

    @BindView(R.id.email_edittext)
    EditText emailEditText;

    @BindView(R.id.password_edittext)
    EditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }



    @OnClick(R.id.validate_button)
    public void onClicOnValidateButton(){

        clearHighlightErrors();

        int error = controlField();

        if (error == NO_ERROR) {
            signUp();
        } else {

            Vibrator vibrator = (Vibrator) Objects.requireNonNull(getContext()).getSystemService(Context.VIBRATOR_SERVICE);

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

    private int controlField() {
        if (emailEditText.getText().length() == 0) {
            return ERROR_EMPTY_EMAIL;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            return ERROR_INCORRECT_EMAIL;
        }

        if (passwordEditText.getText().length() == 0) {
            return ERROR_EMPTY_PASSWORD;
        }

        if (passwordEditText.getText().length() < 6) {
            return ERROR_TO_SHORT_PASSWORD;
        }
        return NO_ERROR;

    }

    private void highlightError(int errorCode) {
        switch (errorCode) {
            case ERROR_EMPTY_EMAIL:
                emailEditText.setError(getString(R.string.mandatory_field_message));
                break;
            case ERROR_EMPTY_PASSWORD:
                passwordEditText.setError(getString(R.string.mandatory_field_message));
                break;
            case ERROR_INCORRECT_EMAIL:
                emailEditText.setError(getString(R.string.email_incorrect_format_message));
                break;
            case ERROR_TO_SHORT_PASSWORD:
                passwordEditText.setError(getString(R.string.password_not_secured_message));
                break;
        }
    }

    private void clearHighlightErrors() {
        emailEditText.setError(null);
        passwordEditText.setError(null);
    }

    private void signUp() {
        //TODO
    }

}
