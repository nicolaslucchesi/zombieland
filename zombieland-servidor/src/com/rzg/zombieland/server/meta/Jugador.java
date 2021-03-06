package com.rzg.zombieland.server.meta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.rzg.zombieland.comunes.comunicacion.POJORegistro;
import com.rzg.zombieland.comunes.misc.Avatar;
import com.rzg.zombieland.comunes.misc.ZombielandException;

/**
 * Modela al usuario del cliente de Zombieland.
 * 
 * @author nicolas
 *
 */
@Entity
public class Jugador {
    /**
     * Verifica nombre y clave del jugador, y devuelve un objeto jugador si logr�
     * iniciar sesi�n. 
     * @param nombre
     * @param clave
     * @return
     */
    public static Jugador iniciarSesion(String nombre, String clave) {
        // TODO implementar.
        return null;
    }
    
    // Nombre de usuario.
    @Id
    private String nombre;

    // Clave de acceso al sistema.
    @Column
    private String clave;

    // Pregunta de seguridad que se env�a al usuario en caso de errores al
    // iniciar sesi�n.
    @Column
    private String preguntaSecreta;

    // Respuesta a la pregunta secreta de inicio de sesi�n.
    @Column
    private String respuestaSecreta;

    // Indica el ranking del jugador en la tabla general.
    @Column
    private int ranking;
    
    // La imagen que representa el personaje del jugador en la partida.
    @Column
    private Avatar avatar;

    // Resultados de partidas hist�ricos.
    @OneToMany
    private List<ResultadoPartida> historicoPartidas;
    
    /**
     * Constructor vac�o para Hibernate.
     */
    public Jugador() {
        
    }
    
    /**
     * Crea un nuevo jugador.
     * TODO verificar tama�os m�ximos de DB.
     * @param nombre
     * @param clave
     * @param validacionClave
     * @param preguntaSecreta
     * @param respuestaSecreta
     * @throws ZombielandException si alg�n par�metro no es v�lido.
     */
    public Jugador(String nombre, String clave, String validacionClave,
                   String preguntaSecreta, String respuestaSecreta) throws ZombielandException {
        List<String> errores = new ArrayList<String>();
        
        if (nombre == null || nombre.equals(""))
            errores.add("El nombre no puede estar vac�o");
        this.nombre = nombre;
        
        if (clave == null || clave.equals(""))
            errores.add("La clave no puede estar vac�a");
        if (!clave.equals(validacionClave))
            errores.add("La clave y la validaci�n no coinciden");
        this.clave = clave;
        
        if (preguntaSecreta == null || preguntaSecreta.equals(""))
            errores.add("La pregunta secreta no puede estar vac�a");
        this.preguntaSecreta = preguntaSecreta;
        
        if (respuestaSecreta == null || respuestaSecreta.equals(""))
            errores.add("La pregunta secreta no puede estar vac�a");
        this.respuestaSecreta = respuestaSecreta;
        
        if (errores.size() > 0)
            throw new ZombielandException("Por favor, revise los campos indicados", errores);
    }
    
    public Jugador(POJORegistro registro) throws ZombielandException {
        this(registro.getNombre(), registro.getClave(),
             registro.getClave(), registro.getPreguntaSecreta(),
             registro.getRespuestaSecreta());
    }
    
    /**
     * @return las partidas que lleva jugadas hist�ricamente.
     */
    public int getPartidasJugadas() {
        // TODO implementar.
        return 0;
    }

    /**
     * @return las partidas que lleva ganadas hist�ricamente.
     */
    public int getPartidasGanadas() {
        // TODO implementar.
        return 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Jugador)) return false;
        Jugador otro = (Jugador)obj;
        return nombre.equals(otro.nombre) &&
               clave.equals(otro.clave) &&
               preguntaSecreta.equals(otro.preguntaSecreta) &&
               respuestaSecreta.equals(otro.respuestaSecreta) &&
               ranking == otro.ranking;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getPreguntaSecreta() {
        return preguntaSecreta;
    }
    
    
    public String getRespuestaSecreta() {
        return respuestaSecreta;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
