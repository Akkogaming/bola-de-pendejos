package Administrador;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import Administrador.MenuAdministrador.MAA;
import Administrador.MenuConsultas.MCA;


public class Admin {

    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    
    public static boolean admin(Connection conn) {

        MAA.MAA();

        return true;
    }

    
   public static void handleConsultas(){
        MCA.handleConsultas();
   }



    public static void insertarEmpleado() {
        Empleados.insertarEmpleado();
    }
    public static void añadirSalon() {
        Salones.añadirSalon();
        }
    public static void añadirServicio() {
            Servicios.añadirServicio();
        }







    public static void eliminarEmpleado() {
        Empleados.eliminarEmpleado();
    }
    
    

    public static void eliminarSalon() {
       Salones.eliminarSalon();
    }
    
     
    
    
    public static void eliminarServicio() {
       Servicios.eliminarServicio();
    }
    

    
    public static void añadirMontaje() {
       Montajes.añadirMontaje();
    }

    
    public static void eliminarMontaje() {
        Montajes.eliminarMontaje();
    }

    public static void añadirEvento() {
        Eventos.añadirEvento();
    }
     
    public static void eliminarEvento() {
        Eventos.eliminarEvento();
    }
        
    
}
