package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.hellowordsem9.Adapters.PublicacionAdapter;
import com.example.hellowordsem9.Adapters.libroAdapter;
import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.models.Publicacion;
import com.example.hellowordsem9.servicios.PublicacionService;
import com.example.hellowordsem9.servicios.servicesWeb;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaPublicaciones extends AppCompatActivity {
    public RecyclerView rv;
    List<Publicacion> publicaciones= new ArrayList<>();
    Button crear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_publicaciones);
        crear = findViewById(R.id.btn_nueva);
        crear.setOnClickListener(view -> {
//            int sum = numbers.stream().reduce(0, Integer::sum);
//            tvSum.setText(String.valueOf(sum));

            Intent intent = new Intent(getApplicationContext(), CrearPublicacionActivity.class);
            startActivity(intent);

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Aquí puedes llamar al método que deseas ejecutar cuando la pantalla está en primer plano
        cargarData();
    }
    protected void cargarData(){
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("https://647892f6362560649a2e0949.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicacionService services = retrofit.create(PublicacionService.class);
        Call<List<Publicacion>> call=services.getLista();

        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (response.isSuccessful()){
                    publicaciones =response.body();

                    PublicacionAdapter adapter=new PublicacionAdapter(publicaciones);


                    rv= findViewById(R.id.rvPublicaciones);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);
                }else{
                    Log.e("Error", response.toString());
                    Log.i("asdasd12312", new Gson().toJson(response.body()));
                    Log.i("asd32", "Respuesta correcta");


                }
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Log.e("asd1234", "no hay conexion");
            }
        });

    }
}