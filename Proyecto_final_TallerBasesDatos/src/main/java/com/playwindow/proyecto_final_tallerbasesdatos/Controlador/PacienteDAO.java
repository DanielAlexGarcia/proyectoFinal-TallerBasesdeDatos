/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Controlador;

import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.Paciente;
import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author daniel
 */
public class PacienteDAO extends AbstracDAO{
    private VentanaInicio Interfaz;
    
    
    public boolean actualizarPaciente(int ID,int DNIPersona, String NumSeguro, String GrupoSangineo, String Alergias) {
	    String sql = "UPDATE Paciente SET DNIPersona = ?, numSeguro = ?, grupoSanineo = ?, alergias = ? " +
	                 "WHERE ID = ?";
	    
         // Implementación del GANCHO/PASO ABSTRACTO usando un lambda (StatementSetter)
         AbstracDAO.StatementSetter setter = (stmt) -> {
            stmt.setInt(1, DNIPersona);
            stmt.setString(2, NumSeguro);
            stmt.setString(3, GrupoSangineo);
            stmt.setString(4, Alergias);
            stmt.setInt(6, ID);
         };
         return ejecutarCRUDTemplate(sql, setter, "actualizar Paciente");
	}
    
    
    
    // Elimina fila usando id con PreparedStatement
    public boolean eliminarFila(int PacienteID) {
	        String sql = "DELETE FROM Paciente WHERE ID = ?";
                AbstracDAO.StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, PacienteID);
                };
	        return ejecutarCRUDTemplate(sql, setter, "eliminar Paciente");
	    }
    
    // agregar fila
         public boolean insertarPaciente(Integer idOP, int DNIPersona, String NumSeguro, String GrupoSangineo, String Alergias) {
    boolean insertarManual = idOP != null;
    String sql;
    if (!insertarManual) {
        sql = "INSERT INTO Persona (DNIPersona, numSeguro, grupoSanineo, alergias) VALUES (?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO Persona (ID, DNIPersona, numSeguro, grupoSanineo, alergias) VALUES (?, ?, ?, ?, ?)";
    }
    

    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert paciente on");
    }
    
    // 🌟 1. DEFINIR TODA LA LÓGICA DE ASIGNACIÓN DENTRO DEL STATEMENT SETTER 🌟
    AbstracDAO.StatementSetter setter = (stmt) -> {
        int paramIndex = 1; // El índice comienza en 1 dentro de la lambda

        // 2. Manejar la inserción del ID (DNI) opcional
        if (idOP != null) {
            stmt.setInt(paramIndex++, idOP);
        }

        // 3. Asignar los demás campos
        stmt.setInt(paramIndex++, DNIPersona);
        stmt.setString(paramIndex++, NumSeguro);
        stmt.setString(paramIndex++, GrupoSangineo);
        stmt.setString(paramIndex++, Alergias);
    };

    // 4. Ejecutar el Template Method (se encarga del try-catch, conexión y ejecución)
    boolean exito = ejecutarCRUDTemplate(sql, setter, "insertar Paciente");
    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert persona ooff");
    }
    
    return exito;
}
         
    public Paciente buscarPacientePorId(int pacienteID) {
	        String sql = "SELECT * FROM Paciente WHERE DNI = ?";
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            stmt.setInt(1, pacienteID);
	            rs = stmt.executeQuery();
	            
	            // IMPORTANTE: Verificar si existe una fila antes de leer los datos
	            if (rs.next()) {
	                // Ahora sí podemos leer los datos de la fila
	                Paciente dona = new Paciente(
	                    rs.getInt("ID"),
                            rs.getInt("DNIPersona"),
	                    rs.getString("numSeguro"),
	                    rs.getString("grupoSanineo"),
	                    rs.getString("alergias")
	                );
                              
	                return dona;
	            } else {
	                // No se encontró ningún donador con ese ID
	                System.out.println("No se encontró un paciente con ese ID: " + pacienteID);
	                return null;
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("Error al buscar Paciente: " + e.getMessage());
	            e.printStackTrace(); // Para más detalles del error
	            return null;
	        } finally {
	            // Cerrar recursos para evitar memory leaks
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	            } catch (SQLException e) {
	                System.out.println("Error al cerrar recursos: " + e.getMessage());
	            }
	        }
	    }
    
    public ResultSet listarPersonas() {
	        return listaEntity("Paciente");
	    } 
}
