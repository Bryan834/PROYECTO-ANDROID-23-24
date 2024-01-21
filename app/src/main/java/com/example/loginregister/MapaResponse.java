package com.example.loginregister;

public class MapaResponse {
    int idMapa;
    String mapa;
    public MapaResponse(int idMapa, String mapa){
        this.idMapa = idMapa;
        this.mapa = mapa;
    }

    public int getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(int idMapa) {
        this.idMapa = idMapa;
    }

    public String getMapaF() {
        return mapa;
    }

    public void setMapas(String mapa) {
        this.mapa = mapa;
    }
}
