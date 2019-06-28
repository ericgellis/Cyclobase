package com.mobithink.cyclobase.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressDialog extends Dialog {

    Context context;

    @BindView(R.id.progress_wheel)
    public ImageView wheel;

    @BindView(R.id.loader_text)
    public TextView textView;

    String text;


    public ProgressDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        setCancelable(false);
    }

    public ProgressDialog setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);

        ButterKnife.bind(this);
    }

    private void setAnimation() {
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);

        if(wheel!= null){
            wheel.startAnimation(rotation);
        }
    }

    @Override
    public void show() {
        super.show();
        if(TextUtils.isEmpty(text)){
            textView.setVisibility(View.INVISIBLE);
        }else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        setAnimation();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }



}