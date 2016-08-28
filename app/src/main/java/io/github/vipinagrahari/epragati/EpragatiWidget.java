package io.github.vipinagrahari.epragati;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.data.db.DbHelper;
import io.github.vipinagrahari.epragati.ui.activity.FinancialActivity;

/**
 * Implementation of App Widget functionality.
 */
public class EpragatiWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String projection[] = {
                "SUM(" + DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT + ") AS SUM ",
                DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE
        };


        // Construct the RemoteViews object
        Intent intent = new Intent(context, FinancialActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        DbHelper dbHelper = new DbHelper(context);

        Cursor c = dbHelper.getReadableDatabase().query(
                DbContract.TransactionEntry.TABLE_NAME,
                projection,
                null,
                null,
                DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE, // GROUP BY
                null,
                DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE // Order By
        );
        int total_expense = 0, total_income = 0;
        if (c.moveToFirst()) total_expense = c.getInt(c.getColumnIndex("SUM"));
        if (c.moveToNext()) total_income = c.getInt(c.getColumnIndex("SUM"));
        c.close();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.epragati_widget);
        //  views.setOnClickPendingIntent(R.id.button, pendingIntent);

        views.setTextViewText(R.id.tv_expense_total, Integer.toString(total_expense));
        views.setTextViewText(R.id.tv_income_total, Integer.toString(total_income));
        views.setTextViewText(R.id.tv_total, Integer.toString(total_income - total_expense));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}

