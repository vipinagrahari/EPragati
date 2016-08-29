package io.github.vipinagrahari.epragati.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import io.github.vipinagrahari.epragati.HttpAsyncLoader;
import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<JsonObject> {

    TextView tvMemberName, tvMemberAge, tvMemberAddress, tvMemberVoter, tvMemberAadhar,
            tvMemberRation, tvMemberHusband, tvMemberChildren, tvFamilyIncome, tvMemberEducation;


    public MyDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_details, container, false);

        tvMemberName = (TextView) view.findViewById(R.id.tv_member_name);
        tvMemberAge = (TextView) view.findViewById(R.id.tv_member_age);
        tvMemberAddress = (TextView) view.findViewById(R.id.tv_member_address);
        tvMemberVoter = (TextView) view.findViewById(R.id.tv_voter_id);
        tvMemberAadhar = (TextView) view.findViewById(R.id.tv_aadhar);
        tvMemberRation = (TextView) view.findViewById(R.id.tv_ration);
        tvMemberHusband = (TextView) view.findViewById(R.id.tv_member_husband);
        tvMemberChildren = (TextView) view.findViewById(R.id.tv_member_children);
        tvFamilyIncome = (TextView) view.findViewById(R.id.tv_family_income);
        tvMemberEducation = (TextView) view.findViewById(R.id.tv_member_education);

        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<JsonObject> onCreateLoader(int id, Bundle args) {

        Call<JsonObject> getMemberDetails = ServiceGenerator.getInstance().
                getMemberDetails();
        return new HttpAsyncLoader(getContext(), getMemberDetails);
    }

    @Override
    public void onLoadFinished(Loader<JsonObject> loader, JsonObject data) {
        if (null != data) {
            JsonObject personal = data.get("personal").getAsJsonObject();
            JsonObject children = personal.get("children").getAsJsonObject();

            tvMemberName.setText(data.get("name").getAsString());
            tvMemberAge.setText(personal.get("age").getAsString());
            tvMemberAddress.setText(personal.get("address").getAsString());
            tvMemberEducation.setText(personal.get("education").getAsString());
            tvMemberAadhar.setText(data.get("aadhar_id").getAsString());
            tvMemberVoter.setText(data.get("voter_id").getAsString());
            tvMemberRation.setText(data.get("ration_card").getAsString());
            tvMemberHusband.setText(personal.get("husband_name").getAsString());
            tvMemberChildren.setText("Boys - " + children.get("boys").getAsString() + "\n"
                    + "Girls - " + children.get("girls").getAsString());
            tvFamilyIncome.setText("10000"); // This parameter is not present in current server response hence hardcoded value
        } else
            Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onLoaderReset(Loader<JsonObject> loader) {
        //Do nothing

    }

}
