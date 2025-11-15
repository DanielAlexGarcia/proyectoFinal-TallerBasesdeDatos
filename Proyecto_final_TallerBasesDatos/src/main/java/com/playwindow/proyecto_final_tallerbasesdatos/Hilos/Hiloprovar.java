/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Hilos;

import com.playwindow.proyecto_final_tallerbasesdatos.Controlador.PersonalDAO;
import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.VentanaInicio;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author daniel
 */
public class Hiloprovar {
    //VentanaInicio interfaz;
    PersonalDAO PDAO = new PersonalDAO(null);
    ArrayList<String> datos;
    int namePanel;
    
    public Hiloprovar(VentanaInicio inter, ArrayList<String> datos) {
       //this.interfaz = inter;
       this.datos = datos;
    }
    
    
    public void hiloAgregarPersonal(){
        //interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
                    if (PDAO != null){
                        int dat = Integer.parseInt(datos.get(0));
                        boolean logro = PDAO.insertarPersonal(null, dat, datos.get(1), datos.get(2), datos.get(3), Float.parseFloat(datos.get(4)));
                        if(logro){
                            System.out.println("se logro hacer la insercion");
                        }
                    }// Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	//interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
                    	//interfaz.ShowMessage("Añadido con exito");
                        
                    });
            	
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    
}
