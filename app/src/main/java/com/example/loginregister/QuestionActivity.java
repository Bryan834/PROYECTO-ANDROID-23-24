package com.example.loginregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    TextInputEditText editTextDate, editTextMessage, editTextTitle;
    Button button_enviar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        editTextDate = findViewById(R.id.Date);
        editTextMessage = findViewById(R.id.message_question);
        editTextTitle = findViewById(R.id.title_question);
        button_enviar = findViewById(R.id.btn_enviarquestion);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        button_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextDate.getText().toString()) || TextUtils.isEmpty(editTextMessage.getText().toString()) || TextUtils.isEmpty(editTextTitle.getText().toString())) {
                    String message = "All inputs are needed";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                } else {
                    QuestionRequest questionRequest = new QuestionRequest();
                    questionRequest.setDate(editTextDate.getText().toString());
                    questionRequest.setMessage(editTextMessage.getText().toString());
                    questionRequest.setTitle(editTextTitle.getText().toString());
                    questionRequest.setSender(sharedPreferences.getString("username", null));
                    addPeticiones(questionRequest);
                }
            }
        });
    }

    public void addPeticiones(QuestionRequest questionRequest) {
        Call<Void> questionResponseCall = ApiClient.getService().addPeticiones(questionRequest);
        questionResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Successful";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                } else {
                    String message = "An error occurred";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("POLLO", "onFailure", t);
                String message = t.getLocalizedMessage();
                Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}