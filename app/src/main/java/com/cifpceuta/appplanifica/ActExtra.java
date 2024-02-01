package com.cifpceuta.appplanifica;

public class ActExtra {
    private String titulo;
    private String grupo;
    private String fecha;

    public ActExtra(){}

    public ActExtra(String titulo, String grupo, String fecha) {
        this.titulo = titulo;
        this.grupo = grupo;
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
