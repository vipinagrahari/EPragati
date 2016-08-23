package io.github.vipinagrahari.epragati.ui.adapter;

/**
 * Created by vivek on 22/8/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import io.github.vipinagrahari.epragati.data.db.DbContract;


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
        return new TransactionView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(TransactionView holder, int position) {
        cursor.moveToPosition(position);
        String description = cursor.getString(cursor.getColumnIndex(DbContract.TransactionEntry.COLUMN_DESCRIPTION));
        String amount = cursor.getString(cursor.getColumnIndex(DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT));
        holder.transactionDetail.setText(description + "\n" + amount + "Rs");

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

        TextView transactionDetail;

        public TransactionView(View itemView) {
            super(itemView);
            transactionDetail = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "TO BE IMPLEMENTED " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(context,TransactionActivity.class);
//            intent.putExtra("transaction",transactions.get(getAdapterPosition()));
//            context.startActivity(intent);

        }
    }


}
