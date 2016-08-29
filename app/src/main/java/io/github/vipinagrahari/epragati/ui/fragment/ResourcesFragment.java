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
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.VideosAdapter;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment implements LoaderManager.LoaderCallbacks<JsonObject> {


    RecyclerView rvVideos;
    VideosAdapter videosAdapter;
    String tag;

    public ResourcesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tag = getArguments().get("TAG").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        rvVideos = (RecyclerView) view.findViewById(R.id.rv_education);
        rvVideos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideos.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<JsonObject> onCreateLoader(int id, Bundle args) {
        Call<JsonObject> getResources = ServiceGenerator.getInstance().
                getResources();

        return new HttpAsyncLoader(getContext(), getResources);
    }

    @Override
    public void onLoadFinished(Loader<JsonObject> loader, JsonObject data) {
        if (null != data) {
            JsonArray array = new JsonArray();
            if (tag.equalsIgnoreCase("EDUCATION")) {
                array = data.get("education").getAsJsonArray();
            } else if (tag.equalsIgnoreCase("SKILLS")) {
                array = data.get("skills").getAsJsonArray();
            }


            Type listType = new TypeToken<List<VideosAdapter.Video>>() {
            }.getType();
            List<VideosAdapter.Video> videos = new Gson().fromJson(array, listType);


            videosAdapter = new VideosAdapter(videos);
            rvVideos.setAdapter(videosAdapter);
        } else
            Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onLoaderReset(Loader<JsonObject> loader) {
        //Do nothing

    }

}
