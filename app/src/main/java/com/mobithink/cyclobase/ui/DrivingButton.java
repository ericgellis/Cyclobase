package com.mobithink.cyclobase.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.mobithink.velo.carbon.R;

import androidx.appcompat.widget.AppCompatButton;

public class DrivingButton extends AppCompatButton {

    private String name;

    public DrivingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public DrivingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);


    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrivingButton, 0, 0);

        this.name = a.getString(R.styleable.DrivingButton_name);
    }


    public DrivingButton setname(String name){
        this.name = name;
        return this;
    }

    public  String getname(){
        return name;
    }

}
