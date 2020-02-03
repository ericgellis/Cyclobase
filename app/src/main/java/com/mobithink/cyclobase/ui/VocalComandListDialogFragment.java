package com.mobithink.cyclobase.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mobithink.velo.carbon.R;
import com.mobithink.cyclobase.recyclerviewutils.MotsRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VocalComandListDialogFragment extends DialogFragment {

    @BindView(R.id.mots_recycler_view)
    RecyclerView mRecyclerView;

    private MotsRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);

        View thisView = inflater.inflate(R.layout.vocal_comand_list_dialog_fragment, parent, false);

        ButterKnife.bind(this, thisView);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }


        return thisView;
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MotsRecyclerViewAdapter(getActivity());
        mRecyclerViewAdapter.setData(getListMots());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private ArrayList<String> getListMots() {
        ArrayList<String>list= new ArrayList<>();

        list.add(getString(R.string.probleme));
        list.add(getResources().getString(R.string.warning_label1));
        list.add(getResources().getString(R.string.warning_label2));
        list.add(getResources().getString(R.string.warning_label3));
        list.add(getResources().getString(R.string.warning_label4));


        list.add(getString(R.string.event_type));
        list.add(getResources().getString(R.string.item_label1));
        list.add(getResources().getString(R.string.item_label2));
        list.add(getResources().getString(R.string.item_label3));
        list.add(getResources().getString(R.string.item_label4));
        list.add(getResources().getString(R.string.item_label5));
        list.add(getResources().getString(R.string.item_label6));



        return list;
    }
}
