package io.github.vipinagrahari.epragati.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.model.Meeting;
import io.github.vipinagrahari.epragati.ui.activity.MeetingActivity;

/**
 * Created by vivek on 21/8/16.
 */
public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingView> {

    List<Meeting> meetings;
    Context context;

    public MeetingsAdapter(List<Meeting> meetings) {
        this.meetings = meetings;
    }


    @Override
    public MeetingView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MeetingView(LayoutInflater.from(context).inflate(R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(MeetingView holder, int position) {
        holder.meetingDate.setText(meetings.get(position).getDate());
        holder.meetingLocation.setText(meetings.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }


    class MeetingView extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView meetingDate;
        TextView meetingLocation;

        public MeetingView(View itemView) {
            super(itemView);
            meetingDate = (TextView) itemView.findViewById(R.id.text1);
            meetingLocation = (TextView) itemView.findViewById(R.id.text2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MeetingActivity.class);
            intent.putExtra("meeting", meetings.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }


}
