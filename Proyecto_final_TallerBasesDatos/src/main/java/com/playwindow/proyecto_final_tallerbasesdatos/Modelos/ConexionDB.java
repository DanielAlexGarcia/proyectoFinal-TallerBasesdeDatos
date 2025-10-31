/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Modelos;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    private Connection conexion;
    private Statement stm;  //PreparedStatement ES MEJOR YA QUE EVITA SQL Injection
    private ResultSet rs;

    public ConexionDB(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    //127.0.0.1
            String serverName = "localhost"; // O el nombre/IP de tu servidor
            String portNumber = "1433"; 
            String databaseName = "Clinica"; 
            
            // La clave es el parámetro integratedSecurity=true
            String URL = "jdbc:sqlserver://" + serverName + ":" + portNumber + 
                         ";databaseName=" + databaseName + 
                         ";integratedSecurity=true;trustServerCertificate=true;"; // Se añade trustServerCertificate=true si tienes problemas con el certificado SSL

            // Conexión SIN usuario ni contraseña
            conexion = DriverManager.getConnection(URL);

            System.out.println("YEEEEI Casi son ingeniera/o INMORTAL !!!!");

        } catch (ClassNotFoundException e) {
            System.out.println("Error en el connector/driver");
        } catch (SQLException e) {
           e.printStackTrace();
            System.out.println("Error en la conexion a MySQL");
        }
    }

    //CRUD - Create Read Update Delete
    //Metodo para los proces de ABC (altas, bajas y cambios)
    public boolean ejecutarInstruccionLMD(String sql){
        boolean res = false;
        try {
            stm = conexion.createStatement();
            if(stm.executeUpdate(sql) >=1)
                res = true;
        } catch (SQLException e) {
            System.out.println("Error en la ejecucion de la instruccion SQL");
        }
        return res;
    }

    //Metodo para CONSULTAS
    public ResultSet ejecutarInstruccionSQL(String sql){
        rs = null;
        System.out.println("SQL => " + sql);
        try {
            stm = conexion.createStatement();
            rs = stm.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error en la ejecucion de la instruccion SQL");
        }
        return rs;
    }

    public static void main(String[] args) {
        System.out.println("Magia magia con INTELLIJ");
        new ConexionDB();
    }

}

