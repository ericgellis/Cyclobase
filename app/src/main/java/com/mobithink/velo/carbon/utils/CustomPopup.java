package com.mobithink.velo.carbon.utils;

import android.app.Activity;
import android.content.Context;
import android.support.text.emoji.widget.EmojiButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

public class CustomPopup extends Dialog {

    //fields
    private Context context;

    //Constructeur
    public CustomPopup(Activity context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        setContentView(R.layout.angry);

    }

    public void build() {
        show();
    }

    public EmojiButton getEmojiButton(int id) {
        return findViewById(id);
    }

    public TextView getTextView(int id) {
        return findViewById(id);
    }
}