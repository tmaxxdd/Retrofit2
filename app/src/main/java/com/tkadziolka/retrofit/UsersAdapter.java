package com.tkadziolka.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkadziolka.retrofit.data.model.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private static final String TAG = "UsersAdapter";

    private final List<User> items;
    private View.OnClickListener listener;
    private View.OnClickListener deleteListener;

    public UsersAdapter(List<User> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(items.get(position).getName()));
        holder.job.setText(items.get(position).getJob());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setOnItemClickListener(View.OnClickListener clickListener) {
        this.listener = clickListener;
    }

    void setOnDeleteClickListener(View.OnClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView job;
        Button deleteAction;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            job = itemView.findViewById(R.id.user_job);
            deleteAction = itemView.findViewById(R.id.user_delete);
            deleteAction.setOnClickListener(deleteListener);
            deleteAction.setTag(this);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }
    }

}
