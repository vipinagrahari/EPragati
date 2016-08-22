package io.github.vipinagrahari.epragati.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.VideosAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment {


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
        loadData();
        return view;
    }

    private void loadData() {

        Call<JsonObject> getResources = ServiceGenerator.getInstance().
                getResources();

        getResources.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject data = response.body();
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


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


            }
        });

    }


}
