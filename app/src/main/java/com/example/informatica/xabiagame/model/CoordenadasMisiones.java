package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 02/11/2016.
 */

public class CoordenadasMisiones {

    public static final String TABLE = "coordenadasmisiones";

    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public static final String KEY_ID = "id";
        public static final String KEY_CoordLat = "coord_latitud";
        public static final String KEY_CoordLong = "coord_longitud";
        public static final String KEY_Nombre = "nombre";
        public static final String KEY_Descripcion = "descripcion";

    }

    private int id;
    private String coordlatitud;
    private String coordlongitud;
    private String nombre;
    private String descripcion;

    public CoordenadasMisiones(int id, String coordlatitud, String coordlongitud, String nombre, String descripcion){
        this.id = id;
        this.coordlatitud = coordlatitud;
        this.coordlongitud = coordlongitud;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordlatitud() {
        return coordlatitud;
    }

    public void setCoordlatitud(String coordlatitud) {
        this.coordlatitud = coordlatitud;
    }

    public String getCoordlongitud() {
        return coordlongitud;
    }

    public void setCoordlongitud(String coordlongitud) {
        this.coordlongitud = coordlongitud;
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
}
