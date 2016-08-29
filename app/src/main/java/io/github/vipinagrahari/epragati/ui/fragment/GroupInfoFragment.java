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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.vipinagrahari.epragati.HttpAsyncLoader;
import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.MembersAdapter;
import retrofit2.Call;


public class GroupInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<JsonObject> {

    RecyclerView rvMembers;
    MembersAdapter membersAdapter;

    TextView tvGroupName, tvGroupFormationDate, tvGroupAim, tvTotalMembers;

    public GroupInfoFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_info, container, false);
        tvGroupName = (TextView) view.findViewById(R.id.tv_group_name);
        tvGroupAim = (TextView) view.findViewById(R.id.tv_group_aim);
        tvGroupFormationDate = (TextView) view.findViewById(R.id.tv_formation_date);
        tvTotalMembers = (TextView) view.findViewById(R.id.tv_total_members);


        rvMembers = (RecyclerView) view.findViewById(R.id.rv_members);
        rvMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMembers.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<JsonObject> onCreateLoader(int id, Bundle args) {
        Call<JsonObject> getGroupDetails = ServiceGenerator.getInstance().getGroupDetails();
        return new HttpAsyncLoader(getContext(), getGroupDetails);
    }

    @Override
    public void onLoadFinished(Loader<JsonObject> loader, JsonObject data) {
        if (null != data) {
            JsonArray members = data.get("members").getAsJsonArray();
            tvGroupName.setText(data.get("group_name").getAsString());
            tvGroupFormationDate.setText(data.get("formed_on").getAsString());
            tvGroupAim.setText(data.get("goal").getAsString());
            tvTotalMembers.setText(data.get("total_members").getAsString());

            membersAdapter = new MembersAdapter(members);
            rvMembers.setAdapter(membersAdapter);
        } else
            Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoaderReset(Loader<JsonObject> loader) {
        //Do nothing

    }
}
