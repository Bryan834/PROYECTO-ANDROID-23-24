package com.example.loginregister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @POST("usuario/login")
    Call<UsuarioResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("usuario/registrar")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);

    @GET("tienda/objetos")
    Call<List<Object>> getObjects();

    @PUT("tienda/comprarObjeto/{Mail}")
    Call<Object> comprarObjeto(@Body Object object,@Path("Mail") String mail);
    @DELETE("deleteUser/{mail}/{password}")
    Call<Void> deleteUser(@Path("mail") String mail, @Path("password") String password);

}