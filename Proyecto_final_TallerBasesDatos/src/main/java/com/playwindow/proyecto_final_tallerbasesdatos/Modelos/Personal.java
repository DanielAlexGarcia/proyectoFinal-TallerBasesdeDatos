/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Modelos;

/**
 *
 * @author daniel
 */
public class Personal {
    private int ID;
    private int DNIPersona;
    private String rol;
    private String departamento;
    private String especialidad;
    private float salario;
    
    // contructor con ID
    
    public Personal(int id, int dniPersona, String Rol, String Departamento, String Especialidad,
            float Salario){
        this.ID = id;
        this.DNIPersona = dniPersona;
        this.rol = Rol;
        this.departamento = Departamento;
        this.especialidad = Especialidad;
        this.salario = Salario;
    }
    
    // constructor sin Id
    
    public Personal(int dniPersona, String Rol, String Departamento, String Especialidad,
            float Salario){
        this.DNIPersona = dniPersona;
        this.rol = Rol;
        this.departamento = Departamento;
        this.especialidad = Especialidad;
        this.salario = Salario;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
    
}
