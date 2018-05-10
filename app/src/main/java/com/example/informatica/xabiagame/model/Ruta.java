package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 07/10/2016.
 */

public class Ruta {

    public static final String TABLE = "ruta";

    public int getRutId1() {
        return rutId1;
    }

    public void setRutId1(int rutId1) {
        this.rutId1 = rutId1;
    }

    public String getRuCoordlatitud1() {
        return ruCoordlatitud1;
    }

    public void setRuCoordlatitud1(String ruCoordlatitud1) {
        this.ruCoordlatitud1 = ruCoordlatitud1;
    }

    public String getRutCoordlongitud1() {
        return rutCoordlongitud1;
    }

    public void setRutCoordlongitud1(String rutCoordlongitud1) {
        this.rutCoordlongitud1 = rutCoordlongitud1;
    }

    public String getRutNombre1() {
        return rutNombre1;
    }

    public void setRutNombre1(String rutNombre1) {
        this.rutNombre1 = rutNombre1;
    }

    public String getRutDescripcion1() {
        return rutDescripcion1;
    }

    public void setRutDescripcion1(String rutDescripcion1) {
        this.rutDescripcion1 = rutDescripcion1;
    }


    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        // Labels Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_nombre = "nombre";
        public static final String KEY_descripcion = "descripcion";
        public static final String KEY_distancia = "distancia";
        public static final String KEY_tiempo_efectivo = "tiempo_aprox";
        public static final String KEY_nivel_dificultad = "nivel_dificultad";
        public static final String KEY_desnivel_acumulado_ascenso = "desnivel_acumulado_ascenso";
        public static final String KEY_id_admin_ajena = "id_admin";
    }

    private int id;
    private String nombre;
    private String descripcion;
    private double distancia;
    private String tiempo_efectivo;
    private String nivel_dificultad;
    private int desnivel_acumulado_ascenso;
    private int id_admin;

    //Ruta as Coordenadas Rutas trampa

    private int rutId1;
    private String ruCoordlatitud1;
    private String rutCoordlongitud1;
    private String rutNombre1;
    private String rutDescripcion1;

    public Ruta(int rutId1, String ruCoordlatitud1, String rutCoordlongitud1, String rutNombre1, String rutDescripcion1){
        this.rutId1 = rutId1;
        this.ruCoordlatitud1 = ruCoordlatitud1;
        this.rutCoordlongitud1 = rutCoordlongitud1;
        this.rutNombre1 = rutNombre1;
        this.rutDescripcion1 = rutDescripcion1;
    }

    public Ruta(){}

    public Ruta(int id, String nombre, String descripcion, double distancia, String tiempo_efectivo, String nivel_dificultad, int desnivel_acumulado_ascenso, int id_admin){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.tiempo_efectivo = tiempo_efectivo;
        this.nivel_dificultad = nivel_dificultad;
        this.desnivel_acumulado_ascenso = desnivel_acumulado_ascenso;
        this.id_admin = id_admin;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getTiempo_efectivo() {
        return tiempo_efectivo;
    }

    public void setTiempo_efectivo(String tiempo_efectivo) {
        this.tiempo_efectivo = tiempo_efectivo;
    }

    public String getNivel_dificultad() {
        return nivel_dificultad;
    }

    public void setNivel_dificultad(String nivel_dificultad) {
        this.nivel_dificultad = nivel_dificultad;
    }

    public int getDesnivel_acumulado_ascenso() {
        return desnivel_acumulado_ascenso;
    }

    public void setDesnivel_acumulado_ascenso(int desnivel_acumulado_ascenso) {
        this.desnivel_acumulado_ascenso = desnivel_acumulado_ascenso;
    }

    public String info(){
        String s;
        s = "ID: " + this.id + "Nombre: " + this.nombre + "\nDescripción: " + this.descripcion + "\nDistáncia: " + this.distancia + "\nTiempo total: " +
                this.tiempo_efectivo + "\nDesnivel Acumulado Ascenso: " + this.desnivel_acumulado_ascenso + "\nNivel Dificultad: " +
                this.nivel_dificultad;
        return s;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
