/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Modelos;

/**
 *
 * @author daniel
 */
public class Cita {
    private int ID;
    private int IDPaciente;
    private int IDPersonal;
    private String fecha;
    private String Hora;
    private String motivo;
    private String estado;
    
    public Cita(int id, int idPaciente, int idPersonal, String fecha, String hora, String motivo,
            String estado){
        this.ID = id;
        this.IDPaciente = idPaciente;
        this.IDPersonal = idPersonal;
        this.fecha = fecha;
        this.Hora = hora;
        this.estado = estado;
    }
}
