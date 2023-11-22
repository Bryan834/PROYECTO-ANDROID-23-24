package com.example.loginregister;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailTiendaActivity extends AppCompatActivity {

    TextView id, nombre, rareza, daño, precio;

    //ImageView image;

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
        btn_compra = findViewById(R.id.btn_comprar);

        int idRecibido = getIntent().getExtras().getInt("Id");
        String nombreRecibido = getIntent().getExtras().getString("Nombre");
        String rarezaRecibida = getIntent().getExtras().getString("Rareza");
        int dañoRecibido = getIntent().getExtras().getInt("Daño");
        String precioRecibido = getIntent().getExtras().getString("Precio");

        id.setText("Id : " + idRecibido);
        nombre.setText("Nombre : " + nombreRecibido);
        rareza.setText("Rareza : " + rarezaRecibida);
        daño.setText("Daño : " + dañoRecibido);
        precio.setText("Precio : " + precioRecibido);

        /*Picasso.with(getApplicationContext())
                .load(imagereceived)
                .into(image);*/

        btn_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

}
}