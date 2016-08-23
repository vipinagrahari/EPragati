package io.github.vipinagrahari.epragati.ui.fragment;


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
public class BudgetFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String[] TRANSACTION_COLUMNS = {
            DbContract.TransactionEntry.TABLE_NAME + "." + DbContract.TransactionEntry._ID,
            DbContract.TransactionEntry.COLUMN_TRANSACTION_DATE,
            DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE,
            DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT,
            DbContract.TransactionEntry.COLUMN_DESCRIPTION
    };
    final int EXPENSE_LOADER = 1;
    final int INCOME_LOADER = 2;
    RecyclerView rvIncome, rvExpense;
    TransactionAdapter incomeAdapter, expenseAdapter;
    List<Transaction> income, expense;
    FloatingActionButton fabAddTransaction;
    int totalIncome, totalExpense;
    Uri uri = DbContract.TransactionEntry.CONTENT_URI;


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
        incomeAdapter = new TransactionAdapter(null);
        expenseAdapter = new TransactionAdapter(null);

        rvIncome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvIncome.setAdapter(incomeAdapter);
        rvIncome.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        rvExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpense.setAdapter(expenseAdapter);
        rvExpense.addItemDecoration(new DividerItemDecoration(getActivity(), null));


        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(INCOME_LOADER, null, this);
        getLoaderManager().initLoader(EXPENSE_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String selection = DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE + " = ?";
        if (null != uri) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            if (id == EXPENSE_LOADER) {
                return new CursorLoader(
                        getActivity(),
                        uri,
                        TRANSACTION_COLUMNS,
                        selection,
                        new String[]{"expense"},
                        null
                );
            } else if (id == INCOME_LOADER) {
                return new CursorLoader(
                        getActivity(),
                        uri,
                        TRANSACTION_COLUMNS,
                        selection,
                        new String[]{"income"},
                        null
                );
            }

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case INCOME_LOADER:
                incomeAdapter.swapCursor(data);
                break;
            case EXPENSE_LOADER:
                expenseAdapter.swapCursor(data);
                break;
            default://Do nothing
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        expenseAdapter.swapCursor(null);
        incomeAdapter.swapCursor(null);
    }

}
