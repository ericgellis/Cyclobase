package com.mobithink.velo.carbon.home.ui;

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
import com.mobithink.velo.carbon.recyclerviewutils.MotsRecyclerViewAdapter;

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
        String[] array;
        list.add(getString(R.string.probleme));
        //Récupération de la liste d’évènements prédéfinis du type probeleme
        array = getResources().getStringArray(R.array.problemes_array);
        for (String s : array){
            list.add(s);
        }
        list.add(getString(R.string.amenagement));
        //Récupération de la liste d’évènements prédéfinis du type amenagement
        array = getResources().getStringArray(R.array.amenagement_array);
        for (String s : array){
            list.add(s);
        }
        list.add(getString(R.string.ressenti));
        //Récupération de la liste d’évènements prédéfinis du type ressenti
        array = getResources().getStringArray(R.array.ressenti_array);
        for (String s : array){
            list.add(s);
        }

        return list;
    }
}
