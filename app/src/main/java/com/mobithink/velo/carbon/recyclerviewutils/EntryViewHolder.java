package com.mobithink.velo.carbon.recyclerviewutils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EntryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.mot_rv_tv)
    TextView textView;


    public EntryViewHolder(@NonNull View itemView) {
        super(itemView);
       ButterKnife.bind(this,itemView);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

}
