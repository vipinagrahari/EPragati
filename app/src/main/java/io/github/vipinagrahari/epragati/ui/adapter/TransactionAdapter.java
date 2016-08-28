package io.github.vipinagrahari.epragati.ui.adapter;

/**
 * Created by vivek on 22/8/16.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.ui.activity.AddEditTransaction;


/**
 * Created by vivek on 22/8/16.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionView> {

    Cursor cursor;
    Context context;

    public TransactionAdapter(Cursor cursor) {
        this.cursor = cursor;
    }


    @Override
    public TransactionView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TransactionView(LayoutInflater.from(context).inflate(R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(TransactionView holder, int position) {
        cursor.moveToPosition(position);
        String description = cursor.getString(cursor.getColumnIndex(DbContract.TransactionEntry.COLUMN_DESCRIPTION));
        String amount = cursor.getString(cursor.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT));
        int transactionId = cursor.getInt(cursor.getColumnIndex(DbContract.TransactionEntry._ID));
        holder.transactionDescription.setText(description);
        holder.transactionAmount.setText(amount);
        holder.transactionId = transactionId;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (this.cursor == cursor) {
            return null;
        }
        Cursor oldCursor = this.cursor;
        this.cursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    class TransactionView extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView transactionDescription;
        TextView transactionAmount;
        int transactionId;

        public TransactionView(View itemView) {
            super(itemView);
            transactionDescription = (TextView) itemView.findViewById(R.id.text1);
            transactionAmount = (TextView) itemView.findViewById(R.id.text2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AddEditTransaction.class);
            intent.putExtra("tag", context.getString(R.string.activity_edit_transaction));
            intent.putExtra("transaction_id", transactionId);
            context.startActivity(intent);

        }
    }


}
