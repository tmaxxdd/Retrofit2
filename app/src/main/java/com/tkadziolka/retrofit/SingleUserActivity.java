package com.tkadziolka.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tkadziolka.retrofit.data.SimpleCallback;
import com.tkadziolka.retrofit.data.controller.UserController;
import com.tkadziolka.retrofit.data.model.User;

public class SingleUserActivity extends AppCompatActivity implements View.OnClickListener, SimpleCallback<Boolean> {

    private UserController userController;
    private EditText name, job;
    private Button save;
    private int userId;
    private Boolean isInEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        userController = new UserController(this.getApplicationContext());

        userId = getIntent().getIntExtra("userId", 0);

        if (userId != 0) {
            isInEditMode = true;
        }

        name = findViewById(R.id.single_user_name);
        job = findViewById(R.id.single_user_job);
        save = findViewById(R.id.single_user_save);
        save.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isInEditMode)
            userController.get(userId, response -> {
                name.setText(response.getName());
                job.setText(response.getJob());
            });

    }

    @Override
    public void onClick(View view) {

        String nameValue = name.getText().toString();
        String jobValue = job.getText().toString();

        if (!nameValue.equals("") && !jobValue.equals("")) {
            if (isInEditMode)
                updateUser(userId, nameValue, jobValue);
            else
                addNewUser(nameValue, jobValue);
        } else {
            Toast.makeText(this, "Fulfill all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(@Nullable Boolean complete) {
        if (complete != null)
            if (complete)
                finish();
            else
                Toast.makeText(this, "Error during editing database!", Toast.LENGTH_SHORT).show();
    }

    private void addNewUser(String nameValue, String jobValue) {
        User newUser = new User(0, nameValue, jobValue);
        userController.add(newUser, this);
    }

    private void updateUser(int id, String nameValue, String jobValue) {
        User newUser = new User(id, nameValue, jobValue);
        userController.update(newUser, this);
    }

}
