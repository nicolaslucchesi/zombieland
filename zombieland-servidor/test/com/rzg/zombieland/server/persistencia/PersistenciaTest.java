package com.rzg.zombieland.server.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rzg.zombieland.comunes.misc.ZombielandException;
import com.rzg.zombieland.server.meta.Jugador;

public abstract class PersistenciaTest<T, T_ID extends Serializable> {

    public PersistenciaTest() {
        super();
    }

    @Before
    public void before() {
        HibernateSingleton.setTest();
    }

    @After
    public void after() {
        HibernateSingleton.cerrarConexion();
    }
    
    /**
     * Genera un objeto con valores aleatorios para persistir como test.
     * @return
     * @throws ZombielandException
     */
    protected abstract T generarObjeto() throws ZombielandException;
    
    /**
     * Modifica valores del objeto aleatoriamente.
     * @param objeto
     * @return 
     * @throws ZombielandException
     */
    protected abstract void actualizarObjeto(T objeto) throws ZombielandException;
    
    /**
     * Devuelve el Dao correspondiente a este objeto.
     * @return
     */
    protected abstract Dao<T, T_ID> getDao();
    
    /**
     * Devuelve el ID del objeto.
     * @param objeto
     */
    protected abstract T_ID getIdObjeto(T objeto);
    
    /**
     * Devuelve un jugador aleatorio.
     * TODO mover a otro lado.
     * @return
     * @throws ZombielandException 
     */
    protected Jugador generarJugador() throws ZombielandException {
        Random random = new Random();
        String clave = Integer.toString(random.nextInt());
        return new Jugador(Integer.toString(random.nextInt()),
                           clave,
                           clave,
                           Integer.toString(random.nextInt()),
                           Integer.toString(random.nextInt()));
    }
    
    /**
     * Crea un jugador y lo trae de la DB.
     * @throws ZombielandException
     */
    @Test
    public void testCrearYRecuperarJugador() throws ZombielandException {
        T objeto = generarObjeto();
        Dao<T, T_ID> dao = getDao();
        dao.guardarObjeto(objeto);
        dao.cerrarSesion();
        
        T jugadorRecuperado = dao.getObjeto(getIdObjeto(objeto));
        assertEquals(objeto, jugadorRecuperado);
    }
    
    /**
     * Guarda un conjunto de jugadores y los recupera a todos, verificando su existencia.
     * @throws ZombielandException
     */
    @Test
    public void testGetListadoJugadores() throws ZombielandException {
        List<T> objetos = new ArrayList<T>();
        Dao<T, T_ID> dao = getDao();
        for (int i = 0; i < 10; i++) {
            objetos.add(generarObjeto());
            dao.guardarObjeto(objetos.get(i));
        }
        dao.cerrarSesion();
        
        List<T> objetosRecuperados = dao.getListado();
        dao.cerrarSesion();
        assertEquals(objetos.size(), objetosRecuperados.size());
        for (int i = 0; i < objetos.size(); i++)
            assertTrue(objetosRecuperados.contains(objetos.get(i)));
    }
    
    /**
     * Guarda un objeto en la DB, lo actualiza y luego lo recupera.
     * @throws ZombielandException
     */
    @Test
    public void testUpdateObjeto() throws ZombielandException {
        T objeto = generarObjeto();
        Dao<T, T_ID> dao = getDao();
        dao.guardarObjeto(objeto);
        dao.cerrarSesion();
        
        actualizarObjeto(objeto);
        dao.actualizarObjeto(objeto);
        dao.cerrarSesion();
        
        T objetoRecuperado = (T) dao.getObjeto(getIdObjeto(objeto));
        assertEquals(objetoRecuperado, objeto);
    }

}