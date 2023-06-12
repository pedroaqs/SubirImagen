package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hellowordsem9.models.Publicacion;
import com.example.hellowordsem9.servicios.PublicacionService;
import com.example.hellowordsem9.servicios.ServicesWebPokemon;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPublicacionActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    String urlImage = "";
    Publicacion publicacion = new Publicacion();
    ImageView itemImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion);
        EditText etNumero = findViewById(R.id.etNumero);
        Button btnCamara = findViewById(R.id.btnCamera);
        Button btnGaleria = findViewById(R.id.btnGaleria);
        Button btn = findViewById(R.id.btnCrear);
        itemImg = findViewById(R.id.image);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://647892f6362560649a2e0949.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PublicacionService services = retrofit.create(PublicacionService.class);


                publicacion.setDescripcion(String.valueOf(etNumero.getText()));
                publicacion.setImagen(String.valueOf("https://demo-upn.bit2bittest.com/"+urlImage));// Obtén el enlace de la imagen desde el EditText
                publicacion.setComentarios(new ArrayList<String>());
                Call<Publicacion> call = services.create(publicacion);


                call.enqueue(new Callback<Publicacion>() {
                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        if (response.isSuccessful()) {
                            // La imagen se agregó correctamente a MockAPI
                            onBackPressed();
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
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenCamara();
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                else {
                    String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 2000);
                }
            }
        });
    }

    private void onOpenCamara() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i("My app","El permiso de la cámara ya se ha otorgado, puedes realizar la acción deseada aquí");
            OpenCamera();
        } else {
            // El permiso de la cámara no se ha otorgado, solicítalo al usuario
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);
            onOpenCamara();
            Log.i("My app","No tienes permiso");
        }
    }

    private void OpenCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA );
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            // La foto se tomó correctamente, puedes manejar el resultado aquí
            // Por ejemplo, puedes obtener la imagen capturada utilizando data.getExtras().get("data")
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            // Convierte el bitmap a una cadena Base64
            String base64Image = convertBitmapToBase64(bitmap);
            // imprimirImagenEnLog(base64Image);
            Retrofit retrofit123 = new Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ServicesWebPokemon services = retrofit123.create(ServicesWebPokemon.class);

            Call<ServicesWebPokemon.ImageResponse> call = services.saveImage(new ServicesWebPokemon.ImageToSave(base64Image));

            call.enqueue(new Callback<ServicesWebPokemon.ImageResponse>() {
                @Override
                public void onResponse(Call<ServicesWebPokemon.ImageResponse> call, Response<ServicesWebPokemon.ImageResponse> response) {
                    Log.i("Respuesta activa", response.toString());
                    if (response.isSuccessful()) {
                        ServicesWebPokemon.ImageResponse imageResponse = response.body();
                        Log.i("Respues", response.toString());
                        urlImage = imageResponse.getUrl();
                        Log.i("Imagen url:", urlImage);
                        Picasso.get()
                                .load("https://demo-upn.bit2bittest.com/"+urlImage) // Carga la imagen desde el enlace proporcionado en el objeto Libro
                                .into(itemImg);

                    } else {

                        Log.e("Error cargar imagen",response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ServicesWebPokemon.ImageResponse> call, Throwable t) {
                    // Error de red o de la API
                    Log.i("Respuesta inactiva", "");
                }
            });

        }

    }
}