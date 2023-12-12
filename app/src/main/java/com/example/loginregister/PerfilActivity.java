package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    TextView editUsername, editMail, editPassword, editName, editLastname;

    SharedPreferences sharedPreferences;

    Button button_logout, button_delete, button_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        editUsername = findViewById(R.id.username);
        editName = findViewById(R.id.name);
        editLastname = findViewById(R.id.lastname);
        editMail = findViewById(R.id.mail);
        editPassword = findViewById(R.id.password);
        button_logout = findViewById(R.id.btn_LogOut);
        button_delete = findViewById(R.id.btn_borrar);
        button_update = findViewById(R.id.btn_actualizar);


        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        editUsername.setText("Username : " + sharedPreferences.getString("username",null));
        editMail.setText("Mail : " + sharedPreferences.getString("mail",null));
        editName.setText("Name : " + sharedPreferences.getString("name",null));
        editLastname.setText("Lastname : " + sharedPreferences.getString("lastName",null));
        editPassword.setText("Password : " + sharedPreferences.getString("password",null));

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(PerfilActivity.this,SplashScreenActivity.class));
                finish();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PerfilActivity.this,ActualizarPerfilActivity.class));
                finish();
            }
        });


        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog(view);
            }
        });

    }
    public void showConfirmationDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que quiere eliminar su cuenta? Todos sus datos serán eliminados.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarUsuario(sharedPreferences.getString("mail",null),sharedPreferences.getString("password",null));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarUsuario(String mail,String password) {
        Call<Void> deleteResponseCall = ApiClient.getService().deleteUser(mail, password);
        deleteResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Successful";
                    Toast.makeText(PerfilActivity.this, message, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(PerfilActivity.this,SplashScreenActivity.class));
                    finish();
                } else {
                    String message = "An error occurred";
                    Toast.makeText(PerfilActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "Error: " + t.getMessage(); // Agrega esta línea para obtener más detalles
                Log.e("Delete User", message); // Registra el mensaje de error en el log
                Toast.makeText(PerfilActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}