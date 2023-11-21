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

        String idRecibido = getIntent().getExtras().getString("Id");
        String nombreRecibido = getIntent().getExtras().getString("Nombre");
        String rarezaRecibida = getIntent().getExtras().getString("Rareza");
        String dañoRecibido = getIntent().getExtras().getString("Daño");
        String precioRecibido = getIntent().getExtras().getString("Precio");

        id.setText("Name : " + idRecibido);
        nombre.setText("Description : " + nombreRecibido);
        rareza.setText("Number of Objects : " + rarezaRecibida);
        daño.setText("Price : " + dañoRecibido);
        precio.setText("Damage : " + precioRecibido);

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