package com.example.informatica.xabiagame.utils;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "63343";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://proyectoxabiagame.esy.es";
    /**
     * URLs del Web Service
     */
    //Model Rutas
    public static final String INSERT = IP + "/insertUser.php";
    public static final String GETRUTAS = IP + "/getRutas.php";
    public static final String GETRUTAS_BY_ID = IP + "/getRutasById.php";
    public static final String GETRUTAS_CON_COORDENADAS_BY_ID = IP + "/getRutasByIdConCoordenadas.php";

    //Model Misiones
    public static final String GETMisiones = IP + "/getMisiones.php";
    public static final String GETMisiones_BY_ID = IP + "/getMisionesById.php";

    //Para Mision, lleva coordenadas y pistas
    public static final String GETPistas_BY_ID = IP + "/getMisionesByIdConPistas.php";

    //Insertar Mision Terminada
    public static final String INSERT_MISIONES_TERMINADAS = IP + "/insertarMisionCompletada.php";
    public static final String GET_MISIONES_COMPLETADAS = IP + "/getMisionesCompletadas.php";

    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";

}