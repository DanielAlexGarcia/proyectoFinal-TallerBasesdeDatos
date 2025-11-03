/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Controlador;

import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.Persona;
import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.VentanaInicio;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author daniel
 */
public abstract class AbstracDAO {
    
    
    // Se recomienda usar el tipo de Interfaz base si existe, o solo la clase VentanaInicio
    protected VentanaInicio Interfaz; 
    
    protected void setVentanaInicio(VentanaInicio interfaz){
        this.Interfaz = interfaz;
    }
    
    protected final boolean ejecutarCRUDTemplate(String sql, StatementSetter setter, String nombreEntidad) {
        try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
            
            // PASO ABSTRACTO: Las subclases definen cómo se llenan los parámetros del PreparedStatement.
            setter.setParameters(stmt);
            
            // PASO FIJO: Ejecutar la operación y verificar las filas afectadas.
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            // PASO FIJO: Manejo de excepciones y notificación a la UI
            System.out.println("Error al " + nombreEntidad + ": " + e.getMessage());
            if (Interfaz != null) {
                // Asumo que la VentanaInicio tiene un método ShowMessage estático o accesible
                // Si ShowMessage es estático, debe estar en Interfaz (VentanaInicio)
                // Si no, usamos la instancia:
                Interfaz.ShowMessage("Error al " + nombreEntidad + ": \n" + e.getMessage());
            }
            return false;
        }
    }
    
    @FunctionalInterface
    protected interface StatementSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
    
    protected ResultSet listaEntity(String entity) {
	        String sql = "SELECT * FROM "+ entity;
	        try {
	        	PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            return stmt.executeQuery();
	        } catch (SQLException e) {
	            System.out.println("Error al listar "+entity+": " + e.getMessage());
	            
	            return null;
	        }
	    } 
    
    
    // Método para ejecutar comandos directos
    protected final boolean ejecutarComandoDirecto(String comando){
        // Mantenemos la implementación original ya que no se ajusta al Template CRUD
         java.sql.Connection conn = ConexionBD.getInstancia().getConnection();
            try (java.sql.Statement directStmt = conn.createStatement()) {
            directStmt.execute(comando);
            return true;
        } catch (java.sql.SQLException e) {
            System.err.println("Error al ejecutar comando directo: " + e.getMessage());
            return false;
        }
    }
}
