package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hellowordsem9.Adapters.ComentarioAdapter;
import com.example.hellowordsem9.Adapters.PublicacionAdapter;
import com.example.hellowordsem9.models.Pokemon;
import com.example.hellowordsem9.models.Publicacion;
import com.example.hellowordsem9.servicios.PublicacionService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePublicacionActivity extends AppCompatActivity {
    Publicacion publicacion;
    ImageView itemImg;
    TextView tvDescripcion;
    EditText etcomentario;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_publicacion);

        String pokemonJson = getIntent().getStringExtra("Publicacion");
        publicacion = new Gson().fromJson(pokemonJson, Publicacion.class);

        tvDescripcion = findViewById(R.id.itemdescription);
        itemImg = findViewById(R.id.image);

        Button btn =findViewById(R.id.btnNuevoComentario);
        etcomentario = findViewById(R.id.etcomentario);

        rv= findViewById(R.id.rvComentarios);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);


        tvDescripcion.setText(publicacion.getDescripcion());
        Picasso.get()
                .load(publicacion.getImagen()) // Carga la imagen desde el enlace proporcionado en el objeto Libro
                .into(itemImg);
        Button btnNuevoComentario = findViewById(R.id.btnNuevoComentario);

        ComentarioAdapter adapter=new ComentarioAdapter(publicacion.getComentarios());

        rv.setAdapter(adapter);


        btn.setOnClickListener(view -> {
            if(!String.valueOf(etcomentario.getText()).equals("")) {
                agregarComentario();
            }
        });

    }

    public void agregarComentario() {
        List<String> comentarios = publicacion.getComentarios();
        comentarios.add(String.valueOf(etcomentario.getText()));

        publicacion.setComentarios(comentarios);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://647892f6362560649a2e0949.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicacionService services = retrofit.create(PublicacionService.class);


        Call<Publicacion> call = services.actualizar(publicacion.getId(),publicacion);


        call.enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if (response.isSuccessful()) {
                    // La imagen se agregó correctamente a MockAPI
                    publicacion = response.body();
                    tvDescripcion.setText(publicacion.getDescripcion());
                    Picasso.get()
                            .load(publicacion.getImagen()) // Carga la imagen desde el enlace proporcionado en el objeto Libro
                            .into(itemImg);
                    Button btnNuevoComentario = findViewById(R.id.btnNuevoComentario);

                    ComentarioAdapter adapter=new ComentarioAdapter(publicacion.getComentarios());

                    rv.setAdapter(adapter);
                    etcomentario.setText("");
                } else {
                    // Hubo un error al agregar la imagen a MockAPI
                    Log.e("Error", "Error al subir: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                // Error de red o de la API
            }
        });
    }
}