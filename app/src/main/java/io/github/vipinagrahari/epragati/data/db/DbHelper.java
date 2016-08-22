package io.github.vipinagrahari.epragati.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.vipinagrahari.epragati.data.db.DbContract.DreamEntry;
import io.github.vipinagrahari.epragati.data.db.DbContract.TransactionEntry;


/**
 * Created by vivek on 21/8/16.
 */
public class DbHelper extends SQLiteOpenHelper {


    static final String DATABASE_NAME = "epragati.db";
    private static final int DATABASE_VERSION = 2;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DATABASE ON CREATE");

        final String SQL_CREATE_DREAM_TABLE = "CREATE TABLE " + DreamEntry.TABLE_NAME + "(" +
                DreamEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DreamEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DreamEntry.COLUMN_DREAM_DESCRIPTION + " TEXT NOT NULL, " +
                DreamEntry.COLUMN_DREAM_DEADLINE + " INTEGER NOT NULL, " +
                DreamEntry.COLUMN_DREAM_COMPLETE + " INTEGER NOT NULL DEFAULT 0 );";

        final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TransactionEntry.TABLE_NAME + "(" +
                TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TransactionEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TransactionEntry.COLUMN_TRANSACTION_AMOUNT + " INTEGER NOT NULL, " +
                TransactionEntry.COLUMN_TRANSACTION_TYPE + " BOOLEAN NOT NULL, " +
                TransactionEntry.COLUMN_TRANSACTION_DATE + " INTEGER NOT NULL);";


        db.execSQL(SQL_CREATE_DREAM_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DreamEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
        onCreate(db);

    }


}
