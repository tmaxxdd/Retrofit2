package com.tkadziolka.retrofit.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tkadziolka.retrofit.R;
import com.tkadziolka.retrofit.views.auth.AuthActivity;
import com.tkadziolka.retrofit.views.stackoverflow.StackOverflowActivity;
import com.tkadziolka.retrofit.views.users.UsersActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.stackoverflow).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, StackOverflowActivity.class));
        });

        findViewById(R.id.auth).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        });

        findViewById(R.id.mockapi).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, UsersActivity.class));
        });

    }

}
