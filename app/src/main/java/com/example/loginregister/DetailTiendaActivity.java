package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTiendaActivity extends AppCompatActivity {

    TextView id, nombre, rareza, daño, precio;

    TextView dinero;
    SharedPreferences sharedPreferences;
    Button btn_compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tienda);
        id = findViewById(R.id.Id);
        nombre = findViewById(R.id.Nombre);
        rareza = findViewById(R.id.Rareza);
        daño = findViewById(R.id.Daño);
        precio = findViewById(R.id.Precio);
        //image = findViewById(R.id.image);
        dinero = findViewById(R.id.dinero);

        btn_compra = findViewById(R.id.btn_comprar);

        int idRecibido = getIntent().getExtras().getInt("Id");
        String nombreRecibido = getIntent().getExtras().getString("Nombre");
        int rarezaRecibida = getIntent().getExtras().getInt("Rareza");
        int dañoRecibido = getIntent().getExtras().getInt("Daño");
        int precioRecibido = getIntent().getExtras().getInt("Precio");

        id.setText("Id : " + idRecibido);
        nombre.setText("Nombre : " + nombreRecibido);
        rareza.setText("Rareza : " + rarezaRecibida);
        daño.setText("Daño : " + dañoRecibido);
        precio.setText("Precio : " + precioRecibido);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        dinero.setText("bolivares :" + sharedPreferences.getInt("bolivares",0));

        /*Picasso.with(getApplicationContext())
                .load(imagereceived)
                .into(image);*/

        btn_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = new Object(idRecibido,rarezaRecibida,nombreRecibido,precioRecibido,dañoRecibido);
                comprarObjeto(object,sharedPreferences.getString("mail",null));
            }
        });

}

    public void comprarObjeto(Object object,String mail){
        Call<Object> BuyResponseCall = ApiClient.getService().comprarObjeto(object,mail);
        BuyResponseCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()){
                    String message = "Compra completada con éxito";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("bolivares",sharedPreferences.getInt("bolivares",0) - response.body().getPrecio());
                    editor.commit();

                    startActivity(new Intent(DetailTiendaActivity.this,TiendaActivity.class));
                    finish();;
                } else {
                    String message = "An error occurred";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

}