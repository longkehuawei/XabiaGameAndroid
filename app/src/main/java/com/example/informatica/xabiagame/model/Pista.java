package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 02/11/2016.
 */

public class Pista {

    public static final String TABLE = "pista";


    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public static final String KEY_ID = "id";
        public static final String KEY_PISTAUNO = "pista1";
        public static final String KEY_PISTADOS = "pista2";
        public static final String KEY_PISTATRES = "pista3";
        public static final String KEY_ID_MISION_GYMKH_COORD = "id_coordenadas_mision_gymk";
    }

    private int id;
    private String pista1;
    private String pista2;
    private String pista3;
    private int id_coordenadas_mision_gymk;


    public Pista(int id, String pista1, String pista2, String pista3, int id_coordenadas_mision_gymk) {
        this.id = id;
        this.pista1 = pista1;
        this.pista2 = pista2;
        this.pista3 = pista3;
        this.id_coordenadas_mision_gymk = id_coordenadas_mision_gymk;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getId_coordenadas_mision_gymk() {
        return id_coordenadas_mision_gymk;
    }

    public void setId_coordenadas_mision_gymk(int id_coordenadas_mision_gymk) {
        this.id_coordenadas_mision_gymk = id_coordenadas_mision_gymk;
    }
}
