package io.github.vipinagrahari.epragati.ui.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.model.Dream;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.DreamsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDreamsFragment extends Fragment {
    RecyclerView rvDreams;
    DreamsAdapter dreamsAdapter;
    List<Dream> dreams;
    FloatingActionButton fabAddDream;

    public MyDreamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dreams, container, false);
        rvDreams = (RecyclerView) view.findViewById(R.id.rv_dreams);
        fabAddDream = (FloatingActionButton) view.findViewById(R.id.fab_add_dream);
        dreams = new ArrayList<>();
        dreamsAdapter = new DreamsAdapter(dreams);

        rvDreams.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDreams.setAdapter(dreamsAdapter);
        rvDreams.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        getDreams();

        fabAddDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    void getDreams() {
        Cursor c = getContext().getContentResolver().query(DbContract.DreamEntry.CONTENT_URI, null, null, null, null);
        List<Dream> dreams = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Dream dream = new Dream();


                dream.setTitle(c.getString(c.getColumnIndex(DbContract.DreamEntry.COLUMN_TITLE)));
                dream.setDescription(c.getString(c.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_DESCRIPTION)));
                dream.setDeadline(c.getInt(c.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_DEADLINE)));
                dream.setComplete(Boolean.parseBoolean(c.getString(c.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_COMPLETE))));

                dreams.add(dream);


            } while (c.moveToNext());
        }

        c.close();

        dreamsAdapter = new DreamsAdapter(dreams);
        rvDreams.setAdapter(dreamsAdapter);


    }

}
