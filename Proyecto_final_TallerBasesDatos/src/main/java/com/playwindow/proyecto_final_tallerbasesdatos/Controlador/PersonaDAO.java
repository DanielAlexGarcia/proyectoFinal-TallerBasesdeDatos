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




/**
 *
 * @author daniel
 */
public class PersonaDAO extends AbstracDAO {
    private VentanaInicio Interfaz;
    
    public PersonaDAO(VentanaInicio interfaz){
                if(interfaz != null) {
			this.Interfaz = interfaz;
                        setVentanaInicio(interfaz);
		}
                
    }
    
    public boolean actualizarPersona(int dni, String Nombres, String PrimerAP, String SegundoAP, String FechaNaci, String telefono) {
	    String sql = "UPDATE Persona SET Nombres = ?, PrimerAP = ?, SegundoAP = ?, FechaNaci = ?, Telefono = ? " +
	                 "WHERE DNI = ?";
	    
         // Implementación del GANCHO/PASO ABSTRACTO usando un lambda (StatementSetter)
         StatementSetter setter = (stmt) -> {
            stmt.setString(1, Nombres);
            stmt.setString(2, PrimerAP);
            stmt.setString(3, SegundoAP);
            stmt.setString(4, FechaNaci);
            stmt.setString(5, telefono);
            stmt.setInt(6, dni);
         };
         return ejecutarCRUDTemplate(sql, setter, "actualizar Persona");
	}
    // Elimina fila usando id con PreparedStatement
    public boolean eliminarFila(int PersonaDNI) {
	        String sql = "DELETE FROM Persona WHERE DNI = ?";
                StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, PersonaDNI);
                };
	        return ejecutarCRUDTemplate(sql, setter, "eliminar Persona");
	    }
    
    // agregar fila
         public boolean insertarPersona(Integer idOP, String Nombres, String PrimerAP, String SegundoAP,
    String FechaNaci, String Telefono) {
    boolean insertarManual = idOP != null;
    String sql;
    if (!insertarManual) {
        sql = "INSERT INTO Persona (Nombres, PrimerAP, SegundoAP, FechaNaci, Telefono) VALUES (?, ?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO Persona (DNI, Nombres, PrimerAP, SegundoAP, FechaNaci, Telefono) VALUES (?, ?, ?, ?, ?, ?)";
    }
    
    // Convertir la fecha ANTES de la lambda para que sea efectivamente final
    java.sql.Date sqlDate = convertirStringADateSQL(FechaNaci);

    if (sqlDate == null) {
        System.err.println("Inserción cancelada: Formato de fecha de nacimiento inválido.");
        return false; // Salir inmediatamente si la fecha es inválida
    }
    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert persona on");
    }
    
    // 🌟 1. DEFINIR TODA LA LÓGICA DE ASIGNACIÓN DENTRO DEL STATEMENT SETTER 🌟
    StatementSetter setter = (stmt) -> {
        int paramIndex = 1; // El índice comienza en 1 dentro de la lambda

        // 2. Manejar la inserción del ID (DNI) opcional
        if (idOP != null) {
            stmt.setInt(paramIndex++, idOP);
        }

        // 3. Asignar los demás campos
        stmt.setString(paramIndex++, Nombres);
        stmt.setString(paramIndex++, PrimerAP);
        stmt.setString(paramIndex++, SegundoAP);
        stmt.setDate(paramIndex++, sqlDate); // sqlDate es efectivamente final
        stmt.setString(paramIndex++, Telefono);
    };

    // 4. Ejecutar el Template Method (se encarga del try-catch, conexión y ejecución)
    boolean exito = ejecutarCRUDTemplate(sql, setter, "insertar Persona");
    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert persona off");
    }
    
    return exito;
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
	        return listaEntity("Persona");
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
}
