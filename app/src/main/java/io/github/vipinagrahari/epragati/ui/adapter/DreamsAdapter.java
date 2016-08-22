package io.github.vipinagrahari.epragati.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.vipinagrahari.epragati.data.model.Dream;


/**
 * Created by vivek on 22/8/16.
 */
public class DreamsAdapter extends RecyclerView.Adapter<DreamsAdapter.DreamView> {

    List<Dream> dreams;
    Context context;

    public DreamsAdapter(List<Dream> dreams) {
        this.dreams = dreams;
    }


    @Override
    public DreamView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DreamView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(DreamView holder, int position) {
        holder.dreamTitle.setText(dreams.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return dreams.size();
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
