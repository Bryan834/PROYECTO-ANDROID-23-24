package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePreguntasActivity extends AppCompatActivity {

    TextView pregunta, respuesta;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_preguntas);
        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuesta);

        String preguntaRecibida = getIntent().getExtras().getString("Pregunta");
        String respuestaRecibida = getIntent().getExtras().getString("Respuesta");

        pregunta.setText("Pregunta: " + preguntaRecibida);
        respuesta.setText("Respuesta: " + respuestaRecibida);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

    }

    public void consultarPregunta(PreguntasRespuestas preguntasRespuestas,String pregunta){
        Call<PreguntasRespuestas> PreguntasResponseCall = ApiClient.getService().consultarPregunta(preguntasRespuestas,pregunta);
        PreguntasResponseCall.enqueue(new Callback<PreguntasRespuestas>() {
            @Override
            public void onResponse(Call<PreguntasRespuestas> call, Response<PreguntasRespuestas> response) {
                if (response.isSuccessful()){
                    String message = "Pregunta registrada con éxito";
                    Toast.makeText(DetallePreguntasActivity.this,message,Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.commit();

                    startActivity(new Intent(DetallePreguntasActivity.this,TiendaActivity.class));
                    finish();;
                } else {
                    String message = "An error occurred";
                    Toast.makeText(DetallePreguntasActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PreguntasRespuestas> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetallePreguntasActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

}