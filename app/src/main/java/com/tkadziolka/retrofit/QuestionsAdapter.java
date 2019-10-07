package com.tkadziolka.retrofit;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkadziolka.retrofit.data.model.Question;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private static final String TAG = "QuestionsAdapter";

    private final List<Question> items;
    private View.OnClickListener listener;

    public QuestionsAdapter(List<Question> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(items.get(position).getQuestion_id()));
        holder.title.setText(items.get(position).getTitle());
        if (items.get(position).getIs_answered()) {
            holder.isAnswered.setText("Answered");
            holder.isAnswered.setTextColor(Color.GREEN);
        } else {
            holder.isAnswered.setText("Not answered");
            holder.isAnswered.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Number of items in list: " + items.size());
        return items.size();
    }

    void setOnItemClickListener(View.OnClickListener clickListener) {
        this.listener = clickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView isAnswered;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_id);
            title = itemView.findViewById(R.id.item_title);
            isAnswered = itemView.findViewById(R.id.item_answered);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }
    }

}
