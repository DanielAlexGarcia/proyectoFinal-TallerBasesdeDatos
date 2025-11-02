/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Controlador;

import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.*;
import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;




/**
 *
 * @author daniel
 */
public class PersonaDAO {

    public void setConexionBD(ConexionBD ConexionBD) {
        this.ConexionBD = ConexionBD;
    }
    private VentanaInicio Interfaz;
    private ConexionBD ConexionBD;
    
    public PersonaDAO(VentanaInicio interfaz, ConexionBD conexionBD){
 
        this.Interfaz = interfaz;
        this.ConexionBD = conexionBD;
    }
    
    public boolean actualizarDonador(int dni, String Nombres, String PrimerAP, String SegundoAP, String FechaNaci, String telefono) {
	    String sql = "UPDATE Persona SET Nombres = ?, PrimerAP = ?, SegundoAP = ?, FechaNaci = ?, Telefono = ?, " +
	                 "WHERE DNI = ?";
	    try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
	        stmt.setString(1, Nombres);
	        stmt.setString(2, PrimerAP);
	        stmt.setString(3, SegundoAP);
	        stmt.setString(4, FechaNaci);
	        stmt.setString(5, telefono);
	        stmt.setInt(6, dni);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar Persona: " + e.getMessage());
	        Interfaz.ShowMessage("Error al actualizar Persona: \n" + e.getMessage());
	        return false;
	    }
	}
    
}
