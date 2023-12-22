package com.example.loginregister;

public class PreguntasRespuestas {
    String pregunta;
    String respuesta;
    String url;

    public PreguntasRespuestas(String pregunta, String respuesta, String url) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.url=url;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
