package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    RecyclerView recycle;
    TextView dinero;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        recycle = findViewById(R.id.recycle);
        dinero = findViewById(R.id.dinero);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        dinero.setText("Bolivares : " + sharedPreferences.getInt("bolivares",0));

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getObjects();
        objectlistcall.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> objectList = response.body();
                    recycleadapter adapter = new recycleadapter(objectList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), objectList.get(recycle.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TiendaActivity.this, DetailTiendaActivity.class);
                            intent.putExtra("Id", objectList.get(recycle.getChildAdapterPosition(view)).getId());
                            intent.putExtra("Nombre", objectList.get(recycle.getChildAdapterPosition(view)).getNombre());
                            intent.putExtra("Rareza", objectList.get(recycle.getChildAdapterPosition(view)).getRareza());
                            intent.putExtra("Precio", objectList.get(recycle.getChildAdapterPosition(view)).getPrecio());
                            intent.putExtra("Daño", objectList.get(recycle.getChildAdapterPosition(view)).getDaño());
                            startActivity(intent);
                        }
                    });
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Object> list;
        private View.OnClickListener listener;
        public recycleadapter(List<Object> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            recycleadapter.MyViewHolder viewHolder = new recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.id.setText("    Id : " + list.get(position).getId());
            holder.nombre.setText("Nombre : " + list.get(position).getNombre());
            holder.rareza.setText("Rareza : " + list.get(position).getRareza());
            holder.daño.setText("Daño : " + list.get(position).getDaño());
            holder.precio.setText("Precio : " + list.get(position).getPrecio());



           /* Picasso.with(getApplicationContext())
                    .load(list.get(position).getImagen())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.image);*/
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
            listener.onClick(view);
        }
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView id, nombre, rareza, precio, daño;
            //ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                nombre = itemView.findViewById(R.id.nombre);
                precio = itemView.findViewById(R.id.precio);
                daño = itemView.findViewById(R.id.daño);
                rareza = itemView.findViewById(R.id.rareza);
                //image = itemView.findViewById(R.id.image);
            }
        }
    }

}