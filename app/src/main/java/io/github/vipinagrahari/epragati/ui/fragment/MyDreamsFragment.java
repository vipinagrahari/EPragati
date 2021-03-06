package io.github.vipinagrahari.epragati.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.model.Dream;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.activity.AddEditDreamActivity;
import io.github.vipinagrahari.epragati.ui.adapter.DreamsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDreamsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] DREAM_COLUMNS = {
            DbContract.DreamEntry.TABLE_NAME + "." + DbContract.DreamEntry._ID,
            DbContract.DreamEntry.COLUMN_TITLE,
            DbContract.DreamEntry.COLUMN_DREAM_DESCRIPTION,
            DbContract.DreamEntry.COLUMN_DREAM_DEADLINE,
            DbContract.DreamEntry.COLUMN_DREAM_COMPLETE
    };
    final int DREAM_LOADER = 1;
    RecyclerView rvDreams;
    DreamsAdapter dreamsAdapter;
    List<Dream> dreams;
    FloatingActionButton fabAddDream;
    Uri uri = DbContract.DreamEntry.CONTENT_URI;

    public MyDreamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dreams, container, false);
        rvDreams = (RecyclerView) view.findViewById(R.id.rv_dreams);
        fabAddDream = (FloatingActionButton) view.findViewById(R.id.fab_add_dream);
        dreams = new ArrayList<>();
        dreamsAdapter = new DreamsAdapter(null);

        rvDreams.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDreams.setAdapter(dreamsAdapter);
        rvDreams.addItemDecoration(new DividerItemDecoration(getActivity(), null));


        fabAddDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logAddDream();
                Intent intent = new Intent(getContext(), AddEditDreamActivity.class);
                intent.putExtra("tag", getString(R.string.activity_add_dream));

                startActivity(intent);
            }
        });

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DREAM_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != uri) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            String sortOrder = DbContract.DreamEntry.COLUMN_DREAM_DEADLINE + " ASC ";
            return new CursorLoader(
                    getActivity(),
                    uri,
                    DREAM_COLUMNS,
                    null,
                    null,
                    sortOrder
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dreamsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dreamsAdapter.swapCursor(null);
    }


    public void logAddDream() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Add Dream");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "feature_use");
        FirebaseAnalytics.getInstance(getContext()).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }
}
