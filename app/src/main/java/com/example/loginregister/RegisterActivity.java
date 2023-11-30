package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextUsername, editTextName, editTextLastname, editTextPasswordC;

    Button  button_register, button_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPasswordC = findViewById(R.id.passwordC);
        editTextUsername  = findViewById(R.id.username);
        editTextName = findViewById(R.id.name);
        editTextLastname = findViewById(R.id.lastName);
        button_return = findViewById(R.id.btn_ret);

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        button_register = findViewById(R.id.btn_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextPasswordC.getText().toString()) || TextUtils.isEmpty(editTextUsername.getText().toString()) || TextUtils.isEmpty(editTextName.getText().toString()) || TextUtils.isEmpty(editTextLastname.getText().toString())){
                    String message = "All inputs are needed";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }else if (! editTextPassword.getText().toString().equals(editTextPasswordC.getText().toString())){
                    String message = "Passwords are not the same";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(editTextEmail.getText().toString());
                    registerRequest.setPassword(editTextPassword.getText().toString());
                    registerRequest.setUsername(editTextUsername.getText().toString());
                    registerRequest.setName(editTextName.getText().toString());
                    registerRequest.setLastName(editTextLastname.getText().toString());
                    registerUser(registerRequest);}
            }
        });


    }

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                } else {
                    String message = "An error occurred";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}