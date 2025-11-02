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
import com.playwindow.proyecto_final_tallerbasesdatos.Controlador.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.math.BigDecimal;

/**
 *
 * @author daniel
 */
public class PersonalDAO {
    VentanaInicio Interfaz;
    public PersonalDAO(VentanaInicio interfa){
        if (interfa != null){
            this.Interfaz = interfa;
        }
    }
    
    public boolean actualizarPersonal(int ID, int dnipersona, String rol, String departamento, String especialidad, float salario) {
	    String sql = "UPDATE Personal SET DNIPersona = ?, rol = ?, departamento = ?, especialidad = ?, salario = ? " +
	                 "WHERE ID = ?";
	    try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
	        stmt.setInt(1, dnipersona);
	        stmt.setString(2, rol);
	        stmt.setString(3, departamento);
	        stmt.setString(4, especialidad);
	        stmt.setBigDecimal(5, conversorfloat(salario));
	        stmt.setInt(6, ID);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar Personal: " + e.getMessage());
	        Interfaz.ShowMessage("Error al actualizar Personal: \n" + e.getMessage());
	        return false;
	    }
	}
    // Elimina fila usando id con PreparedStatement
   
    public boolean eliminarFila(int PersonaDNI) {
	        String sql = "DELETE FROM Personal WHERE ID = ?";
	        try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
	            stmt.setInt(1, PersonaDNI);
	            int filasAfectadas = stmt.executeUpdate();
	            return filasAfectadas > 0;
	        } catch (SQLException e) {
	            System.out.println("Error al eliminar fila: " + e.getMessage()+"\n\n"+
                            "Es posible que este registro sea una dependencia de otro registro");
	            return false;
	        }
	    }
    /*
    // agregar fila
         public boolean insertarPersona(Integer idOP, String Nombres, String PrimerAP, String SegundoAP,
	    		String FechaNaci, String Telefono) {
	    		
	        String sql;
	        if (idOP == null) {
	            sql = "INSERT INTO Persona (Nombres, PrimerAP, SegundoAP, FechaNaci, Telefono)" +
	                  "VALUES (?, ?, ?, ?, ?)";
	        } else {
	            sql = "INSERT INTO Persona (DNI, Nombres, PrimerAP, SegundoAP, FechaNaci, Telefono)" +
	                  "VALUES (?, ?, ?, ?, ?, ?)";
	        }
                
                ejecutarComandoDirecto("set identity_insert persona on");

	        try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
                    
	            int paramIndex = 1;
	            
	            if (idOP != null) {
	                stmt.setInt(paramIndex++, idOP);
	            }
                    java.sql.Date sqlDate = convertirStringADateSQL(FechaNaci);

                    if (sqlDate == null) {
                        System.err.println("Inserción cancelada: Formato de fecha de nacimiento inválido.");
                    }
	            
	            stmt.setString(paramIndex++, Nombres);
	            stmt.setString(paramIndex++, PrimerAP);
	            stmt.setString(paramIndex++, SegundoAP);
	            stmt.setDate(paramIndex++, sqlDate);
	            stmt.setString(paramIndex++, Telefono);
                    
                    
	            int filasAfectadas = stmt.executeUpdate();
                    
                    ejecutarComandoDirecto("set identity_insert persona off");
                    
	            return filasAfectadas > 0;
	        } catch (SQLException e) {
	        	
	            System.out.println("Error al insertar Donador: " + e.getMessage());
	            String n = e.getMessage();
	            if (n.contains("Duplicate") && n.contains("entry")) {
	            	Interfaz.ShowMessage("Error al insertar Donador: \n" + 
	            "Ya existe un donador con el mismo ID");
	            }else {
	            	Interfaz.ShowMessage("Error al insertar Donador: \n" + e.getMessage());
	            }
	            
	            return false;
	        }
	    }
         
    public Persona buscarPersonaPorId(int donadorID) {
	        String sql = "SELECT * FROM Persona WHERE DNI = ?";
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            stmt.setInt(1, donadorID);
	            rs = stmt.executeQuery();
	            
	            // IMPORTANTE: Verificar si existe una fila antes de leer los datos
	            if (rs.next()) {
	                // Ahora sí podemos leer los datos de la fila
	                Persona dona = new Persona(
	                    rs.getInt("DNI"),
	                    rs.getString("Nombres"),
	                    rs.getString("PrimerAP"),
	                    rs.getString("SegundoAP"),
	                    rs.getString("FechaNaci"),
	                    rs.getString("Telefono")
	                );
                              
	                return dona;
	            } else {
	                // No se encontró ningún donador con ese ID
	                System.out.println("No se encontró una persona con ese DNI: " + donadorID);
	                return null;
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("Error al buscar Persona: " + e.getMessage());
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
	        String sql = "SELECT * FROM Persona";
	        try {
	        	PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            return stmt.executeQuery();
	        } catch (SQLException e) {
	            System.out.println("Error al listar Personas: " + e.getMessage());
	            
	            return null;
	        }
	    }     
         
         public Date convertirStringADateSQL(String fechaString) {
    final String FORMATO_FECHA = "yyyy-MM-dd";
    SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_FECHA);

    formatter.setLenient(false);

    try {
        // 1. Convertir String a java.util.Date
        java.util.Date parsedDate = formatter.parse(fechaString);

        // 2. Convertir java.util.Date a java.sql.Date
        return new Date(parsedDate.getTime());

    } catch (ParseException e) {
        // Manejo de error si el formato no coincide (e.g., el usuario escribió mal)
        System.err.println("Error de formato de fecha. Se esperaba: " + FORMATO_FECHA);
        System.err.println("Cadena recibida: " + fechaString);
        e.printStackTrace();
        
        // Retorna null para indicar un fallo en la conversión.
        return null; 
    }
}
         private boolean ejecutarComandoDirecto(String comando){
             java.sql.Connection conn = ConexionBD.getInstancia().getConnection();
                try (java.sql.Statement directStmt = conn.createStatement()) {
                directStmt.execute(comando);
                return true;
    
            } catch (java.sql.SQLException e) {
                System.err.println("Error al ejecutar comando directo: " + e.getMessage());
                return false;
            }
         }
    */
    private BigDecimal conversorfloat (float salarios){
       String salarioString = Float.toString(salarios);
        BigDecimal salariose =  new BigDecimal(salarioString);
        return salariose;
    }
}
