package com.example.loginregister;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsigniasActivityMinimo2 extends AppCompatActivity {

    RecyclerView recycle;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insignias_minimo2_jordi);
        recycle = findViewById(R.id.recycle);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);
        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        String user = sharedPreferences.getString("username", null);
        Call<List<Insignia>> insigniaListCall = ApiClient.getService().getInsignias(user);
        insigniaListCall.enqueue(new Callback<List<Insignia>>() {
            @Override
            public void onResponse(Call<List<Insignia>> call, Response<List<Insignia>> response) {
                if (response.isSuccessful()) {
                    List<Insignia> objectList = response.body();
                    recycleadapter adapter = new recycleadapter(InsigniasActivityMinimo2.this,objectList);
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Insignia>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Insignia> list;
        private Context context;
        private View.OnClickListener listener;
        public recycleadapter(Context context, List<Insignia> list){
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre.setText("Insignia : " + list.get(position).getName());
            Picasso.with(context)
                    .load(list.get(position).getAvatar())
                    .into(holder.image);


           /* Picasso.with(getApplicationContext())
                    .load(list.get(position).getUrl())
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
            TextView nombre;
            public ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombre);
                image = itemView.findViewById(R.id.avatar);
            }
        }
    }
}