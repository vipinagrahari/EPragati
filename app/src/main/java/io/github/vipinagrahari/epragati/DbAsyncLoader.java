package io.github.vipinagrahari.epragati;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;

import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.db.DbHelper;

/**
 * Created by vivek on 28/8/16.
 */

public class DbAsyncLoader extends AsyncTaskLoader<Cursor> {
    final ForceLoadContentObserver mObserver;

    Uri uri;
    String[] projection;
    String selection;
    String[] selectionArgs;
    String groupBy;
    String having;
    String sortOrder;
    Cursor mCursor;

    CancellationSignal mCancellationSignal;

    public DbAsyncLoader(Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    public DbAsyncLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs,
                         String groupBy, String having, String sortOrder) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        this.uri = uri;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.groupBy = groupBy;
        this.sortOrder = sortOrder;
        this.having = having;
    }

    @Override
    public Cursor loadInBackground() {


        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            mCancellationSignal = new CancellationSignal();
        }
        try {
            Cursor cursor = new DbHelper(getContext()).getReadableDatabase().query(
                    DbContract.TransactionEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    groupBy,
                    having,
                    sortOrder
            );
            if (cursor != null) {
                try {
                    // Ensure the cursor window is filled.
                    cursor.getCount();
                    cursor.registerContentObserver(mObserver);
                } catch (RuntimeException ex) {
                    cursor.close();
                    throw ex;
                }
            }
            return cursor;
        } finally {
            synchronized (this) {
                mCancellationSignal = null;
            }
        }
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();

        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }
}
