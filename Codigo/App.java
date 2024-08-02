import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private static Connection connect;
    static Statement statement;

    public static void main(String[] args) {
        String direction = "jdbc:mysql://localhost:3306/eventos"; // Cambia el puerto si es necesario
        
        try {
            connect = DriverManager.getConnection(direction, "root", "");
            statement = connect.createStatement(); // Inicializa el statement

            boolean isRunning = true;
            Scanner read = new Scanner(System.in); 
            while (isRunning) {
                // Imprimir menú
                System.out.println("");
                System.out.println("");
                System.out.println("------------------------------------------------------------"); // 60 caracteres
                System.out.println("|                       BLUE PALACE                        |");
                System.out.println("------------------------------------------------------------");
                System.out.println("|                                                          |");
                System.out.println("|           ELIGA UNA OPCION DE LAS SIGUENTES              |");
                System.out.println("|                                                          |");
                System.out.println("|          1. iniciar sesion como administrador            |");
                System.out.println("|         2. iniciar sesion como empleado regular          |");
                System.out.println("|        3. consultar una reservacion como cliente         |");
                System.out.println("|                        4. otros                          |");
                System.out.println("|                        5. salir                          |"); 
                System.out.println("|                                                          |");
                System.out.println("------------------------------------------------------------");   

                // Leer la opción del usuario
                if (read.hasNextLine()) {
                    String answer = read.nextLine().trim().toUpperCase(Locale.getDefault());

                    // Procesar la opción del usuario
                    switch (answer) {
                        case "1":
                            Admin.admin(connect); // Llama al método estático admin
                            break;
                        case "2":
                            Empleado.empleado(connect);
                            break;
                        case "3":
                            handleClientConsultation(read);
                            break;
                        case "4":
                            Otros.Otros();
                            break;  
                        case "5":
                            System.out.println("Saliendo del programa...");
                            isRunning = false; // Termina el bucle y sale del programa
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
            read.close(); // Cerrar el scanner al final
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depurar excepciones
        } finally {
            if (connect != null) {
                try {
                    connect.close(); // Cerrar la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handleClientConsultation(Scanner read) {
        System.out.println("----------------------------------------------------------------"); // 64 caracteres
        System.out.println("|               Selecciona la consulta a realizar:             |");
        System.out.println("|                       1. Reservaciones                       |");
        System.out.println("|                  2. Reservaciones del cliente                |");
        System.out.println("|                           3. Salir                           |");
        System.out.println("----------------------------------------------------------------"); // 64 caracteres

        if (read.hasNextLine()) {
            String consultaOption = read.nextLine().trim();
            switch (consultaOption) {
                case "1":
                    ConsultasC.consulta1();
                    break;
                case "2":
                    ConsultasC.consulta2();
                    break;
                case "3":
                    // No hacer nada, simplemente salimos
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } else {
            System.out.println("No se ha detectado ninguna entrada.");
        }
    }
}