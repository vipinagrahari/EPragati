package io.github.vipinagrahari.epragati.ui.adapter;

/**
 * Created by vivek on 22/8/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.vipinagrahari.epragati.data.model.Transaction;


/**
 * Created by vivek on 22/8/16.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionView> {

    List<Transaction> transactions;
    Context context;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public TransactionView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TransactionView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(TransactionView holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.transactionDetail.setText(transaction.getDescription() + "\n" + transaction.getAmount() + "Rs");

    }

    @Override
    public int getItemCount() {
        return transactions.size();
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
//            intent.putExtra("dream",transactions.get(getAdapterPosition()));
//            context.startActivity(intent);

        }
    }


}
