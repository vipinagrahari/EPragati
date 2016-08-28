package io.github.vipinagrahari.epragati.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.vipinagrahari.epragati.R;

/**
 * Created by vivek on 21/8/16.
 */
public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberView> {

    JsonArray members;

    public MembersAdapter(JsonArray members) {
        this.members = members;
    }


    @Override
    public MemberView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberView(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberView holder, int position) {
        JsonObject data = members.get(position).getAsJsonObject();
        holder.memberName.setText(data.get("name").getAsString());
        holder.memberContact.setText(data.get("contact").getAsString());

    }

    @Override
    public int getItemCount() {
        return members.size();
    }


    class MemberView extends RecyclerView.ViewHolder {

        TextView memberName;
        TextView memberContact;

        public MemberView(View itemView) {
            super(itemView);
            memberName = (TextView) itemView.findViewById(R.id.text1);
            memberContact = (TextView) itemView.findViewById(R.id.text2);
        }
    }

}