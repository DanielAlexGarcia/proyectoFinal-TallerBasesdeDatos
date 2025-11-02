package com.playwindow.proyecto_final_tallerbasesdatos.Controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class ConexionBD {
	private static final ConexionBD instancia = new ConexionBD();

	public static ConexionBD getInstancia() {							// Se implemeta un metodo estatico para aplicar Singleton (una unica instancia en todo el programa)
	    return instancia;
	}

    private Connection connection; 		// es parte del API JDBC permite establecer y gestionar la conexion a una base de datos
    private Statement stm; // PreparedStatement es mejor ya que evita SQL Injection
    private ResultSet rs;

    private ConexionBD() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String URL = "jdbc:mariadb://localhost:3306/recaudacionuniversicad";


            connection = DriverManager.getConnection(URL, "root", "f44WOs%NvF");
            System.out.println("Conexcion exitosa");

        } catch (ClassNotFoundException e) {
            System.out.println("Error en el connector / driver");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //crud - create read undate delete
        //metodo para los procesos de abc (altas, bajas y cambios)
    }

    public void connecctionDatabase(String dataBase){
        String sql = "Use "+dataBase+";";
        ejecutarInstruccionSQL(sql);
    }

    public boolean eliminarFila(int NumControl, String tabla) {
        String sql = "DELETE FROM "+tabla+" WHERE "+tabla+"ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Asignar el valor del id a la consulta
            stmt.setInt(1, NumControl);

            // Ejecutar la actualización (eliminación)
            int filasAfectadas = stmt.executeUpdate();

            // Si se eliminó al menos una fila, la operación fue exitosa
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ejecutarIntruccionLMD(String sql){       // modifica
        boolean res = false;
        try {
            stm = connection.createStatement();
            if(stm.executeUpdate(sql) >=1){
                res = true;
            }else{
                System.out.println("Error al agregar Donador");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }


    public ResultSet ejecutarInstruccionSQL(String sql){
        rs = null;
        System.out.println("SQL => " + sql);
        try {
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error en la ejecucion de la instruccion SQL");
        }
        return rs;
    }
    
    public Connection getConnection() {
    	return connection;
    }
}

