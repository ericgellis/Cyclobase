package com.mobithink.cyclobase.core.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mobithink.cyclobase.ui.ErrorDialogFragment;
import com.mobithink.cyclobase.ui.ProgressDialog;
import com.mobithink.cyclobase.ui.SuccessDialog;

public abstract class AbstractActivity extends AppCompatActivity {

    public static final String ERROR_DIALOG = "errorDialog";
    public static final String MESSAGE_DIALOG = "MessageDialog";

    public static final int AUTHENTIFICATION_NEEDED = 333;
    public static final int PERMISSION_REQUEST_CODE = 77;

    protected ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public void setTranslucideStatusBar() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    public void setFullscreenMode() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

    public void setDarkStatusIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void removeTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setLightStatusIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(0);
        }
    }

    protected void makeProgressDialog() {
        progressDialog = new ProgressDialog(this);
    }

    public void showProgressDialog() {
        progressDialog.setText("");
        progressDialog.show();
    }

    public void showProgressDialog(String message) {
        progressDialog.setText(message);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing() && !this.isDestroyed()) {
            progressDialog.dismiss();
        }
    }

    public void showSuccessDialog(String message){
        new SuccessDialog(this)
                .setText(message)
                .show();
    }

    public void showError(String message) {
        showError(null, message);
    }

    public void showError(String title, String message) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment()
                .setTitle(title)
                .setMessage(message);

        errorDialogFragment.show(getSupportFragmentManager(), ERROR_DIALOG);
    }

    protected void onAuthSuccess() { }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AUTHENTIFICATION_NEEDED) {
                onAuthSuccess();
            }
        }
    }
}