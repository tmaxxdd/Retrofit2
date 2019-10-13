package com.tkadziolka.retrofit.views.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tkadziolka.retrofit.R;
import com.tkadziolka.retrofit.data.controller.AuthController;

public class AuthActivity extends AppCompatActivity {

    AuthController authController = new AuthController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authController.authorize("Example token");
    }
}
