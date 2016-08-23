package io.github.vipinagrahari.epragati.ui.adapter;

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
public class DreamsAdapter extends RecyclerView.Adapter<DreamsAdapter.DreamView> {

    Cursor cursor;
    Context context;

    public DreamsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }


    @Override
    public DreamView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DreamView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(DreamView holder, int position) {
        cursor.moveToPosition(position);
        String title = cursor.getString(cursor.getColumnIndex(DbContract.DreamEntry.COLUMN_TITLE));
        holder.dreamTitle.setText(title);
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


    class DreamView extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView dreamTitle;

        public DreamView(View itemView) {
            super(itemView);
            dreamTitle = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "TO BE IMPLEMENTED " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(context,DreamActivity.class);
//            intent.putExtra("dream",dreams.get(getAdapterPosition()));
//            context.startActivity(intent);

        }
    }


}
