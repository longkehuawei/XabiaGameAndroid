package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 21/10/2016.
 */

public class Administrador {

    public static final String TABLE = "administrador";



    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        //Static Table codes
        public static final String KEY_ID = "id";
        public static final String KEY_Nombre = "nombre";
        public static final String KEY_Usuario = "usuario";
        public static final String KEY_Contrasenya = "contraseña";
        public static final String KEY_Apellido = "apellido";
        public static final String KEY_Email = "email";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";

    }

    private int id;
    private String nombre;
    private String usuario;
    private String apellido;
    private String email;
    private String contrasenya;

    public Administrador(){}

    public Administrador(int id, String nombre, String usuario, String apellido, String email, String contrasenya) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.apellido = apellido;
        this.email = email;
        this.contrasenya = contrasenya;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
