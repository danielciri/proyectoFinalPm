package com.danielcirilo.frasescelebres.interfaces;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.Models.Categoria;
import com.danielcirilo.frasescelebres.Models.Frase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAPIFrase {


    @GET("autor/{id}")
    public Call<Autor>find(@Path("id")Integer id);

    @GET("autor/all")
    public Call<ArrayList<Autor>> getAutores();

    @GET("categoria/all")
    public Call<ArrayList<Categoria> >getCategorias();

    @GET("frase/autor/{id}")
    public Call<ArrayList<Frase>> fraseAutores(@Path("id") Integer id);

    @POST("autor/add")
    Call<Boolean> addAutor(@Body Autor autor);

    @POST("categoria/add")
    Call<Boolean> addCategoria(@Body Categoria categoria);


    @GET("frase/all")
    Call<ArrayList<Frase>> getFrases();


    @GET("frase/dia")
    public Call<Frase> fraseProgramada();

    @POST("frase/add")
    Call<Boolean> addFrase(@Body Frase frase);

    @POST("frase/addValues")
    @FormUrlEncoded
    Call<Boolean> addFraseValues(@Field("texto") String texto,
                                 @Field("fechaProgramada") String fechaProgramada,
                                 @Field("idAutor") int idAutor,
                                 @Field("idCategoria")int idCategoria);

}
