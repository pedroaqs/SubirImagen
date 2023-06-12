package com.example.hellowordsem9.servicios;

import com.example.hellowordsem9.models.Pokemon;
import com.example.hellowordsem9.models.Publicacion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PublicacionService {

    @GET("publicacion/")
    Call<List<Publicacion>> getLista();

    @GET("publicacion/{id}")
    Call<Publicacion> find(@Path("id") String id);

    @POST("publicacion")
    Call<Publicacion> create(@Body Publicacion publicacion);

    @PUT("publicacion/{id}")
    Call<Publicacion> actualizar(@Path("id") String id, @Body Publicacion publicacion);

    @DELETE("publicacion/{id}")
    Call<Void> delete(@Path("id") String id);

    @POST("image")
    Call<PublicacionService.ImageResponse> saveImage(@Body PublicacionService.ImageToSave image);

    class ImageResponse {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
    class ImageToSave {
        String base64Image;

        public ImageToSave(String base64Image) {
            this.base64Image = base64Image;
        }
    }
}
