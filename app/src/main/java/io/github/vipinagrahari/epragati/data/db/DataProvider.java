package io.github.vipinagrahari.epragati.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import io.github.vipinagrahari.epragati.data.db.DbContract.DreamEntry;
import io.github.vipinagrahari.epragati.data.db.DbContract.TransactionEntry;

/**
 * Created by vivek on 21/8/16.
 */
public class DataProvider extends ContentProvider {

    static final int DREAM = 1;
    static final int DREAM_WITH_ID = 2;
    static final int TRANSACTION = 3;
    static final int TRANSACTION_WITH_ID = 4;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, DbContract.PATH_DREAM, DREAM);
        uriMatcher.addURI(authority, DbContract.PATH_DREAM + "/#", DREAM_WITH_ID);
        uriMatcher.addURI(authority, DbContract.PATH_TRANSACTION, TRANSACTION);
        uriMatcher.addURI(authority, DbContract.PATH_TRANSACTION + "/#", TRANSACTION_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case DREAM: {

                retCursor = dbHelper.getReadableDatabase().query(
                        DreamEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case DREAM_WITH_ID: {
                retCursor = dbHelper.getReadableDatabase().query(
                        DreamEntry.TABLE_NAME,
                        projection,
                        DreamEntry._ID + " =? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TRANSACTION: {

                retCursor = dbHelper.getReadableDatabase().query(
                        TransactionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case TRANSACTION_WITH_ID: {
                retCursor = dbHelper.getReadableDatabase().query(
                        TransactionEntry.TABLE_NAME,
                        projection,
                        TransactionEntry._ID + " =? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);


        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {


        switch (sUriMatcher.match(uri)) {
            case DREAM:
                return DreamEntry.CONTENT_TYPE;
            case DREAM_WITH_ID:
                return DreamEntry.CONTENT_ITEM_TYPE;
            case TRANSACTION:
                return TransactionEntry.CONTENT_TYPE;
            case TRANSACTION_WITH_ID:
                return TransactionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case DREAM: {
                long _id = db.insert(DreamEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DreamEntry.buildDreamUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case TRANSACTION: {
                long _id = db.insert(TransactionEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = TransactionEntry.buildTransactionUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        if (selection == null) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case DREAM:
                rowsDeleted = db.delete(DreamEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRANSACTION:
                rowsDeleted = db.delete(TransactionEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case DREAM:
                rowsUpdated = db.update(DreamEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TRANSACTION:
                rowsUpdated = db.update(TransactionEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}