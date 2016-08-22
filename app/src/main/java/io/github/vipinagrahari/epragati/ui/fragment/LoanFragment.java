package io.github.vipinagrahari.epragati.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.github.vipinagrahari.epragati.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends Fragment {
    TextInputEditText etPrincipal, etInterest, etDuration;

    TextView tvInstallment, tvInterest, tvTotal;


    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan, container, false);
        etPrincipal = (TextInputEditText) view.findViewById(R.id.et_principal);
        etInterest = (TextInputEditText) view.findViewById(R.id.et_interest_rate);
        etDuration = (TextInputEditText) view.findViewById(R.id.et_loan_duration);

        tvInstallment = (TextView) view.findViewById(R.id.text_installment);
        tvInterest = (TextView) view.findViewById(R.id.text_interest);
        tvTotal = (TextView) view.findViewById(R.id.text_total_payment);

        view.findViewById(R.id.btn_calculate_emi).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calculateEmi();
                    }
                });

        view.findViewById(R.id.btn_reset).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearTextFields();
                    }
                });

        return view;
    }

    private void calculateEmi() {
        try {

            int principal = Integer.parseInt(etPrincipal.getText().toString());
            double interestRate = Double.parseDouble(etInterest.getText().toString()) / 100;
            int duration = Integer.parseInt(etDuration.getText().toString());
            long emi = Math.round(principal * interestRate / (1 - Math.pow(1 + interestRate, -duration)));
            long total = emi * duration;
            long interest = total - principal;

            tvInstallment.setText(String.valueOf(emi));
            tvTotal.setText(String.valueOf(total));
            tvInterest.setText(String.valueOf(interest));
        } catch (NumberFormatException e) {


            Toast.makeText(getActivity(), getString(R.string.message_invalid_input), Toast.LENGTH_SHORT).show();
        }


    }

    private void clearTextFields() {
        tvInstallment.setText("--");
        tvTotal.setText("--");
        tvInterest.setText("--");
        etDuration.getText().clear();
        etInterest.getText().clear();
        etPrincipal.getText().clear();
        etPrincipal.requestFocus();

    }


}
