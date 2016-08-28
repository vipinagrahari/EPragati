package io.github.vipinagrahari.epragati.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.db.DbContract;
import io.github.vipinagrahari.epragati.ui.activity.AddEditDreamActivity;


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
        return new DreamView(LayoutInflater.from(context).inflate(R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(DreamView holder, int position) {
        cursor.moveToPosition(position);
        String title = cursor.getString(cursor.getColumnIndex(DbContract.DreamEntry.COLUMN_TITLE));
        int deadline = cursor.getInt(cursor.getColumnIndex(DbContract.DreamEntry.COLUMN_DREAM_DEADLINE));
        int dreamId = cursor.getInt(cursor.getColumnIndex(DbContract.DreamEntry._ID));
        holder.dreamTitle.setText(title);
        holder.dreamId = dreamId;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(deadline * 1000L);
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        holder.dreamDeadline.setText(df.format(calendar.getTime()));

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

        int dreamId;
        TextView dreamTitle;
        TextView dreamDeadline;

        public DreamView(View itemView) {
            super(itemView);
            dreamTitle = (TextView) itemView.findViewById(R.id.text1);
            dreamDeadline = (TextView) itemView.findViewById(R.id.text2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(getAdapterPosition());
            Intent intent = new Intent(context, AddEditDreamActivity.class);
            intent.putExtra("tag", context.getString(R.string.activity_edit_dream));
            intent.putExtra("dream_id", dreamId);
            context.startActivity(intent);

        }
    }


}
