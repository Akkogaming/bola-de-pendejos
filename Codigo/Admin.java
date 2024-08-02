import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

//la putamadre luis, usa variables que existan en la base de datos

public class Admin {

    private static Connection connect;
    private static Statement statement;
    private static Scanner leer;

    // Método estático para inicializar el objeto Admin
    public static void admin(Connection conn) {
        connect = conn;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        leer = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                 MENU ADMINISTRADOR                       |");
            System.out.println("------------------------------------------------------------");
            System.out.println("| 1. Consultas                                             |");
            System.out.println("| 2. Eliminar Empleados                                    |");
            System.out.println("| 3. Añadir Salones                                        |");
            System.out.println("| 4. Añadir Empleados                                      |");
            System.out.println("| 5. Eliminar Salones                                      |");
            System.out.println("| 6. Añadir Servicios                                      |");
            System.out.println("| 7. Eliminar Servicios                                    |");
            System.out.println("| 8. Añadir Montajes                                       |");
            System.out.println("| 9. Eliminar Montajes                                     |");
            System.out.println("| 10. Añadir Eventos                                       |");
            System.out.println("| 11. Eliminar Eventos                                     |");
            System.out.println("| 12. Salir                                                |");
            System.out.println("------------------------------------------------------------");

            String answer = leer.nextLine().toUpperCase(Locale.getDefault());

            switch (answer) {
                case "1":
                    
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
                                default:
                                    Consultas.consulta11();
                            }
                            break;

                    

                case "2":
                    eliminarEmpleado();
                    break;

                case "3":
                    añadirSalon();
                    break;

                case "4":
                    insertarEmpleado();
                    break;

                // Implementar otros casos aquí para las opciones E, F, G, H, I, J, K

                case "5":
                    isRunning = false;
                    System.out.println("Saliendo del menú de administrador.");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        leer.close();
    }

    // Método para insertar un nuevo empleado
    private static void insertarEmpleado() {
        try {
            System.out.println("Insertar un nuevo empleado");

            System.out.println("Nombre:");
            String nombre = leer.nextLine();

            System.out.println("Apellido paterno:");
            String a_paterno = leer.nextLine();

            System.out.println("Apellido materno:");
            String a_materno = leer.nextLine();

            System.out.println("Teléfono:");
            String telefono = leer.nextLine();

            System.out.println("Correo electrónico:");
            String correo_e = leer.nextLine();

            String comando = "INSERT INTO empleados(nombre, A_paterno, A_materno, telefono, correo_E) VALUES ('" +
                    nombre + "', '" + a_paterno + "', '" + a_materno + "', '" + telefono + "', '" + correo_e + "')";

            statement.executeUpdate(comando);
            System.out.println("Empleado insertado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un empleado por código
    private static void eliminarEmpleado() {
        try {
            System.out.println("Número de código a borrar:");
            int numero = leer.nextInt();
            leer.nextLine(); // Consume el newline

            System.out.println("------------------------------------------------------------");
            System.out.println("|                     ¿ESTA SEGURO?                        |");
            System.out.println("|           ASEGURECE QUE EL NUMERO SEA CORRECTO           |");
            System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
            System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
            System.out.println("------------------------------------------------------------");

            String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
            if (confirmDecision.equals("Y")) {
                String comando = "DELETE FROM empleados WHERE numero = " + numero;
                statement.executeUpdate(comando);
                System.out.println("Empleado eliminado exitosamente.");
            } else if (confirmDecision.equals("N")) {
                System.out.println("Operación cancelada.");
            } else {
                System.out.println("Selección no válida. Debe ingresar Y o N.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para añadir un salón
    private static void añadirSalon() {
        try {
            boolean addingEmployee = false;
            do {
                System.out.println("------------------------------------------------------------");
                System.out.println("|       INGRESE LOS DATOS DEL SALON QUE DESEE AÑADIR       |");
                System.out.println("|       Y EL NUMERO DEL EMPLEADO ENCARGADO DEL SALON       |");
                System.out.println("------------------------------------------------------------");

                System.out.println("------------------------------------------------------------");
                System.out.println("|     2)SALIR                                 1)AÑADIR     |");
                System.out.println("------------------------------------------------------------");

                String select = leer.nextLine().toUpperCase(Locale.getDefault());
                if (select.equals("2")) {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("|               REGRESANDO AL MENU DE ADMIN                |");
                    System.out.println("------------------------------------------------------------");
                    break;
                } else if (select.equals("1")) {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("|              INGRESE EL NOMBRE DEL SALON                 |");
                    System.out.println("------------------------------------------------------------");
                    String nombreSalon = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|           INGRESE LA CAPACIDAD MAXIMA DE PERSONAS        |");
                    System.out.println("|              QUE PUEDE HABER DENTRO DEL SALON            |");
                    System.out.println("------------------------------------------------------------");
                    String capacidad = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|           INGRESE EL CODIGO POSTAL DEL SALON             |");
                    System.out.println("------------------------------------------------------------");
                    String codigopostal = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|       INGRESE LA CALLE DONDE SE ENCUENTRA EL SALON       |");
                    System.out.println("------------------------------------------------------------");
                    String calle = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|       INGRESE LA CIUDAD DONDE SE ENCUENTRA EL SALON      |");
                    System.out.println("------------------------------------------------------------");
                    String ciudad = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|       INGRESE EL ESTADO DONDE SE ENCUENTRA EL SALON      |");
                    System.out.println("------------------------------------------------------------");
                    String estado = leer.nextLine();

                    System.out.println("------------------------------------------------------------");
                    System.out.println("|    INGRESE EL NUMERO DE EMPLEADO ENCARGADO DEL SALON     |");
                    System.out.println("------------------------------------------------------------");
                    String empleado = leer.nextLine();

                    String comando = "INSERT INTO salon(nombreSalon, capacidad, codigopostal, calle, ciudad, Estado, empleado) VALUES ('" +
                    nombreSalon + "', " + capacidad + ", '" + codigopostal + "', '" + calle + "', '" + ciudad + "', '" + estado + "', " + empleado + ")";

                    statement.executeUpdate(comando);
                    System.out.println("------------------------------------------------------------");
                    System.out.println("|                        PERFECTO                          |");
                    System.out.println("|                      SALON AÑADIDO                       |");
                    System.out.println("------------------------------------------------------------");
                    addingEmployee = true;
                } else {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("|             POR FAVOR INGRESE UN VALOR VALIDO            |");
                    System.out.println("------------------------------------------------------------");
                }
            } while (!addingEmployee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
