package Administrador;

import java.sql.*;
import java.util.*;

public class Salones {
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
    public static void añadirSalon() {
        try {
            conectar();
            
            Scanner leer = new Scanner(System.in);

            Integer codigo = null;

            System.out.println("Ingrese la capacidad del salón:");
            int capacidad = leer.nextInt();
            leer.nextLine();

            System.out.println("Ingrese el nombre del salón:");
            String nombresalon = leer.nextLine();

            System.out.println("Ingrese el codigo postal del salón:");
            int codigopostal = leer.nextInt();
            leer.nextLine();

            System.out.println("Ingrese el nombre de la calle del salón:");
            String calle = leer.nextLine();

            System.out.println("Ingrese el nombre de la ciudad del salón:");
            String ciudad = leer.nextLine();

            System.out.println("Ingrese el nombre del estado del salón:");
            String estado = leer.nextLine();

            

            System.out.println("Ingrese el número de empleado asociado al salón:");
            int empleado = leer.nextInt(); 
            leer.nextLine();

            String insertQuery = "INSERT INTO salon (codigo, capacidad, nombresalon, codigopostal, calle, ciudad, estado, empleado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setObject(1, codigo, java.sql.Types.INTEGER);
            pstmt.setInt(2, capacidad);
            pstmt.setString(3,nombresalon);
            pstmt.setInt(4, codigopostal);
            pstmt.setString(5,calle);
            pstmt.setString(6,ciudad);
            pstmt.setString(7,estado);
            pstmt.setInt(8, empleado);
            
            
            

            pstmt.executeUpdate();

            System.out.println("Salón añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void eliminarSalon() {
        conectar();
        try {
            
            Scanner leer = new Scanner(System.in);
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
    
            
            String checkReservasQuery = "SELECT COUNT(*) FROM reservaciones WHERE salon = ?";
            PreparedStatement checkReservasStmt = connect.prepareStatement(checkReservasQuery);
            checkReservasStmt.setInt(1, numero);
            ResultSet reservasResultSet = checkReservasStmt.executeQuery();
            reservasResultSet.next();
            int reservaCount = reservasResultSet.getInt(1);
    
            if (reservaCount > 0) {
                System.out.println("El salón está asociado con una o más reservas.");
                System.out.println("¿Desea reasignar estas reservas a otro salón? (S/N)");
                String decision = leer.nextLine().toUpperCase(Locale.getDefault());
    
                if (decision.equals("S")) {
                    
                    String listSalonesQuery = "SELECT codigo, nombreSalon FROM salon";
                    Statement stmt = connect.createStatement();
                    ResultSet salonesResultSet = stmt.executeQuery(listSalonesQuery);
                    System.out.println("Salones disponibles:");
                    while (salonesResultSet.next()) {
                        int salonCodigo = salonesResultSet.getInt("codigo");
                        String salonNombre = salonesResultSet.getString("nombreSalon");
                        System.out.println(salonCodigo + ": " + salonNombre);
                    }
    
                    System.out.println("Ingrese el número del nuevo salón:");
                    int nuevoSalon = leer.nextInt();
                    leer.nextLine(); 
    
                    
                    String updateReservasQuery = "UPDATE reservaciones SET salon = ? WHERE salon = ?";
                    PreparedStatement updateReservasStmt = connect.prepareStatement(updateReservasQuery);
                    updateReservasStmt.setInt(1, nuevoSalon);
                    updateReservasStmt.setInt(2, numero);
                    updateReservasStmt.executeUpdate();
    
                    System.out.println("Reservas reasignadas al nuevo salón.");
    
                    System.out.println("¿Desea eliminar el salón original? (S/N)");
                    String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                    if (confirmDecision.equals("S")) {
                        String deleteSalonQuery = "DELETE FROM salon WHERE codigo = ?";
                        PreparedStatement deleteStmt = connect.prepareStatement(deleteSalonQuery);
                        deleteStmt.setInt(1, numero);
                        deleteStmt.executeUpdate();
                        System.out.println("Salón eliminado exitosamente.");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("¿Desea eliminar el salón? (S/N)");
                String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                if (confirmDecision.equals("S")) {
                    String deleteSalonQuery = "DELETE FROM salon WHERE codigo = ?";
                    PreparedStatement deleteStmt = connect.prepareStatement(deleteSalonQuery);
                    deleteStmt.setInt(1, numero);
                    deleteStmt.executeUpdate();
                    System.out.println("Salón eliminado exitosamente.");
                } else {
                    System.out.println("Operación cancelada.");
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarSalon() {
        conectar();
        Scanner leer = new Scanner(System.in);
    
        try {
            // Solicitar el código del salón a editar
            System.out.println("Ingrese el código del salón que desea editar:");
            int codigoSalon = leer.nextInt();
            leer.nextLine();  // Limpiar el buffer del scanner
    
            // Consultar los datos actuales del salón
            String selectQuery = "SELECT * FROM salon WHERE codigo = ?";
            PreparedStatement selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setInt(1, codigoSalon);
            ResultSet resultSet = selectStmt.executeQuery();
    
            if (resultSet.next()) {
                // Mostrar los datos actuales
                System.out.println("Datos actuales del salón:");
                System.out.println("Capacidad: " + resultSet.getInt("capacidad"));
                System.out.println("Nombre: " + resultSet.getString("nombresalon"));
                System.out.println("Código Postal: " + resultSet.getInt("codigopostal"));
                System.out.println("Calle: " + resultSet.getString("calle"));
                System.out.println("Ciudad: " + resultSet.getString("ciudad"));
                System.out.println("Estado: " + resultSet.getString("estado"));
                System.out.println("Empleado asociado: " + resultSet.getInt("empleado"));
    
                // Solicitar los nuevos datos
                System.out.println("Ingrese la nueva capacidad (o presione Enter para mantener el valor actual):");
                String input = leer.nextLine();
                int capacidad = input.isEmpty() ? resultSet.getInt("capacidad") : Integer.parseInt(input);
    
                System.out.println("Ingrese el nuevo nombre del salón (o presione Enter para mantener el valor actual):");
                String nombreSalon = leer.nextLine();
                if (nombreSalon.isEmpty()) nombreSalon = resultSet.getString("nombresalon");
    
                System.out.println("Ingrese el nuevo código postal (o presione Enter para mantener el valor actual):");
                input = leer.nextLine();
                int codigoPostal = input.isEmpty() ? resultSet.getInt("codigopostal") : Integer.parseInt(input);
    
                System.out.println("Ingrese la nueva calle (o presione Enter para mantener el valor actual):");
                String calle = leer.nextLine();
                if (calle.isEmpty()) calle = resultSet.getString("calle");
    
                System.out.println("Ingrese la nueva ciudad (o presione Enter para mantener el valor actual):");
                String ciudad = leer.nextLine();
                if (ciudad.isEmpty()) ciudad = resultSet.getString("ciudad");
    
                System.out.println("Ingrese el nuevo estado (o presione Enter para mantener el valor actual):");
                String estado = leer.nextLine();
                if (estado.isEmpty()) estado = resultSet.getString("estado");
    
                System.out.println("Ingrese el nuevo número de empleado asociado (o presione Enter para mantener el valor actual):");
                input = leer.nextLine();
                int empleado = input.isEmpty() ? resultSet.getInt("empleado") : Integer.parseInt(input);
    
                // Actualizar los datos del salón
                String updateQuery = "UPDATE salon SET capacidad = ?, nombresalon = ?, codigopostal = ?, calle = ?, ciudad = ?, estado = ?, empleado = ? WHERE codigo = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setInt(1, capacidad);
                updateStmt.setString(2, nombreSalon);
                updateStmt.setInt(3, codigoPostal);
                updateStmt.setString(4, calle);
                updateStmt.setString(5, ciudad);
                updateStmt.setString(6, estado);
                updateStmt.setInt(7, empleado);
                updateStmt.setInt(8, codigoSalon);
    
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Datos del salón actualizados exitosamente.");
                } else {
                    System.out.println("No se encontraron salones con el código proporcionado.");
                }
            } else {
                System.out.println("No se encontró el salón con el código especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
    
    
}
