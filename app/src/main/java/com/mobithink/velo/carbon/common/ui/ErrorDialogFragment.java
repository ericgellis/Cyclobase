package com.mobithink.velo.carbon.common.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorDialogFragment extends DialogFragment {

    boolean isLargeSize = false;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.message)
    TextView messageView;

    @OnClick(R.id.cancel_button)
            public void onClickCancelButton(){
        dismiss();
    }

    private String title;
    private String message;


    public ErrorDialogFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public ErrorDialogFragment setMessage(String message) {
        this.message = message;
        return this;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);

        View thisView = inflater.inflate(R.layout.simple_dialog_fragment, parent, false);

        ButterKnife.bind(this, thisView);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }

        if (!TextUtils.isEmpty(message)) {
            messageView.setText(message);
        }

        return thisView;
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}