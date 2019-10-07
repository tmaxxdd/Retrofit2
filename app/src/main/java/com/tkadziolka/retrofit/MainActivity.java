package com.tkadziolka.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
