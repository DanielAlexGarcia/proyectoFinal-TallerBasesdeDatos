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
    
    //constructor con ID
    public Cita(int id, int idPaciente, int idPersonal, String fecha, String hora, String motivo,
            String estado){
        this.ID = id;
        this.IDPaciente = idPaciente;
        this.IDPersonal = idPersonal;
        this.fecha = fecha;
        this.Hora = hora;
        this.estado = estado;
    }
    
    //Constructor sin ID
    public Cita(int idPaciente, int idPersonal, String fecha, String hora, String motivo,
            String estado){
        this.IDPaciente = idPaciente;
        this.IDPersonal = idPersonal;
        this.fecha = fecha;
        this.Hora = hora;
        this.estado = estado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDPaciente() {
        return IDPaciente;
    }

    public void setIDPaciente(int IDPaciente) {
        this.IDPaciente = IDPaciente;
    }

    public int getIDPersonal() {
        return IDPersonal;
    }

    public void setIDPersonal(int IDPersonal) {
        this.IDPersonal = IDPersonal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
