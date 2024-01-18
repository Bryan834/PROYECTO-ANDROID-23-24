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

public class Mensajes_Sistema_Activity_Minimo2 extends AppCompatActivity {


    RecyclerView recycle;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        recycle = findViewById(R.id.recycle); // Asegúrate de tener el ID correcto en tu layout
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(llm);

        Call<List<Mensaje>> mensajelistcall = ApiClient.getService().getMensajes();
        mensajelistcall.enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful()) {
                    List<Mensaje> mensajeList = response.body();
                    Mensajes_Sistema_Activity_Minimo2.recycleadapter adapter = new Mensajes_Sistema_Activity_Minimo2.recycleadapter(Mensajes_Sistema_Activity_Minimo2.this,mensajeList);

                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
    class recycleadapter extends RecyclerView.Adapter<Mensajes_Sistema_Activity_Minimo2.recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Mensaje> list;
        private Context contexto;
        private View.OnClickListener listener;
        public recycleadapter(Context contexto, List<Mensaje> list){
            this.contexto = contexto;
            this.list = list;
        }

        @NonNull
        @Override
        public Mensajes_Sistema_Activity_Minimo2.recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            Mensajes_Sistema_Activity_Minimo2.recycleadapter.MyViewHolder viewHolder = new Mensajes_Sistema_Activity_Minimo2.recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Mensajes_Sistema_Activity_Minimo2.recycleadapter.MyViewHolder holder, int position) {
            holder.mensaje.setText(list.get(position).getContenido());
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
            TextView mensaje;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mensaje = itemView.findViewById(R.id.rareza);
            }
        }
    }

}
