package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarPerfilActivity extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextPassword, editTextName, editTextLastname, editTextMail;
    Button button_save;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextName = findViewById(R.id.name);
        editTextLastname = findViewById(R.id.lastname);
        editTextMail = findViewById(R.id.mail);
        button_save = findViewById(R.id.btn_save);



        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String mail = sharedPreferences.getString("mail", null);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editTextUsername.getText()) &&
                        !TextUtils.isEmpty(editTextPassword.getText()) &&
                        !TextUtils.isEmpty(editTextName.getText()) &&
                        !TextUtils.isEmpty(editTextLastname.getText()) &&
                        !TextUtils.isEmpty(editTextMail.getText())) {
                    String newUsername = editTextUsername.getText().toString();
                    String newPassword = editTextPassword.getText().toString();
                    String newName = editTextName.getText().toString();
                    String newLastName = editTextLastname.getText().toString();
                    String newMail = editTextMail.getText().toString();

                    updateUser(mail, newPassword, newUsername, newName, newLastName, newMail);
                } else {
                    Toast.makeText(ActualizarPerfilActivity.this, "Algunos elementos de la vista son nulos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void updateUser(String mail, String newPassword, String newUsername, String newName, String newLastName, String newMail){
        Call<UsuarioResponse> updateResponseCall = ApiClient.getService().updateUser(mail, newPassword, newUsername, newName, newLastName, newMail);
        updateResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(ActualizarPerfilActivity.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Verificar y actualizar el correo electrónico si no está vacío
                    if (!TextUtils.isEmpty(response.body().getEmail())) {
                        editor.putString("mail", response.body().getEmail());
                    }

                    // Verificar y actualizar la contraseña si no está vacía
                    if (!TextUtils.isEmpty(response.body().getPassword())) {
                        editor.putString("password", response.body().getPassword());
                    }

                    // Verificar y actualizar el nombre de usuario si no está vacío
                    if (!TextUtils.isEmpty(response.body().getUsername())) {
                        editor.putString("username", response.body().getUsername());
                    }

                    // Verificar y actualizar el nombre si no está vacío
                    if (!TextUtils.isEmpty(response.body().getName())) {
                        editor.putString("name", response.body().getName());
                    }

                    // Verificar y actualizar el apellido si no está vacío
                    if (!TextUtils.isEmpty(response.body().getLastname())) {
                        editor.putString("lastName", response.body().getLastname());
                    }


                    editor.apply();

                    startActivity(new Intent(ActualizarPerfilActivity.this, PerfilActivity.class));
                    finish();
                } else {
                    String message = "An error occurred";
                    Toast.makeText(ActualizarPerfilActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ActualizarPerfilActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }


}