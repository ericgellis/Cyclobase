package com.mobithink.cyclobase.recyclerviewutils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;

import java.util.ArrayList;

public class MotsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> mDataSet;
    private final Context context;
    ArrayList<String> labelList;


    public MotsRecyclerViewAdapter(Context c ) {
        context=c;
        labelList= new ArrayList<>();
        labelList.add(c.getString(R.string.probleme));
        labelList.add(c.getString(R.string.event_type));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        EntryViewHolder viewHolder ;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_viewholder_mot, viewGroup, false);
        viewHolder= new EntryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        EntryViewHolder entryViewHolder= (EntryViewHolder) viewHolder;
        TextView itemTv = entryViewHolder.getTextView();
        //Pour les Labels le Style est BOLD est le text size plus grande
        if (labelList.contains(mDataSet.get(i))){
            itemTv.setTextAppearance(R.style.label_texview);
        }

        itemTv.setText(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        if (mDataSet==null || mDataSet.isEmpty())return 0;
        return mDataSet.size();
    }

    public void setData(ArrayList<String> data){
        mDataSet =new ArrayList<>();
        mDataSet=data;
    }
}
