package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains("mail") && sharedPreferences.contains("password") && sharedPreferences.contains("username")){
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();;
                } else {
                    startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                    finish();;
                }
            }
        }, 3000);

    }
}