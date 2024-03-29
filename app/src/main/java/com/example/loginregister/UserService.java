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

    @POST("usuario/register")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);

    @POST("Peticiones/question")
    Call<Void> addPeticiones(@Body QuestionRequest questionRequest);
    @DELETE("usuario/deleteUser/{mail}&{password}")
    Call<Void> deleteUser(@Path("mail") String mail, @Path("password") String password);

    @PUT("usuario/actualizarUsuario/{mail}/{newPassword}/{newUsername}/{newName}/{newLastName}/{newMail}/{newBolivares}")
    Call<UsuarioResponse> updateUser(@Path("mail") String mail, @Path("newPassword") String newPassword, @Path("newUsername") String newUsername, @Path("newName") String newName, @Path("newLastName") String newLastName, @Path("newMail") String newMail, @Path("newBolivares") int newBolivares);

    @GET("tienda/objetos")
    Call<List<Object>> getObjects();

    @GET("usuario/backpack/{mail}")
    Call<List<Object>> getUserObjects(@Path("mail") String mail);

    @POST("tienda/comprarObjeto/{mail}")
    Call<Object> comprarObjeto(@Body Object object,@Path("mail") String mail);

    @GET("faqs/preguntas")
    Call<List<PreguntasRespuestas>> getPreguntas();

    @GET("usuario/listar_insignias_usuario/insignias/{username}")
    Call<List<Insignia>> getInsignias(@Path("username")String username);

    @GET("usuario/posts")
    Call<List<Mensaje>> getMensajes();

    @GET("usuario/mapas/{idMapa}")
    Call<MapaResponse> getMapa(@Path("idMapa")int idMapa);
}