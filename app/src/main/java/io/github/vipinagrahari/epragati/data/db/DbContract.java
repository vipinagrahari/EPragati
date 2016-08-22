package io.github.vipinagrahari.epragati.data.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vivek on 21/8/16.
 */
public class DbContract {

    public static final String CONTENT_AUTHORITY = "io.github.vipinagrahari.epragati";

    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_DREAM = "dream";
    public static final String PATH_TRANSACTION = "transaction";


    public static final class DreamEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_DREAM).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DREAM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DREAM;
        public static final String TABLE_NAME = "dream";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DREAM_DESCRIPTION = "description";
        public static final String COLUMN_DREAM_DEADLINE = "deadline";
        public static final String COLUMN_DREAM_COMPLETE = "complete";


        public static Uri buildDreamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class TransactionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_TRANSACTION).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTION;
        public static final String TABLE_NAME = "budget";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TRANSACTION_TYPE = "transaction_type";
        public static final String COLUMN_TRANSACTION_AMOUNT = "amount";
        public static final String COLUMN_TRANSACTION_DATE = "transaction_date";


        public static Uri buildTransactionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
