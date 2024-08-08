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

    public static void editarSalones() {
        
    }
    
}
