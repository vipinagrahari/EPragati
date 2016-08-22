package io.github.vipinagrahari.epragati.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupFinanceFragment extends Fragment {

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
        loadData();


        return view;
    }

    private void loadData() {

        Call<JsonObject> getGroupFinance = ServiceGenerator.getInstance().
                getGroupFinance();

        getGroupFinance.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject data = response.body().get("financial").getAsJsonObject();
                JsonObject members = data.get("members").getAsJsonObject();
                JsonObject bank = data.get("bank").getAsJsonObject();


                tvGroupFunds.setText(data.get("cash").getAsString());
                tvGroupGivenLoan.setText(members.get("loan_given").getAsString());
                tvGroupRepaidLoan.setText(members.get("loan_repaid").getAsString());
                tvGroupRemainingLoan.setText(members.get("loan_pending").getAsString());

                tvBankDeposit.setText(bank.get("deposit").getAsString());
                tvBankTakenLoan.setText(bank.get("loan_taken").getAsString());
                tvBankRemainingLoan.setText(bank.get("loan_pending").getAsString());
                tvBankRepaidLoan.setText(bank.get("loan_repaid").getAsString());


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(getContext(), getString(R.string.message_failed_to_load_data), Toast.LENGTH_SHORT).show();


            }
        });

    }


}
