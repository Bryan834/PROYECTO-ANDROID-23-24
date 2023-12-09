package com.example.loginregister;

public class UsuarioResponse {

    private String id;
    private String email;
    private String username;
    private String password;
    private int bolivares;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBolivares() {
        return bolivares;
    }

    public void setBolivares(int bolivares) {
        this.bolivares = bolivares;
    }
}

