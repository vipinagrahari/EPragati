package io.github.vipinagrahari.epragati.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.github.vipinagrahari.epragati.HttpAsyncLoader;
import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import io.github.vipinagrahari.epragati.data.model.Meeting;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.MeetingsAdapter;
import retrofit2.Call;


public class GroupMeetingsFragment extends Fragment implements LoaderManager.LoaderCallbacks<JsonObject> {


    RecyclerView rvMeetings;
    MeetingsAdapter meetingsAdapter;
    List<Meeting> meetings;

    public GroupMeetingsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_meetings, container, false);
        rvMeetings = (RecyclerView) view.findViewById(R.id.rv_meetings);
        rvMeetings.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMeetings.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<JsonObject> onCreateLoader(int id, Bundle args) {
        Call<JsonObject> getMeetings = ServiceGenerator.getInstance().
                getGroupMeetings();
        return new HttpAsyncLoader(getContext(), getMeetings);
    }

    @Override
    public void onLoadFinished(Loader<JsonObject> loader, JsonObject response) {
        if (null != response) {
            JsonArray data = response.get("meetings").getAsJsonArray();
            Type listType = new TypeToken<List<Meeting>>() {
            }.getType();
            List<Meeting> meetings = new Gson().fromJson(data, listType);
            meetingsAdapter = new MeetingsAdapter(meetings);
            rvMeetings.setAdapter(meetingsAdapter);
        } else
            Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onLoaderReset(Loader<JsonObject> loader) {
        //Do nothing

    }


}
