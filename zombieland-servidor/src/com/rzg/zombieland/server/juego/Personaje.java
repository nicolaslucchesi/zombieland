package com.rzg.zombieland.server.juego;

import java.util.UUID;

import com.rzg.zombieland.comunes.misc.Movimiento;
import com.rzg.zombieland.server.meta.Jugador;

/**
 * Representa una entidad del tablero controlada por un jugador.
 * @author nicolas
 *
 */
public abstract class Personaje extends EntidadTablero {
    // ID �nico que la identifica.
    private UUID id;

    // Jugador que controla este personaje.
    private Jugador jugador;
    
    // Movimiento que ejecutar en el siguiente turno.
    private Movimiento siguienteMovimiento;
    
    // Tablero en el que el personaje est� inscrito.
    private Tablero tablero;
    
    public void setSiguienteMovimiento(Movimiento siguienteMovimiento) {
        this.siguienteMovimiento = siguienteMovimiento;
    }
    
    /**
     * Realiza el siguiente movimiento.
     */
    public void mover() {
        // TODO implementar.
    }
}
