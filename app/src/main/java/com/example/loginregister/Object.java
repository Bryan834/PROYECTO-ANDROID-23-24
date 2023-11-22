package com.example.loginregister;

public class Object {
    int id;
    int rareza;
    String nombre;
    int precio;
    int daño;

    public Object(int id, int rareza, String nombre, int precio, int daño) {
        this.id = id;
        this.nombre = nombre;
        this.rareza=rareza;
        this.precio = precio;
        this.daño=daño;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getRareza() {
        return rareza;
    }

    public void setRareza(int rareza) {
        this.rareza = rareza;
    }
    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getDaño() {
        return daño;
    }

    public void setDaño(int damage) {
        this.daño = daño;
    }

}
