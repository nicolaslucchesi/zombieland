package com.rzg.zombieland.server.comunicacion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.rzg.zombieland.comunes.misc.ZombielandException;

/**
 * Prueba iniciar y cerrar un servicio de escucha.
 * @author nicolas
 *
 */
public class ServicioEscuchaTest {

    @Test
    public void testCerrarRapido() throws ZombielandException, InterruptedException {
        ServicioEscucha escucha = new ServicioEscucha();
        escucha.start();
        assertTrue(escucha.isAlive());
        escucha.cerrar();
        escucha.join();
        assertFalse(escucha.isAlive());
    }
    
    @Test
    public void testCerrarRetrasado() throws ZombielandException, InterruptedException {
        ServicioEscucha escucha = new ServicioEscucha();
        escucha.start();
        assertTrue(escucha.isAlive());
        Thread.sleep(100);
        escucha.cerrar();
        escucha.join();
        assertFalse(escucha.isAlive());
    }

    @Test
    public void testCerrarHiloEscuchaDesdeServicioEscucha() throws InterruptedException, UnknownHostException, IOException, ZombielandException {
        ServicioEscucha servicio = new ServicioEscucha();
        servicio.start();
        Socket socket = new Socket("localhost", 2048);
        assertTrue(servicio.isAlive());
        servicio.cerrar();
        servicio.join();
        assertFalse(servicio.isAlive());
        socket.close();
    }
}
