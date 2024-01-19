package com.example.loginregister;

import android.content.Context;
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

public class InventoryActivity extends AppCompatActivity {

    RecyclerView recycle;
    TextView titulo;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        recycle = findViewById(R.id.recycle);
        titulo = findViewById(R.id.titulo);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String mail = sharedPreferences.getString("mail", null);

        titulo.setText("Inventario de " + sharedPreferences.getString("username",null));

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getUserObjects(mail);
        objectlistcall.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> objectList = response.body();
                    recycleadapter adapter = new recycleadapter(InventoryActivity.this,objectList);
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
        private Context contexto;
        private View.OnClickListener listener;
        public recycleadapter(Context contexto, List<Object> list){
            this.contexto = contexto;
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
            holder.danyo.setText("Daño : " + list.get(position).getDamage());
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
            TextView id, nombre, rareza, precio, danyo;
            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                nombre = itemView.findViewById(R.id.nombre);
                precio = itemView.findViewById(R.id.precio);
                danyo = itemView.findViewById(R.id.danyo);
                rareza = itemView.findViewById(R.id.rareza);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}