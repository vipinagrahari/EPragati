package io.github.vipinagrahari.epragati.ui.activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.model.Transaction;
import io.github.vipinagrahari.epragati.ui.fragment.DatePickerFragment;

public class AddEditTransaction extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, AppCompatSpinner.OnItemSelectedListener {

    String tag, transactionType;
    TextInputEditText etDescription, etAmount, etDate;

    int transactionDate, transactionId = -1;
    AppCompatSpinner spinner;


    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_transaction);
        tag = getIntent().getStringExtra("tag");
        getSupportActionBar().setTitle(tag);


        etAmount = (TextInputEditText) findViewById(R.id.et_transaction_amount);
        etDescription = (TextInputEditText) findViewById(R.id.et_transaction_description);
        etDate = (TextInputEditText) findViewById(R.id.et_transaction_date);
        spinner = (AppCompatSpinner) findViewById(R.id.spinner_transaction_type);
        spinner.setOnItemSelectedListener(this);
        isEdit = getString(R.string.activity_edit_transaction).equals(tag);

        if (isEdit) {
            transactionId = getIntent().getIntExtra("transaction_id", -1);
            loadTransaction(transactionId);
        }

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "date_picker");
            }
        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        transactionDate = (int) (calendar.getTimeInMillis() / 1000);
        etDate.setText(formatDate(calendar));

    }


    public void save(View v) {

        String transactionDescription = etDescription.getText().toString().trim();
        String transactionAmount = etAmount.getText().toString().trim();

        String dateText = etDate.getText().toString().trim();


        if ("".equals(transactionAmount) || "".equals(transactionDescription) || "".equals(dateText) ||
                "".equals(transactionType)) {
            Toast.makeText(AddEditTransaction.this, getString(R.string.message_invalid_input), Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction();

        transaction.setDescription(transactionDescription);
        transaction.setAmount(Integer.parseInt(transactionAmount));
        transaction.setDate(transactionDate);
        transaction.setType(transactionType);

        if (isEdit) {
            int n = getContentResolver().update(DbContract.TransactionEntry.CONTENT_URI, transaction.getContentValues(), DbContract.TransactionEntry._ID + " =? ",
                    new String[]{transactionId + ""});
            if (n > 0)
                Toast.makeText(AddEditTransaction.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
        } else {

            getContentResolver().insert(DbContract.TransactionEntry.CONTENT_URI, transaction.getContentValues());
            Toast.makeText(AddEditTransaction.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
        finish();

    }

    public void cancel(View v) {
        finish();
    }


    public void loadTransaction(int transactionId) {
        Cursor c = getContentResolver().query(
                DbContract.TransactionEntry.CONTENT_URI, null, DbContract.TransactionEntry._ID + " =? ",
                new String[]{transactionId + ""}, null);
        if (c.moveToFirst()) {
            etDescription.setText(c.getString(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_DESCRIPTION)));
            transactionDate = c.getInt(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_DATE));
            etDate.setText(formatDate(transactionDate));
            etAmount.setText(Integer.toString(c.getInt(c.getColumnIndex
                    (DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT))));
            transactionType = c.getString(c.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE));
            spinner.setSelection(((ArrayAdapter) spinner.getAdapter()).getPosition(transactionType));

        } else
            Toast.makeText(AddEditTransaction.this, "Data might be corrupt", Toast.LENGTH_SHORT).show();
    }

    String formatDate(Calendar calendar) {
        return new SimpleDateFormat("MMM d, yyyy").format(calendar.getTime());
    }

    String formatDate(int timeInSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInSeconds * 1000L);
        return new SimpleDateFormat("MMM d, yyyy").format(calendar.getTime());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        transactionType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Default Transaction Type is Expense
        transactionType = getResources().getStringArray(R.array.transaction_type)[0];


    }
}
