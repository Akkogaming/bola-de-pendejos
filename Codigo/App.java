import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

import Administrador.Admin;
import Consultascarpeta.Consultas;


public class App {
    private static Connection connect;
    static Statement statement;

    public static void main(String[] args) {
        String direction = "jdbc:mysql://localhost:3306/eventos"; 
        
        try {
            connect = DriverManager.getConnection(direction, "root", "");
            statement = connect.createStatement(); 

            boolean isRunning = true;
            Scanner read = new Scanner(System.in); 
            while (isRunning) {
                
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("╔══════════════════════════════════════════════════════════╗"); 
                System.out.println("║                       BLUE PALACE                        ║");
                System.out.println("╠══════════════════════════════════════════════════════════╣");
                System.out.println("║                                                          ║");
                System.out.println("║           ELIGE UNA OPCION DE LAS SIGUIENTES             ║");
                System.out.println("║                                                          ║");
                System.out.println("║        1. iniciar sesion como administrador              ║");
                System.out.println("║        2. iniciar sesion como empleado regular           ║");
                System.out.println("║        3. consultar una reservacion como cliente         ║");
                System.out.println("║        4. otros                                          ║");
                System.out.println("║        5. salir                                          ║"); 
                System.out.println("║                                                          ║");
                System.out.println("╚══════════════════════════════════════════════════════════╝");   

                
                if (read.hasNextLine()) {
                    String answer = read.nextLine().trim().toUpperCase(Locale.getDefault());

                    
                    switch (answer) {
                        case "1":
                            Admin.admin(connect); 
                            break;
                        case "2":
                            Empleado.empleado(connect);
                            break;
                        case "3":
                            handleClientConsultation(read);
                            break;
                        case "4":
                            Otros.otros();
                            break;  
                        case "5":
                            System.out.println("Saliendo del programa...");
                            isRunning = false; 
                            break;        
                        default:
                            System.out.println("Elija una de las opciones correctas por favor");
                            break;
                    }
                } else {
                    System.out.println("No se ha detectado ninguna entrada. El programa se cerrará.");
                    isRunning = false;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            if (connect != null) {
                try {
                    connect.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handleClientConsultation(Scanner read) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗"); 
        System.out.println("║               Selecciona la consulta a realizar:             ║");
        System.out.println("║               1. Reservaciones                               ║");
        System.out.println("║               (tenga su codigo de reservacion en mano)       ║");
        System.out.println("║               2. Salir                                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝"); 

        if (read.hasNextLine()) {
            String consultaOption = read.nextLine().trim();
            switch (consultaOption) {
                case "1":
                    Consultas.consulta6();

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } else {
            System.out.println("No se ha detectado ninguna entrada.");
        }
    }
}
