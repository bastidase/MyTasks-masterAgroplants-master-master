package com.jonmid.mytasks.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonmid.mytasks.Models.User;
import com.jonmid.mytasks.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> myUserList = new ArrayList<>();
    Context context;

    public UserAdapter(Context context, List<User> myUserList) {
        this.myUserList = myUserList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_picture, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextName.setText(myUserList.get(position).getName());
        holder.myTextEmail.setText(myUserList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return myUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextName;
        TextView myTextEmail;

        public ViewHolder(View item){
            super(item);
            myTextName = (TextView) item.findViewById(R.id.username_card);
            myTextEmail = (TextView) item.findViewById(R.id.time_card);
        }
    }
}
