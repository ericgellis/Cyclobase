package com.mobithink.cyclobase.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

public class SuccessDialog extends Dialog {

    public ImageView imageView;
    public TextView textView;

    public String message;


    public SuccessDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setCancelable(true);
    }

    public SuccessDialog setText(String text) {
        message = text;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_dialog);

        textView = findViewById(R.id.success_text);
        textView.setText(message);

        findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessDialog.this.dismiss();
            }
        });

        setCanceledOnTouchOutside(true);
    }
}
