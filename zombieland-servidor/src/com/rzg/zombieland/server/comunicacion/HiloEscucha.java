package com.rzg.zombieland.server.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.rzg.zombieland.comunes.comunicacion.Enviable;
import com.rzg.zombieland.comunes.misc.Log;
import com.rzg.zombieland.server.controlador.Controlador;
import com.rzg.zombieland.server.controlador.Controlador.ComandoDesconocidoException;

/**
 * Clase que se ocupa de la comunicaci�n con un cliente en particular.
 * 
 * @author nicolas
 *
 */
public class HiloEscucha extends Thread {

    // El socket sobre el que se escucha al cliente.
    private Socket socket;
    
    // Indica si el hilo se est� ejecutando.
    private boolean corriendo;
    
    /**
     * Construye un hilo de escucha.
     * 
     * @param socket
     *            - el socket con el que se escuchar� al cliente.
     */
    public HiloEscucha(Socket socket) {
        super("HiloEscucha: " + socket.getInetAddress());
        corriendo = true;
        Log.debug("Aceptando nueva conexi�n de " + socket.getInetAddress());
        this.socket = socket;
    }
    
    @Override
    public void run() {
        // Los dos warnings siguiente NO son de verdad, son un bug de Eclipse:
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=371614
        try (@SuppressWarnings("resource")
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            @SuppressWarnings("resource")
             BufferedReader in = new BufferedReader(new InputStreamReader(
                 socket.getInputStream()))) {
            while (corriendo) {
                int codigo = in.read();
                Log.debug("Recibiendo datos en HiloEscucha " + this + ". C�digo:");
                Log.debug(codigo);
                
                // Condici�n de fin del stream.
                if (codigo == -1)
                    return;
                
                try {
                    Controlador controlador = Controlador.crear(codigo);
                    Log.debug("Contenido:");
                    String contenido = in.readLine();
                    Log.debug(contenido);
                    out.println(controlador.procesar(contenido));
                    socket.close();
                } catch (ComandoDesconocidoException e) {
                    out.println(Enviable.LINEA_ERROR);
                }
            }   
        } catch (SocketException e) {
            // Esperada, se usa para cerrar el Thread.
            return;
        } catch (IOException e) {
            Log.error("Error en hilo de escucha " + getName() + ":");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cierra el hilo de escucha.
     */
    public void cerrar() {
        corriendo = false;
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

}
