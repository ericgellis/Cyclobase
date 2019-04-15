package com.mobithink.velo.carbon.core.ui;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import static com.mobithink.velo.carbon.core.ui.AbstractActivity.PERMISSION_REQUEST_CODE;

/**
 * Created by julienp on 20/12/2017.
 */

public abstract class AbstractFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public AbstractActivity getManypayActivity() {
        return (AbstractActivity) super.getActivity();
    }

    public void showProgressDialog() {
        if (isAdded()) {
            getManypayActivity().showProgressDialog();
        }
    }

    public void showProgressDialog(String message) {
        if (isAdded()) {
            getManypayActivity().showProgressDialog(message);
        }
    }

    public void hideProgressDialog() {
        if (isAdded()) {
            getManypayActivity().hideProgressDialog();
        }
    }

    public void showSuccessDialog(String message){
        if (isAdded()) {
            getManypayActivity().showSuccessDialog(message);
        }
    }

    protected List<String> getMissingPermissions() {
        return new ArrayList<>();
    }

    protected void askPermission(List<String> missingPermissions) {
        if (Build.VERSION.SDK_INT >= 23 && missingPermissions.size() > 0) {

            List<String> requestPermissions = new ArrayList<>(missingPermissions);

            if (requestPermissions.size() > 0) {
                requestPermissions(requestPermissions.toArray(new String[0]), PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void showError(String message) {
        if (isAdded()) {
            getManypayActivity().showError(message);
        }
    }


}
