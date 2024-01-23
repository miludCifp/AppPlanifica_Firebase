package com.cifpceuta.appplanifica;

public class Tarea {
    private String idUser;
    private String tituloTarea;
    private String curso;
    private String modulo;
    private String descripcionTarea;
    private String fechaInicio;
    private String fechaFin;

    public Tarea() {}

    public Tarea(String idUser, String tituloTarea, String curso, String modulo, String descripcionTarea, String fechaInicio, String fechaFin) {
        this.idUser = idUser;
        this.tituloTarea = tituloTarea;
        this.curso = curso;
        this.modulo = modulo;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTituloTarea() {
        return tituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
