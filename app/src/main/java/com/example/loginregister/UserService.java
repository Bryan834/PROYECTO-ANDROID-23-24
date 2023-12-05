package com.example.loginregister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @POST("usuario/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("usuario/registrar")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);

    @GET("tienda/objetos")
    Call<List<Object>> getObjects();

}