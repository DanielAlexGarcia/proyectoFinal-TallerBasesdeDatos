/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Controlador;

import com.playwindow.proyecto_final_tallerbasesdatos.Vistas.VentanaInicio;
import com.playwindow.proyecto_final_tallerbasesdatos.Modelos.Cita;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

/**
 *
 * @author daniel
 */
public class CitaDAO extends AbstracDAO{
    private VentanaInicio Interfaz;
    
    public CitaDAO (VentanaInicio interfaz){
        if (interfaz != null){
            this.Interfaz = interfaz;
            setVentanaInicio(interfaz);
        }
    }
    
    public boolean actualizarCita(int ID, int IDPaciente, int IDPersonal, String fecha, String hora, String motivo, String estado) {
	    String sql = "UPDATE Cita SET IDPaciente = ?, IDPersonal = ?, fecha = ?, hora = ?, motivo = ? , estado = ?" +
	                 "WHERE DNI = ?";
	    LocalTime localTime = LocalTime.parse(hora); 
            Time sqlTime = Time.valueOf(localTime);
            java.sql.Date sqlDate = convertirStringADateSQL(fecha);
         // Implementación del GANCHO/PASO ABSTRACTO usando un lambda (StatementSetter)
         StatementSetter setter = (stmt) -> {
            stmt.setInt(1, IDPaciente);
            stmt.setInt(2, IDPersonal);
            stmt.setDate(3, sqlDate);
            stmt.setTime(4, sqlTime);
            stmt.setString(5, motivo);
            stmt.setString(6, estado);
            stmt.setInt(7, ID);
         };
         return ejecutarCRUDTemplate(sql, setter, "actualizar Persona");
	}
    // Elimina fila usando id con PreparedStatement
    public boolean eliminarFila(int CitaID) {
	        String sql = "DELETE FROM Cita WHERE DNI = ?";
                StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, CitaID);
                };
	        return ejecutarCRUDTemplate(sql, setter, "eliminar Cita");
	    }
    
    // agregar fila
         public boolean insertarCita(Integer idOP, int IDPaciente, int IDPersonal,
                 String fecha, String hora, String motivo, String estado) {
    boolean insertarManual = idOP != null;
    String sql;
    if (!insertarManual) {
        sql = "INSERT INTO Cita (IDPaciente, IDPersonal, fecha, hora, motivo, estado) VALUES (?, ?, ?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO Cita (ID, IDPaciente, IDPersonal, fecha, hora, motivo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }
    
    // Convertir la fecha ANTES de la lambda para que sea efectivamente final
    java.sql.Date sqlDate = convertirStringADateSQL(fecha);
    LocalTime localTime = LocalTime.parse(hora); 
    Time sqlTime = Time.valueOf(localTime);

    if (sqlDate == null) {
        System.err.println("Inserción cancelada: Formato de fechainválido.");
        return false; // Salir inmediatamente si la fecha es inválida
    }
    if (sqlTime == null){
        System.err.println("Inserción cancelada: Formato de la hora inválido.");
        return false;
    }
    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert Cita on");
    }
    
    // 🌟 1. DEFINIR TODA LA LÓGICA DE ASIGNACIÓN DENTRO DEL STATEMENT SETTER 🌟
    StatementSetter setter = (stmt) -> {
        int paramIndex = 1; // El índice comienza en 1 dentro de la lambda

        // 2. Manejar la inserción del ID (DNI) opcional
        if (idOP != null) {
            stmt.setInt(paramIndex++, idOP);
        }

        // 3. Asignar los demás campos
        stmt.setInt(paramIndex++, IDPaciente);
        stmt.setInt(paramIndex++, IDPersonal);
        stmt.setDate(paramIndex++, sqlDate);
        stmt.setTime(paramIndex++, sqlTime);
        stmt.setString(paramIndex++, motivo);
        stmt.setString(paramIndex++, estado);
    };

    // 4. Ejecutar el Template Method (se encarga del try-catch, conexión y ejecución)
    boolean exito = ejecutarCRUDTemplate(sql, setter, "insertar Cita");
    
    if (insertarManual) {
        ejecutarComandoDirecto("set identity_insert Cita off");
    }
    
    return exito;
}
         
    public Cita buscarCitaPorId(int donadorID) {
	        String sql = "SELECT * FROM Cita WHERE DNI = ?";
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            stmt.setInt(1, donadorID);
	            rs = stmt.executeQuery();
	            
	            // IMPORTANTE: Verificar si existe una fila antes de leer los datos
	            if (rs.next()) {
	                // Ahora sí podemos leer los datos de la fila
	                Cita dona = new Cita(
	                    rs.getInt("ID"),
                            rs.getInt("IDPaciente"),
                            rs.getInt("IDPersonal"),
                            sqlDateToString(rs.getDate("fecha")),
                            sqlTimeToString(rs.getTime("hora")),
	                    rs.getString("motivo"),
	                    rs.getString("estado")
	                );
                              
	                return dona;
	            } else {
	                // No se encontró ningún donador con ese ID
	                System.out.println("No se encontró una cita con ese ID: " + donadorID);
	                return null;
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("Error al buscar Cita: " + e.getMessage());
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
    
    public ResultSet listarCitas() {
	        return listaEntity("Cita");
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
         
         public static String sqlDateToString(Date sqlDate) {
    if (sqlDate == null) {
        return null;
    }
    // Define el formato deseado para la fecha (ej: 2025-11-03)
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    // El objeto java.sql.Date es un subtipo de java.util.Date,
    // por lo que se puede formatear directamente.
    return formatter.format(sqlDate);
}
         
         public static String sqlTimeToString(Time sqlTime) {
    if (sqlTime == null) {
        return null;
    }
    // Define el formato deseado para la hora (ej: 14:30:00)
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    
    // El objeto java.sql.Time es un subtipo de java.util.Date,
    // por lo que se puede formatear directamente.
    return formatter.format(sqlTime);
}
    
}
