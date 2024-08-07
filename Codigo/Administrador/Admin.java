package Administrador;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import Consultascarpeta.Consultas;
import Consultascarpeta.ConsultasC;

public class Admin {

    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    
    public static boolean admin(Connection conn) {
        connect = conn;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        leer = new Scanner(System.in);
        boolean isRunning = true;

            

        while (isRunning) {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                       MENU ADMINISTRADOR                     ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║                      1. Consultas                            ║");
            System.out.println("║                      2. Añadir Empleados                     ║");
            System.out.println("║                      3. Eliminar Empleados                   ║");
            System.out.println("║                      4. Añadir Salones                       ║");
            System.out.println("║                      5. Eliminar Salones                     ║");
            System.out.println("║                      6. Añadir Servicios                     ║");
            System.out.println("║                      7. Eliminar Servicios                   ║");
            System.out.println("║                      8. Añadir Montajes                      ║");
            System.out.println("║                      9. Eliminar Montajes                    ║");
            System.out.println("║                      10. Añadir Eventos                      ║");
            System.out.println("║                      11. Eliminar Eventos                    ║");
            System.out.println("║                      12. Salir                               ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            String answer = leer.nextLine();

            switch (answer) {
                case "1":
                    
                    handleConsultas();
                    break;

                case "2":
                    insertarEmpleado();
                    break;

                case "3":
                    eliminarEmpleado();
                    break;

                case "4":
                    añadirSalon();
                    break;

                case "5":
                    eliminarSalon();
                    break;

                case "6":
                    añadirServicio();
                    break;

                case "7":
                    eliminarServicio();
                    break;

                case "8":
                    añadirMontaje();
                    break;

                case "9":
                    eliminarMontaje();
                    break;

                case "10":
                    añadirEvento();
                    break;

                case "11":
                    eliminarEvento();
                    break;

                case "12":
                    System.out.println("Saliendo del menú de administrador...");
                    return false;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        leer.close();
        return true;
    }

    
    private static void handleConsultas() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗"); 
        System.out.println("║               Selecciona la consulta a realizar:             ║"); 
        System.out.println("║             1. Reservaciones                                 ║");
        System.out.println("║             2. Equipamiento requerido                        ║");
        System.out.println("║             3. Servicios requeridos                          ║");
        System.out.println("║             4. Reservaciones para el mismo salón             ║");
        System.out.println("║             5. Servicios del mismo tipo                      ║");
        System.out.println("║             6. Reservaciones del cliente                     ║");
        System.out.println("║             7. Reservaciones con un servicio específico      ║");
        System.out.println("║             8. Reservaciones con un equipo específico        ║");
        System.out.println("║             9. Reservaciones con un montaje específico       ║");
        System.out.println("║             10. Reservaciones en el mismo mes                ║");
        System.out.println("║             11. Reservaciones asignados a un empleado        ║");
        System.out.println("║             12. consultar empleados                          ║");
        System.out.println("║             13. consultar salones                            ║");
        System.out.println("║             14. consultar servicios                          ║");
        System.out.println("║             15. consultar montajes                           ║");
        System.out.println("║             16. consultar eventos                            ║");
        System.out.println("║             17. Salir                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝"); 

        String consultaOption = leer.nextLine();
        switch (consultaOption) {
            case "1":
                ConsultasC.consulta1();
                break;
            case "2":
                ConsultasC.consulta2();
                break;
            case "3":
                Consultas.consulta3();
                break;
            case "4":
                Consultas.consulta4();
                break;
            case "5":
                Consultas.consulta5();
                break;
            case "6":
                Consultas.consulta6();
                break;
            case "7":
                Consultas.consulta7();
                break;
            case "8":
                Consultas.consulta8();
                break;
            case "9":
                Consultas.consulta9();
                break;
            case "10":
                Consultas.consulta10();
                break;
            case "11":
                Consultas.consulta11();
                break;
            case "12":
                Consultas.consulta12();
                break;
            case "13":
                Consultas.consulta13();
                break;
            case "14":
                Consultas.consulta14();
                break;
            case "15":
                Consultas.consulta15();
                break;
            case "16":
                Consultas.consulta16();
                
                break;
            case "17":
                Consultas.consulta17(); 
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }



    private static void insertarEmpleado() {
        Empleados.insertarEmpleado();
    }

    private static void eliminarEmpleado() {
        Empleados.eliminarEmpleado();
    }
    
    private static void añadirSalon() {
       Salones.añadirSalon();
    }

    private static void eliminarSalon() {
       Salones.eliminarSalon();
    }
    
     
    private static void añadirServicio() {
        Servicios.añadirServicio();
    }
    
    private static void eliminarServicio() {
       Servicios.eliminarServicio();
    }
    

    
    private static void añadirMontaje() {
       Montajes.añadirMontaje();
    }

    
    private static void eliminarMontaje() {
        Montajes.eliminarMontaje();
    }

    private static void añadirEvento() {
        try {
            System.out.println("Ingrese la descripción del evento:");
            String descripcion = leer.nextLine();
    
    
            System.out.println("Ingrese el código del salón:");
            int salonCodigo = leer.nextInt();
            leer.nextLine(); 
    
            System.out.println("Ingrese el código del montaje:");
            int montajeCodigo = leer.nextInt();
            leer.nextLine(); 
    
            System.out.println("Ingrese el código del tipo de evento:");
            int tipoEventoCodigo = leer.nextInt();
            leer.nextLine(); 
    
            String insertQuery = "INSERT INTO evento (descripcion, salon, montaje, tipo_evento) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, descripcion);
            pstmt.setInt(2, salonCodigo);
            pstmt.setInt(3, montajeCodigo);
            pstmt.setInt(4, tipoEventoCodigo);
    
            pstmt.executeUpdate();
            System.out.println("Evento añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
    private static void eliminarEvento() {
        try {
            System.out.println("Número de evento a borrar (o 'C' para cancelar):");
            String input = leer.nextLine().toUpperCase(Locale.getDefault());
    
            if (input.equals("C")) {
                System.out.println("Operación cancelada.");
                return;
            }
    
            int numero;
            try {
                numero = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número o 'C' para cancelar.");
                return;
            }
    
            String deleteQuery = "DELETE FROM evento WHERE numeroEvento = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();
    
            System.out.println("Evento eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
