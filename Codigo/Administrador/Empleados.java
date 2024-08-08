package Administrador;

import java.sql.*;
import java.util.*;

public class Empleados {

    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    public static void conectar() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos", "root", "");
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexión con la base de datos.");
        }
    }

    public static void insertarEmpleado() {
        conectar();
        try {
            Scanner leer = new Scanner(System.in);
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



    public static void eliminarEmpleado() {
        conectar();
        Scanner leer = new Scanner(System.in);
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
                    leer.nextLine(); 

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
    
public static void editarEmpleado() {
        conectar();
        Scanner leer = new Scanner(System.in);
        try {
            System.out.println("Número de código del empleado a editar:");
            int numero = leer.nextInt();
            leer.nextLine(); // Consume el salto de línea

            // Verifica si el empleado existe
            String checkEmpleadoQuery = "SELECT * FROM empleados WHERE numero = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkEmpleadoQuery);
            checkStmt.setInt(1, numero);
            ResultSet resultSet = checkStmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Empleado no encontrado.");
                return;
            }

            // Muestra los datos actuales del empleado
            System.out.println("Datos actuales del empleado:");
            System.out.println("Nombre: " + resultSet.getString("nombre"));
            System.out.println("Primer Apellido: " + resultSet.getString("a_paterno"));
            System.out.println("Segundo Apellido: " + resultSet.getString("a_materno"));
            System.out.println("Teléfono: " + resultSet.getString("telefono"));
            System.out.println("Correo: " + resultSet.getString("correo_e"));

            // Solicita los nuevos datos
            System.out.println("Ingrese el nuevo nombre del empleado (o presione Enter para mantener el actual):");
            String nombre = leer.nextLine();
            if (nombre.isEmpty()) nombre = resultSet.getString("nombre");

            System.out.println("Ingrese el nuevo primer apellido del empleado (o presione Enter para mantener el actual):");
            String a_paterno = leer.nextLine();
            if (a_paterno.isEmpty()) a_paterno = resultSet.getString("a_paterno");

            System.out.println("Ingrese el nuevo segundo apellido del empleado (o presione Enter para mantener el actual):");
            String a_materno = leer.nextLine();
            if (a_materno.isEmpty()) a_materno = resultSet.getString("a_materno");

            System.out.println("Ingrese el nuevo teléfono del empleado (o presione Enter para mantener el actual):");
            String telefono = leer.nextLine();
            if (telefono.isEmpty()) telefono = resultSet.getString("telefono");

            System.out.println("Ingrese el nuevo correo del empleado (o presione Enter para mantener el actual):");
            String correo_e = leer.nextLine();
            if (correo_e.isEmpty()) correo_e = resultSet.getString("correo_e");

            // Actualiza los datos del empleado en la base de datos
            String updateQuery = "UPDATE empleados SET nombre = ?, a_paterno = ?, a_materno = ?, telefono = ?, correo_e = ? WHERE numero = ?";
            PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
            updateStmt.setString(1, nombre);
            updateStmt.setString(2, a_paterno);
            updateStmt.setString(3, a_materno);
            updateStmt.setString(4, telefono);
            updateStmt.setString(5, correo_e);
            updateStmt.setInt(6, numero);
            updateStmt.executeUpdate();

            System.out.println("Datos del empleado actualizados exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
