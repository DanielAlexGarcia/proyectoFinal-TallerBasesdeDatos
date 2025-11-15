/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Hilos;

import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.*;
import com.playwindow.proyecto_final_tallerbasesdatos.Controlador.*;
import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.SwingUtilities;

/**
 *
 * @author daniel
 */
public class HilosABCCPersonal {
    VentanaInicio interfas;
    PersonalDAO PalDAO;
    int indexDato;
    ArrayList<String> datos;
    private final Thread hiloDeTrabajo;
    private volatile boolean seguirEjecutando = true;
    private final BlockingQueue<Runnable> colaDeTareas = new LinkedBlockingQueue<>();
    
    Runnable agregarPersonal;
    Runnable eliminarPersonal;
    Runnable modificarPersonal;
    
    public HilosABCCPersonal(VentanaInicio interf, PersonalDAO pDAO, int Indexdat
    , ArrayList<String> datos) {
        this.interfas = interf;
        this.PalDAO = pDAO;
        this.indexDato = Indexdat;
        this.datos = datos;
        // Definimos lo que hará el hilo de trabajo cuando se inicie.
        this.hiloDeTrabajo = new Thread(() -> {
            try {
                // El hilo de trabajo se queda en este bucle "por siempre"
                while (seguirEjecutando) {
                    // Espera por una tarea en la cola. Si está vacía, el hilo se "duerme"
                    // hasta que llegue una tarea (Método take() bloquenate).
                    Runnable tarea = colaDeTareas.take();
                    
                    System.out.println("-> Hilo de Trabajo: Ejecutando una nueva tarea...");
                    tarea.run(); // Ejecuta la tarea
                    System.out.println("-> Hilo de Trabajo: Tarea finalizada.");
                }
            } catch (InterruptedException e) {
                // Esto sucede si alguien llama a hiloDeTrabajo.interrupt()
                System.out.println("-> Hilo de Trabajo: Interrumpido y terminando.");
            }
        }, "Hilo-Trabajador"); // Le damos un nombre para identificarlo en la consola

        // Arranca el hilo. A partir de aquí, el hilo ya está esperando tareas.
        this.hiloDeTrabajo.start();
        //System.out.println("ManejadorTareas iniciado. Hilo de trabajo: " + hiloDeTrabajo.getName())
    }
    
    private void initAddPersonal(){
        agregarPersonal = () ->{
            
            
            //PalDAO.insertarPersonal(valorTOInt(datos.get(0)), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5));
    };
    }
    
    private int valorTOInt(String s){
        return Integer.parseInt(s);
    }
    
    
    public void asignarTarea(Runnable tarea) {
        Runnable tareaCompleta = () -> {
            try {
                // Ejecuta la Tarea Pesada (en Hilo-Trabajador)
                System.out.println("-> Hilo de Trabajo: Iniciando Tarea...");
                //tareaDeTrabajo.run(); 
                System.out.println("-> Hilo de Trabajo: Tarea finalizada.");
            } finally {
                // 3. SOLICITAR AL HILO DE INTERFAZ QUE CIERRE LA CARGA
                
                // SwingUtilities.invokeLater() garantiza que la tareaDeFinalizacion
                // se ejecute en el Hilo de Despacho de Eventos (Event Dispatch Thread - EDT),
                // que es el Hilo Principal de la GUI.
                //SwingUtilities.invokeLater(tarea    DeFinalizacion);
            }
        };

        // 4. ENVIAR LA TAREA COMPLETA AL HILO DE TRABAJO
        try {
            colaDeTareas.put(tareaCompleta);
            System.out.println("<<< Hilo Principal: Nueva tarea con feedback asignada a la cola.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void detener() {
        seguirEjecutando = false;
        // Interrumpimos para sacar el hilo de su estado de espera (colaDeTareas.take()).
        hiloDeTrabajo.interrupt();
        try {
            // Esperamos a que el hilo de trabajo termine antes de salir del programa.
            hiloDeTrabajo.join(); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("ManejadorTareas detenido.");
    }
    
    
    
    
    
    
}
