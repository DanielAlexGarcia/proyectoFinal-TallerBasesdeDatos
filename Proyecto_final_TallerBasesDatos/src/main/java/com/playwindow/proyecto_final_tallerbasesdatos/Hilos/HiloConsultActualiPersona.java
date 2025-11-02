/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.playwindow.proyecto_final_tallerbasesdatos.Hilos;
import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.*;
import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.*;
import com.playwindow.proyecto_final_tallerbasesdatos.Controlador.*;

import java.sql.ResultSet;
import javax.swing.SwingUtilities;

/**
 *
 * @author daniel
 */
public class HiloConsultActualiPersona {
	private VentanaInicio interfa;
    private Integer id;
    private PersonaDAO dona = new PersonaDAO(null);
    private String ventana;
    

    public HiloConsultActualiPersona(Integer consultaID, String ventana, VentanaInicio interfaces) {
       this.id = consultaID;
       this.ventana = ventana;
       this.interfa = interfaces;
    }

    public void consultarYActualizarGUI() {
    /*	interfa.showMessageDialog(interfa.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(id != null && id != 0) {
            		int ID = (int) id;
            		// Obtener datos desde la base de datos
                    Persona donana = dona.buscarDonadorPorId(ID);
                    // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfa.showMessageDialog(interfa.frame, "Buscando...", false);
                    	interfa.interfaz.setDonador(donana, ventana);
                        
                    });
            	}
            	else {
            		ResultSet donana = dona.listarDonadores();
            		interfa.showMessageDialog(interfa.frame, "Buscando...", false);
            		interfa.interfaz.setResultSet(donana);
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
*/    }

}
