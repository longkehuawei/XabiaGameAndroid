package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 02/11/2016.
 */

public class RutaCoordenadasNN {

    public static final String TABLE = "rutacoordenadasnn";


    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        // Labels Table Columns names
        public static final String KEY_ID_RUTA = "id_ruta";
        public static final String KEY_ID_COORENADASDRUTAS = "id_coordenadasrutas";
    }

    private int id_ruta;
    private int id_coordenadasrutas;


    public RutaCoordenadasNN(int id_ruta, int id_coordenadasrutas) {
        this.id_ruta = id_ruta;
        this.id_coordenadasrutas = id_coordenadasrutas;
    }


    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public int getId_coordenadasrutas() {
        return id_coordenadasrutas;
    }

    public void setId_coordenadasrutas(int id_coordenadasrutas) {
        this.id_coordenadasrutas = id_coordenadasrutas;
    }
}
