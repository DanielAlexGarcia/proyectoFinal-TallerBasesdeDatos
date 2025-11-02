/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Modelos;

/**
 *
 * @author daniel
 */
public class Paciente {
    private int ID;
    private int DNIPersona;
    private String numSeguro;
    private String grupoSanineo;
    private String alergias;
    
    //Contructor con ID
    public Paciente (int id, int DNI, String numSeguro, String grupoSangineo,String alergias){
        this.ID = id;
        this.DNIPersona = DNI;
        this.numSeguro = numSeguro;
        this.grupoSanineo = grupoSangineo;
        this.alergias = alergias;
    }
    
    //Constructor sin ID
    public Paciente (int DNI, String numSeguro, String grupoSangineo,String alergias){
        this.DNIPersona = DNI;
        this.numSeguro = numSeguro;
        this.grupoSanineo = grupoSangineo;
        this.alergias = alergias;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDNIPersona() {
        return DNIPersona;
    }

    public void setDNIPersona(int DNIPersona) {
        this.DNIPersona = DNIPersona;
    }

    public String getNumSeguro() {
        return numSeguro;
    }

    public void setNumSeguro(String numSeguro) {
        this.numSeguro = numSeguro;
    }

    public String getGrupoSanineo() {
        return grupoSanineo;
    }

    public void setGrupoSanineo(String grupoSanineo) {
        this.grupoSanineo = grupoSanineo;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    
}
