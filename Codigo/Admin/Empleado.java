package Admin;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Empleado {
    private static Connection connect;
    private static Statement statement;
    private static Scanner leer;

    // Método estático para inicializar la conexión a la base de datos y el objeto Scanner
    public static boolean initializeDatabase() {
        String direction = "jdbc:mysql://localhost:3306/eventos"; // Cambia el puerto si es necesario
        try {
            connect = DriverManager.getConnection(direction, "root", "");
            statement = connect.createStatement(); // Inicializa el statement
            leer = new Scanner(System.in);  // Inicializa el objeto Scanner aquí
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir el stack trace para depurar excepciones
            return false;
        }
    }

    // Método para insertar un nuevo empleado
    public static void insertarEmpleado() {
        if (connect == null) {
            System.out.println("La conexión a la base de datos no está inicializada.");
            return;
        }

        if (leer == null) {
            System.out.println("El objeto Scanner no está inicializado.");
            return;
        }

        try {
            Integer numero = null;

            System.out.println("Ingrese el nombre del empleado:");
            String nombre = leer.nextLine();

            System.out.println("Ingrese el primer apellido del empleado:");
            String a_paterno = leer.nextLine();

            System.out.println("Ingrese el segundo apellido del empleado:");
            String a_materno = leer.nextLine();

            System.out.println("Ingrese el telefono del empleado:");
            String telefono = leer.nextLine();

            System.out.println("Ingrese el correo del empleado:");
            String correo_e = leer.nextLine();

            String insertQuery = "INSERT INTO empleados (numero, nombre, a_paterno, a_materno, telefono, correo_e) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setObject(1, numero, java.sql.Types.INTEGER);
            pstmt.setString(2, nombre);
            pstmt.setString(3, a_paterno);
            pstmt.setString(4, a_materno);
            pstmt.setString(5, telefono);
            pstmt.setString(6, correo_e);
            pstmt.executeUpdate();

            System.out.println("Empleado añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un empleado
    public static void eliminarEmpleado() {
        if (connect == null) {
            System.out.println("La conexión a la base de datos no está inicializada.");
            return;
        }

        if (leer == null) {
            System.out.println("El objeto Scanner no está inicializado.");
            return;
        }

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

    // Método para cerrar la conexión a la base de datos
    public static void closeConnection() {
        if (connect != null) {
            try {
                connect.close(); // Cerrar la conexión
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
