import java.sql.*;
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
                


                //System.out.print("\033[H\033[2J");
    
                // Imprimir menú
                System.out.println("");
                System.out.println("");
                System.out.println("------------------------------------------------------------"); // 60 caracteres
                System.out.println("|                       BLUE PALACE                        |");
                System.out.println("------------------------------------------------------------");
                System.out.println("|           ELIGA UNA OPCION DE LAS SIGUENTES              |");
                System.out.println("|                                                          |");
                System.out.println("|          1. iniciar sesion como administrador            |");
                System.out.println("|         2. iniciar sesion como empleado regular          |");
                System.out.println("|        3. consultar una reservacion como cliente         |");
                System.out.println("|                        4. otros                          |");
                System.out.println("|                        5. salir                          |"); 
                System.out.println("|                                                          |");
                System.out.println("------------------------------------------------------------");   
                //System.out.flush();
                // Leer la opción del usuario de manera segura
                if (read.hasNextLine()) {
                    String answer = read.nextLine().toUpperCase(Locale.getDefault());

                    // Procesar la opción del usuario
                    switch (answer) {
                        // Llama al método Admin
                        case "1":
                            Admin admin = new Admin(connect);
                            admin.Admin();
                            break;
                        case "2":
                            // Manejar el caso de iniciar sesión como empleado regular
                            

                            System.out.println("Ingrese el nombre del empleado:");
                            String nombre = read.nextLine();

                            System.out.println("Ingrese el apellido paterno del empleado:");
                            String A_paterno = read.nextLine();

                            System.out.println("Ingrese el apellido materno del empleado:");
                            String A_materno = read.nextLine();

                            System.out.println("Ingrese el teléfono del empleado:");
                            String telefono = read.nextLine();

                            System.out.println("Ingrese el correo electrónico del empleado:");
                            String correo_E = read.nextLine();

                            // Crear una nueva instancia de Empleado
                            Empleado empleado = new Empleado(nombre, A_paterno, A_materno, telefono, correo_E);

                            // Mostrar los detalles del empleado creado
                            System.out.println("Empleado creado:");
                            System.out.println("Nombre: " + empleado.getNombre());
                            System.out.println("Apellido Paterno: " + empleado.getA_paterno());
                            System.out.println("Apellido Materno: " + empleado.getA_materno());
                            System.out.println("Teléfono: " + empleado.getTelefono());
                            System.out.println("Correo Electrónico: " + empleado.getCorreo_E());
                            break;
                        case "3":
                            System.out.println("----------------------------------------------------------------"); // 64 caracteres
                            System.out.println("|               Selecciona la consulta a realizar:             |");
                            System.out.println("|                       1. Reservaciones                       |");
                            System.out.println("|                   2. Equipamiento requerido                  |");
                            System.out.println("|                     3. Servicios requeridos                  |");
                            System.out.println("|             4. Reservaciones para el mismo salón             |");
                            System.out.println("|                  5. Servicios del mismo tipo                 |");
                            System.out.println("|                  6. Reservaciones del cliente                |");
                            System.out.println("|           7. Reservaciones con un servicio específico        |");
                            System.out.println("|            8. Reservaciones con un equipo específico         |");
                            System.out.println("|           9. Reservaciones con un montaje específico         |");
                            System.out.println("|               10. Reservaciones en el mismo mes              |");
                            System.out.println("|                           11. Salir                          |");
                            System.out.println("----------------------------------------------------------------"); // 64 caracteres

                            String consultaOption = read.nextLine();
                            switch (consultaOption) {
                                case "1":
                                    Consultas.consulta1();
                                    break;
                                case "2":
                                    Consultas.consulta2();
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
                                default:
                                    Consultas.consulta11();
                            }
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
}
