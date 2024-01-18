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

import java.text.BreakIterator;
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

        Call<List<PreguntasRespuestas>> preguntaslistcall = ApiClient.getService().getPreguntas();

        preguntaslistcall.enqueue(new Callback<List<PreguntasRespuestas>>() {
            public void onResponse(Call<List<PreguntasRespuestas>> call, Response<List<PreguntasRespuestas>> response) {
                if (response.isSuccessful()) {
                    List<PreguntasRespuestas> preguntasRespuestasList = response.body();
                    FAQsActivity.recycleradapter adapter = new FAQsActivity.recycleradapter(FAQsActivity.this,preguntasRespuestasList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), preguntasRespuestasList.get(recycler.getChildAdapterPosition(view)).getPregunta(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FAQsActivity.this, DetailTiendaActivity.class);
                            intent.putExtra("Pregunta", preguntasRespuestasList.get(recycler.getChildAdapterPosition(view)).getPregunta());
                            intent.putExtra("Respuesta", preguntasRespuestasList.get(recycler.getChildAdapterPosition(view)).getRespuesta());
                            startActivity(intent);
                        }
                    });
                    recycler.setAdapter(adapter);
                }
            }

            public void onFailure(Call<List<PreguntasRespuestas>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
    class recycleradapter extends RecyclerView.Adapter<FAQsActivity.recycleradapter.MyViewHolder> implements View.OnClickListener{
        List<PreguntasRespuestas> list;
        private Context contexto;
        private View.OnClickListener listener;
        public recycleradapter(Context contexto, List<PreguntasRespuestas> list){
            this.contexto = contexto;
            this.list = list;
        }

        @Override
        public FAQsActivity.recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            FAQsActivity.recycleradapter.MyViewHolder viewHolder = new FAQsActivity.recycleradapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.pregunta.setText("Pregunta: " + list.get(position).getPregunta());
            holder.respuesta.setText("Respuesta: " + list.get(position).getRespuesta());
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

            TextView pregunta, respuesta;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                //pregunta = itemView.findViewById(R.id.pregunta);
                //respuesta = itemView.findViewById(R.id.respuesta);
            }
        }
    }
}