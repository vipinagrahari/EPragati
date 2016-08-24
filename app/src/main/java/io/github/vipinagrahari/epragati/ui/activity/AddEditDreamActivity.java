package io.github.vipinagrahari.epragati.ui.activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.db.DbContract.DreamEntry;
import io.github.vipinagrahari.epragati.data.model.Dream;
import io.github.vipinagrahari.epragati.ui.fragment.DatePickerFragment;

public class AddEditDreamActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String tag;
    TextInputEditText etDeadline, etTitle, etDescription;
    int dreamDeadline, dreamId = -1;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_dream);
        tag = getIntent().getStringExtra("tag");
        getSupportActionBar().setTitle(tag);
        etDeadline = (TextInputEditText) findViewById(R.id.et_dream_deadline);
        etTitle = (TextInputEditText) findViewById(R.id.et_title);
        etDescription = (TextInputEditText) findViewById(R.id.et_dream_description);
        isEdit = getString(R.string.activity_edit_dream).equals(tag);

        if (isEdit) {
            dreamId = getIntent().getIntExtra("dream_id", -1);
            loadDream(dreamId);
        }

        etDeadline.setOnClickListener(new View.OnClickListener() {
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
        dreamDeadline = (int) (calendar.getTimeInMillis() / 1000);
        etDeadline.setText(formatDate(calendar));

    }

    public void save(View v) {

        String dreamTitle = etTitle.getText().toString().trim();
        String dreamDescription = etDescription.getText().toString().trim();
        String deadLineText = etDeadline.getText().toString().trim();


        if ("".equals(dreamTitle) || "".equals(dreamDescription) || "".equals(deadLineText)) {
            Toast.makeText(AddEditDreamActivity.this, getString(R.string.message_invalid_input), Toast.LENGTH_SHORT).show();
            return;
        }

        Dream dream = new Dream();
        dream.setTitle(dreamTitle);
        dream.setDescription(dreamDescription);
        dream.setDeadline(dreamDeadline);
        dream.setComplete(false);

        if (isEdit) {
            int n = getContentResolver().update(DreamEntry.CONTENT_URI, dream.getContentValues(), DbContract.DreamEntry._ID + " =? ",
                    new String[]{dreamId + ""});
            if (n > 0)
                Toast.makeText(AddEditDreamActivity.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
        } else {

            getContentResolver().insert(DbContract.DreamEntry.CONTENT_URI, dream.getContentValues());
            Toast.makeText(AddEditDreamActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
        finish();

    }

    public void cancel(View v) {
        finish();
    }


    public void loadDream(int dreamId) {

        Cursor c = getContentResolver().query(
                DbContract.DreamEntry.CONTENT_URI, null, DbContract.DreamEntry._ID + " =? ",
                new String[]{dreamId + ""}, null);
        if (c.moveToFirst()) {
            etTitle.setText(c.getString(c.getColumnIndex(DbContract.DreamEntry.COLUMN_TITLE)));
            dreamDeadline = c.getInt(c.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_DEADLINE));
            etDeadline.setText(formatDate(dreamDeadline));
            etDescription.setText(c.getString(c.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_DESCRIPTION)));
        } else
            Toast.makeText(AddEditDreamActivity.this, "Data might be corrupt", Toast.LENGTH_SHORT).show();
    }

    String formatDate(Calendar calendar) {
        return new SimpleDateFormat("MMM d, yyyy").format(calendar.getTime());
    }

    String formatDate(int timeInSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInSeconds * 1000L);
        return new SimpleDateFormat("MMM d, yyyy").format(calendar.getTime());
    }


}
