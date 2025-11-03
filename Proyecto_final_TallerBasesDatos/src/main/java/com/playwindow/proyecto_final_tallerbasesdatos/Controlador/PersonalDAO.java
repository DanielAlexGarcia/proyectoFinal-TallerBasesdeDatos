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
public class PersonalDAO extends AbstracDAO{
    VentanaInicio Interfaz;
    public PersonalDAO(VentanaInicio interfa){
        if (interfa != null){
            this.Interfaz = interfa;
        }
    }
    
    public boolean actualizarPersonal(int ID, int dnipersona, String rol, String departamento, String especialidad, float salario) {
	    String sql = "UPDATE Personal SET DNIPersona = ?, rol = ?, departamento = ?, especialidad = ?, salario = ? " +
	                 "WHERE ID = ?";
                StatementSetter setter = (stmt) -> {
                stmt.setInt(1, dnipersona);
	        stmt.setString(2, rol);
	        stmt.setString(3, departamento);
	        stmt.setString(4, especialidad);
	        stmt.setBigDecimal(5, conversorfloat(salario));
	        stmt.setInt(6, ID);
                };
                        return ejecutarCRUDTemplate(sql, setter, "actualizar personal");
	  
	}
    // Elimina fila usando id con PreparedStatement
   
    public boolean eliminarFila(int PersonalID) {
	        String sql = "DELETE FROM Personal WHERE ID = ?";
	        StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, PersonalID);
                };
                return ejecutarCRUDTemplate(sql, setter, "eliminar Eprsonal"); 
	    }
    
    // agregar fila
         public boolean insertarPersonal(Integer idOP, int DNIPersona, String rol, String departamento,
	    		String especialidad, float salario) {
	    		 boolean insertarManual = idOP != null;
	        String sql;
	        if (!insertarManual) {
	            sql = "INSERT INTO Personal (DNIPersona, rol, departamento, especialidad, salario)" +
	                  "VALUES (?, ?, ?, ?, ?)";
	        } else {
	            sql = "INSERT INTO Personal (ID, DNIPersona, rol, departamento, especialidad, salario)" +
	                  "VALUES (?, ?, ?, ?, ?, ?)";
	        }
                
                if (insertarManual) {
                    ejecutarComandoDirecto("set identity_insert personal on");
                 }

	        StatementSetter setter = (stmt) -> {
                    int paramIndex = 1;
                    if (idOP != null) {
                        stmt.setInt(paramIndex++, idOP);
                    }
                stmt.setInt(paramIndex++, DNIPersona);
	        stmt.setString(paramIndex++, rol);
	        stmt.setString(paramIndex++, departamento);
	        stmt.setString(paramIndex++, especialidad);
	        stmt.setBigDecimal(paramIndex++, conversorfloat(salario));
                };
	        
                boolean exito = ejecutarCRUDTemplate(sql, setter, "insertar Personal");
                
                if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert personal off");
    }
                return exito;
                
	    }
         
    public Personal buscarPersonalPorId(int PersonalID) {
	        String sql = "SELECT * FROM Personal WHERE ID = ?";
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            stmt.setInt(1, PersonalID);
	            rs = stmt.executeQuery();
	            
	            // IMPORTANTE: Verificar si existe una fila antes de leer los datos
	            if (rs.next()) {
	                // Ahora sí podemos leer los datos de la fila
	                Personal dona = new Personal(
	                    rs.getInt("ID"),
                            rs.getInt("DNIPersona"),
	                    rs.getString("rol"),
	                    rs.getString("departamento"),
	                    rs.getString("especialidad"),
	                    rs.getBigDecimal("salario").floatValue()
	                );
                              
	                return dona;
	            } else {
	                // No se encontró ningún donador con ese ID
	                System.out.println("No se encontró una persona con ese DNI: " + PersonalID);
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
    
    public ResultSet listarPersonal() {
	        return listaEntity("Personal");
}

    private BigDecimal conversorfloat (float salarios){
       String salarioString = Float.toString(salarios);
        BigDecimal salariose =  new BigDecimal(salarioString);
        return salariose;
    }
}
