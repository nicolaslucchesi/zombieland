package com.rzg.zombieland.server.controlador;

import com.google.gson.Gson;
import com.rzg.zombieland.comunes.comunicacion.POJORegistro;
import com.rzg.zombieland.comunes.comunicacion.RespuestaRegistro;
import com.rzg.zombieland.comunes.controlador.Controlador;
import com.rzg.zombieland.comunes.misc.ZombielandException;
import com.rzg.zombieland.server.meta.Jugador;
import com.rzg.zombieland.server.persistencia.JugadorDao;

public class ControladorRegistro extends Controlador {

    @Override
    public String procesar(String linea) {
        Gson gson = new Gson();
        POJORegistro registro = gson.fromJson(linea, POJORegistro.class);
        JugadorDao dao = new JugadorDao();
        try {
            Jugador jugador = new Jugador(registro);
            Jugador existente = dao.getObjeto(jugador.getNombre());
            if (existente != null)
                throw new ZombielandException("El usuario ya existe");
            dao.guardarObjeto(jugador);
            return gson.toJson(new RespuestaRegistro(true));
        } catch (ZombielandException e) {
            return gson.toJson(new RespuestaRegistro(false));
        } finally {
            dao.cerrarSesion();
        }
    }

}
