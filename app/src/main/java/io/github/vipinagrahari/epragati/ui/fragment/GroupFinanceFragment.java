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


public class GroupFinanceFragment extends Fragment implements LoaderManager.LoaderCallbacks<JsonObject> {

    TextView tvGroupFunds, tvGroupGivenLoan, tvGroupRepaidLoan, tvGroupRemainingLoan,
            tvBankDeposit, tvBankTakenLoan, tvBankRepaidLoan, tvBankRemainingLoan;


    public GroupFinanceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_finance, container, false);
        tvGroupFunds = (TextView) view.findViewById(R.id.tv_group_funds);
        tvGroupGivenLoan = (TextView) view.findViewById(R.id.tv_group_given_loan);
        tvGroupRepaidLoan = (TextView) view.findViewById(R.id.tv_group_repaid_loan);
        tvGroupRemainingLoan = (TextView) view.findViewById(R.id.tv_group_remaining_loan);
        tvBankDeposit = (TextView) view.findViewById(R.id.tv_bank_deposit);
        tvBankTakenLoan = (TextView) view.findViewById(R.id.tv_bank_taken_loan);
        tvBankRepaidLoan = (TextView) view.findViewById(R.id.tv_bank_repaid_loan);
        tvBankRemainingLoan = (TextView) view.findViewById(R.id.tv_bank_remaining_loan);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);

    }


    @Override
    public Loader<JsonObject> onCreateLoader(int id, Bundle args) {
        Call<JsonObject> getGroupFinance = ServiceGenerator.getInstance().getGroupFinance();
        return new HttpAsyncLoader(getContext(), getGroupFinance);
    }

    @Override
    public void onLoadFinished(Loader<JsonObject> loader, JsonObject data) {
        if (null != data) {
            JsonObject financial = data.get("financial").getAsJsonObject();
            JsonObject members = financial.get("members").getAsJsonObject();
            JsonObject bank = financial.get("bank").getAsJsonObject();
            tvGroupFunds.setText(financial.get("cash").getAsString());
            tvGroupGivenLoan.setText(members.get("loan_given").getAsString());
            tvGroupRepaidLoan.setText(members.get("loan_repaid").getAsString());
            tvGroupRemainingLoan.setText(members.get("loan_pending").getAsString());

            tvBankDeposit.setText(bank.get("deposit").getAsString());
            tvBankTakenLoan.setText(bank.get("loan_taken").getAsString());
            tvBankRemainingLoan.setText(bank.get("loan_pending").getAsString());
            tvBankRepaidLoan.setText(bank.get("loan_repaid").getAsString());
        } else
            Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onLoaderReset(Loader<JsonObject> loader) {
        //Do nothing

    }


}
