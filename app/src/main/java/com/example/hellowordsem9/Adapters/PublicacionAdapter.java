package com.example.hellowordsem9.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellowordsem9.DetallePokemonActivity;
import com.example.hellowordsem9.R;
import com.example.hellowordsem9.models.Pokemon;
import com.example.hellowordsem9.models.Publicacion;
import com.example.hellowordsem9.servicios.PublicacionService;
import com.example.hellowordsem9.servicios.ServicesWebPokemon;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder>{
    List<Publicacion> publicaciones;

    public PublicacionAdapter(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
    @NonNull
    @Override
    public PublicacionAdapter.PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_publicacion, parent, false);
        PublicacionAdapter.PublicacionViewHolder vh = new PublicacionAdapter.PublicacionViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull PublicacionAdapter.PublicacionViewHolder holder, int position) {
        View vw = holder.itemView;

        Publicacion publicacion = publicaciones.get(position);
        TextView description = holder.itemView.findViewById(R.id.description);
        ImageView itemImg = holder.itemView.findViewById(R.id.image);


        description.setText(publicacion.getDescripcion());
        Picasso.get()
                .load(publicacion.getImagen()) // Carga la imagen desde el enlace proporcionado en el objeto Libro
                .into(itemImg);

        vw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new  Retrofit.Builder()
                        .baseUrl("https://647892f6362560649a2e0949.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PublicacionService services = retrofit.create(PublicacionService.class);
                Call<Publicacion> call=services.find(publicacion.getId());

                call.enqueue(new Callback<Publicacion>() {
                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        if (!response.isSuccessful()){
                            Log.e("asd1234", "error");
                        }else {

                            Log.i("asdasd12312", new Gson().toJson(response.body()));
                            Log.i("asd32", "Respuesta correcta por id");

                            Intent intent= new Intent(vw.getContext(), DetallePokemonActivity.class);


                            Log.i("asd32", "Respuesta correcta por id------------ ");
//
                            String publicacionJson = new Gson().toJson(publicacion);
                            intent.putExtra("Publicacion",publicacionJson);

                            vw.getContext().startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }
    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
