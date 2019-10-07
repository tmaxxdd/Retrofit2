package com.tkadziolka.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tkadziolka.retrofit.data.SimpleCallback;
import com.tkadziolka.retrofit.data.controller.UserController;
import com.tkadziolka.retrofit.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements View.OnClickListener {

    private UserController userController = new UserController();
    private UsersAdapter usersAdapter;
    private List<User> users = new ArrayList<>();
    private Intent singleUserIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        singleUserIntent = new Intent(this, SingleUserActivity.class);

        RecyclerView usersList = findViewById(R.id.users_list);
        FloatingActionButton userAdd = findViewById(R.id.users_add);
        userAdd.setOnClickListener(this);
        usersAdapter = new UsersAdapter(users);

        usersAdapter.setOnItemClickListener(view -> {
            singleUserIntent.putExtra("userId", users.get(getViewPosition(view)).getId());
            startActivity(singleUserIntent);
        });

        usersAdapter.setOnDeleteClickListener(view -> {
            userController.delete(users.get(getViewPosition(view)).getId(), deleted -> {
                if (deleted != null)
                    if (deleted)
                        fetchUsers();
            });
        });

        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersList.setAdapter(usersAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        fetchUsers();
    }

    @Override
    public void onClick(View view) {
        startActivity(singleUserIntent);
    }

    private void fetchUsers() {
        userController.getAll(response -> {
            if (response != null) {
                users.clear();
                users.addAll(response);
                usersAdapter.notifyDataSetChanged();
            }
        });
    }

    private int getViewPosition(View view) {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }

}
