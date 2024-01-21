package com.example.loginregister;

import android.content.ComponentName;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Button button_Tienda, button_perfil, button_faqs, button_insignias, button_mensajes, button_questions, button_inventory, button_unity;

    TextView username;
    SharedPreferences sharedPreferences;
    String data;
    boolean respuesta = false;
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
        button_inventory = findViewById(R.id.btn_inventario);
        button_unity = findViewById(R.id.btn_unity);

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

        button_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InventoryActivity.class));
            }
        });
        button_unity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                getMapa(1);

                i.setComponent(new ComponentName("com.pollopeta.UnityPlugin", "com.unity3d.player.UnityPlayerActivity"));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i.putExtra("input", data);
                        startActivity(i);
                    }
                }, 3000);
            }
        });
    }
    public void getMapa(int idMapa) {
        Call<MapaResponse> mapaResponseCall = ApiClient.getService().getMapa(idMapa);
        mapaResponseCall.enqueue(new Callback<MapaResponse>() {
            @Override
            public void onResponse(Call<MapaResponse> call, Response<MapaResponse> response) {
                if (response.isSuccessful()) {
                    String message = "Successful";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    data = response.body().getMapaF();
                    respuesta = true;
                } else {
                    String message = "An error occurred";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<MapaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}