package com.cifpceuta.appplanifica;

public class Alumno {
    private String nombre;
    private String apellidos;
    private String email;
    private String grupo;
    private String turno;
    private String contraseña;

    public Alumno(){}

    public Alumno(String nombre, String apellidos, String email, String grupo, String turno) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.grupo = grupo;
        this.turno = turno;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
