package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button button_login, button_register;
    ProgressBar spinner;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        button_login = findViewById(R.id.btn_login);
        button_register = findViewById(R.id.btn_register);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextEmail.getText().toString())){
                    String message = "All inputs are needed";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(editTextEmail.getText().toString());
                    loginRequest.setPassword(editTextPassword.getText().toString());
                    loginUser(loginRequest);
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


    }

    public void loginUser(LoginRequest loginRequest){
        Call<UsuarioResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("mail", loginRequest.getEmail());
                    editor.putString("password",loginRequest.getPassword());
                    editor.putString("username", response.body().getUsername());
                    editor.putString("name", response.body().getName());
                    editor.putString("lastName", response.body().getLastname());
                    editor.putInt("bolivares",response.body().getBolivares());
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    String message = "An error occurred";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
            }
        });

    }
}