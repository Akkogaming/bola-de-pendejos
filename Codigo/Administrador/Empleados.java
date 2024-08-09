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

    // Método para validar la longitud de la cadena
    private static boolean validarLongitud(String valor, int maxLongitud) {
        if (valor.length() > maxLongitud) {
            System.out.println("El valor ingresado excede la longitud máxima permitida de " + maxLongitud + " caracteres.");
            return false;
        }
        return true;
    }

    // Método para validar que el valor es un número
    private static boolean esNumero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado debe ser un número.");
            return false;
        }
    }

    public static void insertarEmpleado() {
        conectar();
        try {
            Scanner leer = new Scanner(System.in);

            System.out.println("Ingrese el nombre del empleado (máx. 30 caracteres):");
            String nombre = leer.nextLine();
            if (!validarLongitud(nombre, 30)) return;

            System.out.println("Ingrese el primer apellido del empleado (máx. 30 caracteres):");
            String a_paterno = leer.nextLine();
            if (!validarLongitud(a_paterno, 30)) return;

            System.out.println("Ingrese el segundo apellido del empleado (máx. 30 caracteres):");
            String a_materno = leer.nextLine();
            if (!validarLongitud(a_materno, 30)) return;

            System.out.println("Ingrese el teléfono del empleado (máx. 10 caracteres):");
            String telefono = leer.nextLine();
            if (!validarLongitud(telefono, 10)) return;

            System.out.println("Ingrese el correo del empleado (máx. 50 caracteres):");
            String correo_e = leer.nextLine();
            if (!validarLongitud(correo_e, 50)) return;

            String insertQuery = "INSERT INTO empleados (numero, nombre, a_paterno, a_materno, telefono, correo_e) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setNull(1, java.sql.Types.INTEGER); // Ajusta si 'numero' es autoincremental
            pstmt.setString(2, nombre);
            pstmt.setString(3, a_paterno);
            pstmt.setString(4, a_materno);
            pstmt.setString(5, telefono);
            pstmt.setString(6, correo_e);
            pstmt.executeUpdate();

            System.out.println("Empleado añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
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

            if (!esNumero(input)) return;
            int numero = Integer.parseInt(input);

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
                    String nuevoEmpleadoStr = leer.nextLine();
                    if (!esNumero(nuevoEmpleadoStr)) return;
                    int nuevoEmpleado = Integer.parseInt(nuevoEmpleadoStr);

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
        } finally {
            cerrarConexion();
        }
    }

    public static void editarEmpleado() {
        conectar();
        Scanner leer = new Scanner(System.in);
        try {
            System.out.println("Número de código del empleado a editar:");
            String input = leer.nextLine();

            if (!esNumero(input)) return;
            int numero = Integer.parseInt(input);

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
            System.out.println("Ingrese el nuevo nombre del empleado (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String nombre = leer.nextLine();
            if (!nombre.isEmpty() && !validarLongitud(nombre, 30)) return;

            System.out.println("Ingrese el nuevo primer apellido del empleado (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String a_paterno = leer.nextLine();
            if (!a_paterno.isEmpty() && !validarLongitud(a_paterno, 30)) return;

            System.out.println("Ingrese el nuevo segundo apellido del empleado (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String a_materno = leer.nextLine();
            if (!a_materno.isEmpty() && !validarLongitud(a_materno, 30)) return;

            System.out.println("Ingrese el nuevo teléfono del empleado (máx. 10 caracteres, o presione Enter para mantener el actual):");
            String telefono = leer.nextLine();
            if (!telefono.isEmpty() && !validarLongitud(telefono, 10)) return;

            System.out.println("Ingrese el nuevo correo del empleado (máx. 50 caracteres, o presione Enter para mantener el actual):");
            String correo_e = leer.nextLine();
            if (!correo_e.isEmpty() && !validarLongitud(correo_e, 50)) return;

            // Muestra los nuevos datos ingresados
            System.out.println("Nuevos datos del empleado:");
            System.out.println("Nombre: " + (nombre.isEmpty() ? resultSet.getString("nombre") : nombre));
            System.out.println("Primer Apellido: " + (a_paterno.isEmpty() ? resultSet.getString("a_paterno") : a_paterno));
            System.out.println("Segundo Apellido: " + (a_materno.isEmpty() ? resultSet.getString("a_materno") : a_materno));
            System.out.println("Teléfono: " + (telefono.isEmpty() ? resultSet.getString("telefono") : telefono));
            System.out.println("Correo: " + (correo_e.isEmpty() ? resultSet.getString("correo_e") : correo_e));

            // Pregunta al usuario si desea aceptar los cambios
            System.out.println("¿Desea aceptar los cambios? (s/n):");
            String respuesta = leer.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                // Actualiza los datos del empleado en la base de datos
                String updateQuery = "UPDATE empleados SET nombre = ?, a_paterno = ?, a_materno = ?, telefono = ?, correo_e = ? WHERE numero = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, nombre.isEmpty() ? resultSet.getString("nombre") : nombre);
                updateStmt.setString(2, a_paterno.isEmpty() ? resultSet.getString("a_paterno") : a_paterno);
                updateStmt.setString(3, a_materno.isEmpty() ? resultSet.getString("a_materno") : a_materno);
                updateStmt.setString(4, telefono.isEmpty() ? resultSet.getString("telefono") : telefono);
                updateStmt.setString(5, correo_e.isEmpty() ? resultSet.getString("correo_e") : correo_e);
                updateStmt.setInt(6, numero);
                updateStmt.executeUpdate();

                System.out.println("Datos del empleado actualizados exitosamente.");
            } else {
                System.out.println("Cambios cancelados. Los datos del empleado no han sido modificados.");
            }

            // Retraso de 5 segundos
            System.out.println("Esperando 5 segundos...");
            Thread.sleep(5000);  // Pausa el hilo actual durante 5000 milisegundos (5 segundos)
            System.out.println("¡Tiempo transcurrido!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Manejo de la excepción en caso de que el hilo se interrumpa
            System.err.println("El retraso fue interrumpido.");
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
}
