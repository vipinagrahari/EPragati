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
import io.github.vipinagrahari.epragati.data.model.Transaction;
import io.github.vipinagrahari.epragati.ui.DividerItemDecoration;
import io.github.vipinagrahari.epragati.ui.adapter.TransactionAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {

    RecyclerView rvIncome, rvExpense;
    TransactionAdapter incomeAdapter, expenseAdapter;
    List<Transaction> income, expense;
    FloatingActionButton fabAddTransaction;
    int totalIncome, totalExpense;


    public BudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        rvIncome = (RecyclerView) view.findViewById(R.id.rv_income);
        rvExpense = (RecyclerView) view.findViewById(R.id.rv_expense);
        fabAddTransaction = (FloatingActionButton) view.findViewById(R.id.fab_add_transaction);
        income = new ArrayList<>();
        expense = new ArrayList<>();
        incomeAdapter = new TransactionAdapter(income);
        expenseAdapter = new TransactionAdapter(expense);

        rvIncome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvIncome.setAdapter(incomeAdapter);
        rvIncome.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        rvExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpense.setAdapter(expenseAdapter);
        rvExpense.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        getTransactions();

        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    void getTransactions() {
        Cursor c = getContext().getContentResolver().query(DbContract.TransactionEntry.CONTENT_URI, null, null, null, null);
        List<Transaction> income = new ArrayList<>();
        List<Transaction> expense = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setDescription(c.getString(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_DESCRIPTION)));
                transaction.setType(c.getString(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE)));
                transaction.setAmount(c.getInt(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT)));
                transaction.setDate(c.getInt(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_DATE)));

                if (transaction.getType().equalsIgnoreCase("income")) {
                    income.add(transaction);
                    totalIncome += transaction.getAmount();
                } else {
                    expense.add(transaction);
                    totalExpense += transaction.getAmount();
                }


            } while (c.moveToNext());
        }

        c.close();

        incomeAdapter = new TransactionAdapter(income);
        expenseAdapter = new TransactionAdapter(expense);
        rvIncome.setAdapter(incomeAdapter);
        rvExpense.setAdapter(expenseAdapter);

    }

}
