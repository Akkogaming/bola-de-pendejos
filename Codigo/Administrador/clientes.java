package Administrador;

import java.sql.*;
import java.util.*;

public class clientes {

    private static Connection connect;
    private static Scanner leer = new Scanner(System.in);

    public static void conectar() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos", "root", "");
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }
    }

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

    private static boolean validarLongitud(String valor, int maxLongitud) {
        if (valor.length() > maxLongitud) {
            System.out.println("El valor ingresado excede la longitud máxima permitida de " + maxLongitud + " caracteres.");
            return false;
        }
        return true;
    }

    private static boolean esNumero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado debe ser un número.");
            return false;
        }
    }

    public static void insertarCliente() {
        conectar();
        try {
            System.out.println("Ingrese el nombre del cliente (máx. 30 caracteres):");
            String nombre = leer.nextLine();
            if (!validarLongitud(nombre, 30)) return;

            System.out.println("Ingrese el primer apellido del cliente (máx. 30 caracteres):");
            String a_paterno = leer.nextLine();
            if (!validarLongitud(a_paterno, 30)) return;

            System.out.println("Ingrese el segundo apellido del cliente (máx. 30 caracteres, opcional):");
            String a_materno = leer.nextLine();
            if (!a_materno.isEmpty() && !validarLongitud(a_materno, 30)) return;

            System.out.println("Ingrese el teléfono del cliente (máx. 10 caracteres):");
            String telefono = leer.nextLine();
            if (!validarLongitud(telefono, 10)) return;

            System.out.println("Ingrese el número de empleado asociado:");
            String empleadoStr = leer.nextLine();
            if (!esNumero(empleadoStr)) return;
            int empleado = Integer.parseInt(empleadoStr);

            String insertQuery = "INSERT INTO cliente (nombre, apaterno, amaterno, telefono, empleado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, nombre);
            pstmt.setString(2, a_paterno);
            pstmt.setString(3, a_materno.isEmpty() ? null : a_materno);
            pstmt.setString(4, telefono);
            pstmt.setInt(5, empleado);
            pstmt.executeUpdate();

            System.out.println("Cliente añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void eliminarCliente() {
        conectar();
        try {
            System.out.println("Código del cliente a borrar (o 'C' para cancelar):");
            String input = leer.nextLine().toUpperCase(Locale.getDefault());

            if (input.equals("C")) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (!esNumero(input)) return;
            int codigo = Integer.parseInt(input);

            String checkClienteQuery = "SELECT COUNT(*) FROM cliente WHERE codigo = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkClienteQuery);
            checkStmt.setInt(1, codigo);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("¿Desea eliminar al cliente? (S/N)");
                String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                if (confirmDecision.equals("S")) {
                    String deleteClienteQuery = "DELETE FROM cliente WHERE codigo = ?";
                    PreparedStatement deleteStmt = connect.prepareStatement(deleteClienteQuery);
                    deleteStmt.setInt(1, codigo);
                    deleteStmt.executeUpdate();
                    System.out.println("Cliente eliminado exitosamente.");
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("No se encontró el cliente con el código especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void editarCliente() {
        conectar();
        try {
            System.out.println("Código del cliente a editar:");
            String input = leer.nextLine();

            if (!esNumero(input)) return;
            int codigo = Integer.parseInt(input);

            String checkClienteQuery = "SELECT * FROM cliente WHERE codigo = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkClienteQuery);
            checkStmt.setInt(1, codigo);
            ResultSet resultSet = checkStmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            System.out.println("Datos actuales del cliente:");
            System.out.println("Nombre: " + resultSet.getString("nombre"));
            System.out.println("Primer Apellido: " + resultSet.getString("apaterno"));
            System.out.println("Segundo Apellido: " + resultSet.getString("amaterno"));
            System.out.println("Teléfono: " + resultSet.getString("telefono"));
            System.out.println("Empleado asociado: " + resultSet.getInt("empleado"));

            System.out.println("Ingrese el nuevo nombre del cliente (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String nombre = leer.nextLine();
            if (!nombre.isEmpty() && !validarLongitud(nombre, 30)) return;

            System.out.println("Ingrese el nuevo primer apellido del cliente (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String a_paterno = leer.nextLine();
            if (!a_paterno.isEmpty() && !validarLongitud(a_paterno, 30)) return;

            System.out.println("Ingrese el nuevo segundo apellido del cliente (máx. 30 caracteres, o presione Enter para mantener el actual):");
            String a_materno = leer.nextLine();
            if (!a_materno.isEmpty() && !validarLongitud(a_materno, 30)) return;

            System.out.println("Ingrese el nuevo teléfono del cliente (máx. 10 caracteres, o presione Enter para mantener el actual):");
            String telefono = leer.nextLine();
            if (!telefono.isEmpty() && !validarLongitud(telefono, 10)) return;

            System.out.println("Ingrese el nuevo número de empleado asociado (o presione Enter para mantener el actual):");
            String empleadoStr = leer.nextLine();
            int empleado = empleadoStr.isEmpty() ? resultSet.getInt("empleado") : Integer.parseInt(empleadoStr);

            System.out.println("Nuevos datos del cliente:");
            System.out.println("Nombre: " + (nombre.isEmpty() ? resultSet.getString("nombre") : nombre));
            System.out.println("Primer Apellido: " + (a_paterno.isEmpty() ? resultSet.getString("apaterno") : a_paterno));
            System.out.println("Segundo Apellido: " + (a_materno.isEmpty() ? resultSet.getString("amaterno") : a_materno));
            System.out.println("Teléfono: " + (telefono.isEmpty() ? resultSet.getString("telefono") : telefono));
            System.out.println("Empleado asociado: " + empleado);

            System.out.println("¿Desea aceptar los cambios? (s/n):");
            String respuesta = leer.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                String updateQuery = "UPDATE cliente SET nombre = ?, apaterno = ?, amaterno = ?, telefono = ?, empleado = ? WHERE codigo = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, nombre.isEmpty() ? resultSet.getString("nombre") : nombre);
                updateStmt.setString(2, a_paterno.isEmpty() ? resultSet.getString("apaterno") : a_paterno);
                updateStmt.setString(3, a_materno.isEmpty() ? resultSet.getString("amaterno") : a_materno);
                updateStmt.setString(4, telefono.isEmpty() ? resultSet.getString("telefono") : telefono);
                updateStmt.setInt(5, empleado);
                updateStmt.setInt(6, codigo);
                updateStmt.executeUpdate();

                System.out.println("Datos del cliente actualizados exitosamente.");
            } else {
                System.out.println("Cambios cancelados. Los datos del cliente no han sido modificados.");
            }

            System.out.println("Esperando 5 segundos...");
            Thread.sleep(5000);  // Pausa el hilo actual durante 5000 milisegundos (5 segundos)
            System.out.println("¡Tiempo transcurrido!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("El retraso fue interrumpido.");
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
}
