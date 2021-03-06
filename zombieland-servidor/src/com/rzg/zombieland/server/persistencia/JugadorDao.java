package com.rzg.zombieland.server.persistencia;

import com.rzg.zombieland.server.meta.Jugador;

/**
 * Administra la persistencia de un jugador.
 * @author nicolas
 *
 */
public class JugadorDao extends Dao<Jugador, String> {
    public JugadorDao() {
        super(Jugador.class);
    }
}
