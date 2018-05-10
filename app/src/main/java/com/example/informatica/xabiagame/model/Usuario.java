package com.example.informatica.xabiagame.model;

import android.provider.BaseColumns;

/**
 * Created by Informatica on 04/11/2016.
 */

public class Usuario {

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        //Static Table codes
        public static final String KEY_ID = "id";
        public static final String KEY_Nombre = "nombre";
        public static final String KEY_Apellidos = "apellidos";
        public static final String KEY_Email = "email";
        public static final String KEY_Dni = "dni";
        public static final String KEY_Contrasenya = "contraseña";
        public static final String KEY_UsuarioNombre = "usuario";
        public static final String KEY_Edad = "edad";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";
        public final static String TABLE = "usuario";

    }



    //JAVA Things
    public int id;
    public String nombre;
    public String apellidos;
    public String email;
    public String dni;
    public String contrasenya;
    public String usuarionombre;
    public int edad;


    public Usuario(int id, String nombre, String apellidos, String email, String dni, String contrasenya, String usuarionombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.dni = dni;
        this.contrasenya = contrasenya;
        this.usuarionombre = usuarionombre;
        this.edad = edad;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getUsuarionombre() {
        return usuarionombre;
    }

    public void setUsuarionombre(String usuarionombre) {
        this.usuarionombre = usuarionombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }



}
