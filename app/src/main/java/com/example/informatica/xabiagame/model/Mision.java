package com.example.informatica.xabiagame.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Informatica on 14/10/2016.
 */

public class Mision {

    public static final String TABLE = "mision";



    private int id;
    private String titulo;
    private String descripcion;
    @SerializedName("niveldificultad")
    private String nivelDificultad;
    private String mision_completada;
    private int id_admin;

    //TRAMPA
    private String titulo1;
    private String pista1;
    private String pista2;
    private String pista3;
    private String nombre1;
    private String descripcion1;
    private String coord_latitudM;
    private String coord_longitudM;

    public Mision(String titulo, String pista1, String coord_latitudM, String coord_longitudM, String pista2, String pista3, String nombre1, String descripcion1){
        this.titulo = titulo;
        this.pista1 = pista1;
        this.coord_latitudM = coord_latitudM;
        this.coord_longitudM = coord_longitudM;
        this.pista2 = pista2;
        this.pista3 = pista3;
        this.nombre1 = nombre1;
        this.descripcion1 = descripcion1;
    }

    public Mision(){}

    public Mision(int id, String titulo, String descripcion, String nivelDificultad, String mision_completada, int id_admin){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nivelDificultad = nivelDificultad;
        this.mision_completada = mision_completada;
        this.id_admin = id_admin;
    }

    //Mision Datos Con pistas y coordenadas
    public String getPista1() {
        return pista1;
    }

    public void setPista1(String pista1) {
        this.pista1 = pista1;
    }

    public String getPista2() {
        return pista2;
    }

    public void setPista2(String pista2) {
        this.pista2 = pista2;
    }

    public String getPista3() {
        return pista3;
    }

    public void setPista3(String pista3) {
        this.pista3 = pista3;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getCoord_latitudM() {
        return coord_latitudM;
    }

    public void setCoord_latitudM(String coord_latitudM) {
        this.coord_latitudM = coord_latitudM;
    }

    public String getCoord_longitudM() {
        return coord_longitudM;
    }

    public void setCoord_longitudM(String coord_longitudM) {
        this.coord_longitudM = coord_longitudM;
    }

    public String getTitulo1() {
        return titulo1;
    }

    public void setTitulo1(String titulo1) {
        this.titulo1 = titulo1;
    }

    //Mision Datos Normal
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

     public String info(){
        String retorne = "";
            retorne = "Id: " + this.id + " Titulo: " + this.titulo + " Descripcion: " + this.descripcion + " Nivel Dificultad: " + this.nivelDificultad;
        return retorne;
    }

    public String getMision_completada() {
        return mision_completada;
    }

    public void setMision_completada(String mision_completada) {
        this.mision_completada = mision_completada;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
