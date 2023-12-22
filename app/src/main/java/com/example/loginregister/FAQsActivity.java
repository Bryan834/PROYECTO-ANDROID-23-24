package com.example.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.content.SharedPreferences;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQsActivity extends AppCompatActivity {

    RecyclerView recycler;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        recycler = findViewById(R.id.faqsRecyclerView);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getObjects();

        objectlistcall.enqueue(new Callback<List<Object>>() {
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> objectList = response.body();
                    FAQsActivity.recycleradapter adapter = new FAQsActivity.recycleradapter(FAQsActivity.this,objectList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), objectList.get(recycler.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FAQsActivity.this, DetailTiendaActivity.class);
                            intent.putExtra("Id", objectList.get(recycler.getChildAdapterPosition(view)).getId());
                            intent.putExtra("Nombre", objectList.get(recycler.getChildAdapterPosition(view)).getNombre());
                            intent.putExtra("Rareza", objectList.get(recycler.getChildAdapterPosition(view)).getRareza());
                            intent.putExtra("Precio", objectList.get(recycler.getChildAdapterPosition(view)).getPrecio());
                            intent.putExtra("Daño", objectList.get(recycler.getChildAdapterPosition(view)).getDaño());
                            startActivity(intent);
                        }
                    });
                    recycler.setAdapter(adapter);
                }
            }

            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
    class recycleradapter extends RecyclerView.Adapter<FAQsActivity.recycleradapter.MyViewHolder> implements View.OnClickListener{
        List<Object> list;
        private Context contexto;
        private View.OnClickListener listener;
        public recycleradapter(Context contexto, List<Object> list){
            this.contexto = contexto;
            this.list = list;
        }

        @NonNull
        @Override
        public FAQsActivity.recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            FAQsActivity.recycleradapter.MyViewHolder viewHolder = new FAQsActivity.recycleradapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        public void onBindViewHolder(@NonNull TiendaActivity.recycleadapter.MyViewHolder holder, int position) {
            holder.id.setText("    Id : " + list.get(position).getId());
            holder.nombre.setText("Nombre : " + list.get(position).getNombre());
            holder.rareza.setText("Rareza : " + list.get(position).getRareza());
            holder.daño.setText("Daño : " + list.get(position).getDaño());
            holder.precio.setText("Precio : " + list.get(position).getPrecio());
            Picasso.with(contexto)
                    .load(list.get(position).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.image);
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
            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                nombre = itemView.findViewById(R.id.nombre);
                precio = itemView.findViewById(R.id.precio);
                daño = itemView.findViewById(R.id.daño);
                rareza = itemView.findViewById(R.id.rareza);
                image = itemView.findViewById(R.id.image);
            }
        }
    }
}