package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 11/11/2016.
 */

public class Misiones_Compl {

    /**
     * CREATE TABLE misiones_completadas(
     id INT(10) PRIMARY KEY,
     titulo VARCHAR(20),
     descripcion TEXT,
     nivel_dificultad VARCHAR(10),
     distancia_total FLOAT (4,2),
     tiempo_total VARCHAR(10),
     id_misiongymkh INT(10),
     id_usuario INT(10),
     CONSTRAINT fk_id_misiongymkh FOREIGN KEY (id_misiongymkh) REFERENCES misiongymkh(id)
     CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
     );
     */

    public static final String TABLE = "misiones_completadas";


    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public static final String KEY_ID = "id";
        public static final String KEY_Titulo = "titulo";
        public static final String KEY_Descripcion = "descripcion";
        public static final String KEY_Nivel_dificultad = "nivel_dificultad";
        public static final String KEY_Distancia = "distacia_total";
        public static final String KEY_tiempo = "tiempo_total";
        public static final String KEY_ID_Misiongymkh = "id_misiongymkh";
        public static final String KEY_ID_Usuario = "id_usuario";

    }
    private int id;
    private String titulo;
    private String descripcion;
    private String nivel_dificultad;
    private String distancia;
    private String tiempo_total;
    private int id_misiongymkh;
    private String id_usuario;


    public Misiones_Compl(int id, String titulo, String descripcion, String nivel_dificultad, String distancia, String tiempo_total, int id_misiongymkh, String id_usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nivel_dificultad = nivel_dificultad;
        this.distancia = distancia;
        this.tiempo_total = tiempo_total;
        this.id_misiongymkh = id_misiongymkh;
        this.id_usuario = id_usuario;
    }

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

    public String getNivel_dificultad() {
        return nivel_dificultad;
    }

    public void setNivel_dificultad(String nivel_dificultad) {
        this.nivel_dificultad = nivel_dificultad;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTiempo_total() {
        return tiempo_total;
    }

    public void setTiempo_total(String tiempo_total) {
        this.tiempo_total = tiempo_total;
    }

    public int getId_misiongymkh() {
        return id_misiongymkh;
    }

    public void setId_misiongymkh(int id_misiongymkh) {
        this.id_misiongymkh = id_misiongymkh;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
