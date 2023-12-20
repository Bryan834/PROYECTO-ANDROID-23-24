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

    @DELETE("usuario/deleteUser/{mail}&{password}")
    Call<Void> deleteUser(@Path("mail") String mail, @Path("password") String password);

    @PUT("usuario/actualizarUsuario/{mail}/{newPassword}/{newUsername}/{newName}/{newLastName}/{newMail}")
    Call<UsuarioResponse> updateUser(@Path("mail") String mail, @Path("newPassword") String newPassword, @Path("newUsername") String newUsername, @Path("newName") String newName, @Path("newLastName") String newLastName, @Path("newMail") String newMail);

    @GET("tienda/objetos")
    Call<List<Object>> getObjects();

    @PUT("tienda/comprarObjeto/{mail}")
    Call<Object> comprarObjeto(@Body Object object,@Path("mail") String mail);

}