/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Modelos;

/**
 *
 * @author daniel
 */
public class Persona {
    private int DNI;

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getPrimerAP() {
        return PrimerAP;
    }

    public void setPrimerAP(String PrimerAP) {
        this.PrimerAP = PrimerAP;
    }

    public String getSegundoAP() {
        return SegundoAP;
    }

    public void setSegundoAP(String SegundoAP) {
        this.SegundoAP = SegundoAP;
    }

    public String getFechaNaci() {
        return FechaNaci;
    }

    public void setFechaNaci(String FechaNaci) {
        this.FechaNaci = FechaNaci;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
    private String Nombres;
    private String PrimerAP;
    private String SegundoAP;
    private String FechaNaci;
    private String Telefono;
    
    
    public Persona(int dni, String nombres, String primerAP, String segundoAP,
            String fechaNaci, String telefono){
        DNI = dni;
        Nombres = primerAP;
        PrimerAP = primerAP;
        SegundoAP = segundoAP;
        FechaNaci = fechaNaci;
        Telefono = telefono;
    }
    public Persona(String nombres, String primerAP, String segundoAP,
            String fechaNaci, String telefono){
        Nombres = primerAP;
        PrimerAP = primerAP;
        SegundoAP = segundoAP;
        FechaNaci = fechaNaci;
        Telefono = telefono;
    }
    
    
    @Override
	public String toString() {
		return "Persona{"+ '\''
				+ "DNI: " + DNI + '\n'
				+ "Nombres: " + Nombres+ '\n'
				+ "PrimerAP: " + PrimerAP+ '\n'
				+ "SegundoAP: " +SegundoAP+ '\n'
				+ "Fecha nacimiento: " +FechaNaci+ '\n'
				+ "Telefono: "+ Telefono+ '\n'+" }";
				
		
	}
}
