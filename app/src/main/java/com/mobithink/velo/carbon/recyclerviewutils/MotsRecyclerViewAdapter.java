package com.mobithink.velo.carbon.recyclerviewutils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        labelList.add(c.getString(R.string.amenagement));
        labelList.add(c.getString(R.string.ressenti));
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

        //Pour le dernier entree on n'affiche pas la ligne de division
        int lastIndex = mDataSet.size()-1;
        if (i==lastIndex){
            entryViewHolder.getLine().setVisibility(View.GONE);
        }
        itemTv.setText(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        if (mDataSet==null || mDataSet.isEmpty())return 0;
        return mDataSet.size();
    }

    public void mDataSet(ArrayList<String> data){
        mDataSet =new ArrayList<>();
        mDataSet=data;
    }
}