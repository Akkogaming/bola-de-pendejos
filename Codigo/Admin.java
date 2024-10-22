import java.sql.*;
import java.util.Locale;
import java.util.Scanner;


public class Admin {

    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    // Método estático para inicializar el objeto Admin
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
            System.out.println("------------------------------------------------------------");
            System.out.println("|                 MENU ADMINISTRADOR                       |");
            System.out.println("------------------------------------------------------------");
            System.out.println("|                        1. Consultas                      |");
            System.out.println("|                   2. Eliminar Empleados                  |");
            System.out.println("|                     3. Añadir Salones        *           |");
            System.out.println("|                    4. Añadir Empleados       *           |");
            System.out.println("|                    5. Eliminar Salones       *           |");//
            System.out.println("|                    6. Añadir Servicios       *           |");
            System.out.println("|                   7. Eliminar Servicios      *           |");// 
            System.out.println("|                    8. Añadir Montajes        *           |");
            System.out.println("|                   9. Eliminar Montajes       *           |");//
            System.out.println("|                    10. Añadir Eventos        *           |");
            System.out.println("|                   11. Eliminar Eventos       *           |");//
            System.out.println("|                         12. Salir                        |");
            System.out.println("------------------------------------------------------------");
      
            String answer = leer.nextLine();

            switch (answer) {
                case "1":
                    // Consultas
                    handleConsultas();
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

    // Métodos de consulta
    private static void handleConsultas() {
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
        System.out.println("|           11. Reservaciones asignados a un empleado          |");
        System.out.println("|                   12. consultar empleados                    |");
        System.out.println("|                    13. consultar salones                     |");
        System.out.println("|                   14. consultar servicios                    |");
        System.out.println("|                   15. consultar montajes                     |");
        System.out.println("|                    16. consultar eventos                     |");
        System.out.println("|                           17. Salir                          |");
        System.out.println("----------------------------------------------------------------"); // 64 caracteres

        String consultaOption = leer.nextLine();
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
                Consultas.consulta17(); //YA SE QUE NO EXISTEN AUN NO ESTEN CHINGANDO
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    // Método para insertar un nuevo empleado
    private static void insertarEmpleado() {
        try {
            System.out.println("Ingrese el nombre del empleado:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese el número del empleado:");
            int numero = leer.nextInt();
            leer.nextLine(); // Consume el newline

            String insertQuery = "INSERT INTO empleados (numero, nombre) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setInt(1, numero);
            pstmt.setString(2, nombre);
            pstmt.executeUpdate();

            System.out.println("Empleado añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un empleado por código
    private static void eliminarEmpleado() {
        try {
            System.out.println("Número de código a borrar (o 'C' para cancelar):");
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

            String checkEmpleadoQuery = "SELECT COUNT(*) FROM cliente WHERE empleado = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkEmpleadoQuery);
            checkStmt.setInt(1, numero);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            String checkSalonQuery = "SELECT COUNT(*) FROM salon WHERE empleado = ?";
            PreparedStatement checkSalonStmt = connect.prepareStatement(checkSalonQuery);
            checkSalonStmt.setInt(1, numero);
            ResultSet salonResultSet = checkSalonStmt.executeQuery();
            salonResultSet.next();
            int salonCount = salonResultSet.getInt(1);

            if (count > 0 || salonCount > 0) {
                System.out.println("El empleado está asociado con uno o más registros.");
                System.out.println("¿Desea reasignar estos registros a otro empleado? (S/N)");
                String decision = leer.nextLine().toUpperCase(Locale.getDefault());

                if (decision.equals("S")) {
                    String listEmpleadosQuery = "SELECT numero, nombre FROM empleados";
                    Statement stmt = connect.createStatement();
                    ResultSet empleadosResultSet = stmt.executeQuery(listEmpleadosQuery);
                    System.out.println("Empleados disponibles:");
                    while (empleadosResultSet.next()) {
                        int empNumero = empleadosResultSet.getInt("numero");
                        String empNombre = empleadosResultSet.getString("nombre");
                        System.out.println(empNumero + ": " + empNombre);
                    }

                    System.out.println("Ingrese el número del nuevo empleado:");
                    int nuevoEmpleado = leer.nextInt();
                    leer.nextLine(); // Consume el newline

                    String updateClienteQuery = "UPDATE cliente SET empleado = ? WHERE empleado = ?";
                    PreparedStatement updateClienteStmt = connect.prepareStatement(updateClienteQuery);
                    updateClienteStmt.setInt(1, nuevoEmpleado);
                    updateClienteStmt.setInt(2, numero);
                    updateClienteStmt.executeUpdate();

                    String updateSalonQuery = "UPDATE salon SET empleado = ? WHERE empleado = ?";
                    PreparedStatement updateSalonStmt = connect.prepareStatement(updateSalonQuery);
                    updateSalonStmt.setInt(1, nuevoEmpleado);
                    updateSalonStmt.setInt(2, numero);
                    updateSalonStmt.executeUpdate();

                    System.out.println("Registros reasignados al nuevo empleado.");

                    System.out.println("¿Desea eliminar al empleado original? (S/N)");
                    String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                    if (confirmDecision.equals("S")) {
                        String deleteEmpleadoQuery = "DELETE FROM empleados WHERE numero = ?";
                        PreparedStatement deleteStmt = connect.prepareStatement(deleteEmpleadoQuery);
                        deleteStmt.setInt(1, numero);
                        deleteStmt.executeUpdate();
                        System.out.println("Empleado eliminado exitosamente.");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("¿Desea eliminar al empleado? (S/N)");
                String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                if (confirmDecision.equals("S")) {
                    String deleteEmpleadoQuery = "DELETE FROM empleados WHERE numero = ?";
                    PreparedStatement deleteStmt = connect.prepareStatement(deleteEmpleadoQuery);
                    deleteStmt.setInt(1, numero);
                    deleteStmt.executeUpdate();
                    System.out.println("Empleado eliminado exitosamente.");
                } else {
                    System.out.println("Operación cancelada.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para añadir un salón
    private static void añadirSalon() {
        try {
            System.out.println("Ingrese el nombre del salón:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese el número de salón:");
            int numero = leer.nextInt();
            leer.nextLine(); // Consume el newline

            String insertQuery = "INSERT INTO salon (numero, nombre) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setInt(1, numero);
            pstmt.setString(2, nombre);
            pstmt.executeUpdate();

            System.out.println("Salón añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un salón por número
    private static void eliminarSalon() {
        try {
            System.out.println("Número de salón a borrar (o 'C' para cancelar):");
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

            String deleteQuery = "DELETE FROM salon WHERE numero = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();

            System.out.println("Salón eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para añadir un servicio
    private static void añadirServicio() {
        try {
            System.out.println("Ingrese el nombre del servicio:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese el costo del servicio:");
            double costo = leer.nextDouble();
            leer.nextLine(); // Consume el newline

            String insertQuery = "INSERT INTO servicios (nombre, costo) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, costo);
            pstmt.executeUpdate();

            System.out.println("Servicio añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un servicio
    private static void eliminarServicio() {
        try {
            System.out.println("Número de servicio a borrar (o 'C' para cancelar):");
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

            String deleteQuery = "DELETE FROM servicios WHERE numero = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();

            System.out.println("Servicio eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para añadir un montaje
    private static void añadirMontaje() {
        try {
            System.out.println("Ingrese el nombre del montaje:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese la descripción del montaje:");
            String descripcion = leer.nextLine();

            String insertQuery = "INSERT INTO montajes (nombre, descripcion) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.executeUpdate();

            System.out.println("Montaje añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un montaje
    private static void eliminarMontaje() {
        try {
            System.out.println("Número de montaje a borrar (o 'C' para cancelar):");
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

            String deleteQuery = "DELETE FROM montajes WHERE numero = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();

            System.out.println("Montaje eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para añadir un evento
    private static void añadirEvento() {
        try {
            System.out.println("Ingrese el nombre del evento:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese la fecha del evento (YYYY-MM-DD):");
            String fecha = leer.nextLine();

            String insertQuery = "INSERT INTO eventos (nombre, fecha) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, nombre);
            pstmt.setDate(2, Date.valueOf(fecha));
            pstmt.executeUpdate();

            System.out.println("Evento añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un evento
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

            String deleteQuery = "DELETE FROM eventos WHERE numero = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();

            System.out.println("Evento eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
