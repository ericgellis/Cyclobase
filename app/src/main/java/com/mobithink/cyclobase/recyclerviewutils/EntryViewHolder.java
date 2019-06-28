package com.mobithink.cyclobase.recyclerviewutils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
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
