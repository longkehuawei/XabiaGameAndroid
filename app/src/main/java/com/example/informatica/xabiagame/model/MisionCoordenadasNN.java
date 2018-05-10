package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 02/11/2016.
 */

public class MisionCoordenadasNN {

    public static final String TABLE = "misioncoordenadasnn";



    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public static final String KEY_ID_Mision = "id_mision";
        public static final String KEY_ID_COORENADASMision = "id_coordenadasmision";

    }

    private int id_mision;
    private int id_coordenadasmision;


    public MisionCoordenadasNN(int id_mision, int id_coordenadasmision) {
        this.id_mision = id_mision;
        this.id_coordenadasmision = id_coordenadasmision;
    }


    public int getId_mision() {
        return id_mision;
    }

    public void setId_mision(int id_mision) {
        this.id_mision = id_mision;
    }

    public int getId_coordenadasmision() {
        return id_coordenadasmision;
    }

    public void setId_coordenadasmision(int id_coordenadasmision) {
        this.id_coordenadasmision = id_coordenadasmision;
    }
}
