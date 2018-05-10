package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 11/11/2016.
 */

public class MisionesCompletadasUsuarioNN {

    /*
        CREATE TABLE misioncompletadausuario(
	id_usuario INT(10),
	id_mision_compl INT(10),
	PRIMARY KEY{id_usuario, id_mision_compl},
	CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	CONSTRAINT fk_id_mision_compl FOREIGN KEY (id_mision_compl) REFERENCES misiones_completadas(id)
	);
         */

    public static final String TABLE = "misioncompletadausuario";
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public static final String KEY_ID_USUARIO = "id_usuario";
        public static final String KEY_ID_MisionCompl = "id_misiongymkh";

    }


    private int id_usuario;
    private int id_misiongymkh;


    public MisionesCompletadasUsuarioNN(int id_usuario, int id_misiongymkh) {
        this.id_usuario = id_usuario;
        this.id_misiongymkh = id_misiongymkh;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_misiongymkh() {
        return id_misiongymkh;
    }

    public void setId_misiongymkh(int id_misiongymkh) {
        this.id_misiongymkh = id_misiongymkh;
    }
}
