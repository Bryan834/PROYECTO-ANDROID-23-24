package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button_Tienda, button_perfil, button_faqs, button_insignias, button_mensajes, button_questions;

    TextView username;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_questions = findViewById(R.id.btn_questions);
        button_Tienda = findViewById(R.id.btn_tienda);
        button_perfil = findViewById(R.id.btn_perfil);
        username = findViewById(R.id.username);
        button_faqs = findViewById(R.id.faqsBtn);
        button_insignias = findViewById(R.id.btn_listar_insignias);
        button_mensajes = findViewById(R.id.btn_mensajes_sistema);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username",null));
        button_Tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TiendaActivity.class));
            }
        });

        button_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
            }
        });

        button_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FAQsActivity.class));
            }
        });

        button_insignias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsigniasActivityMinimo2.class));
            }
        });

        button_mensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Mensajes_Sistema_Activity_Minimo2.class));
            }
        });

        button_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });
    }
}