package Administrador;

import java.sql.*;
import java.util.*;

public class Montajes {
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

    public static void añadirMontaje() {
        conectar();
        try {
            Integer codigo=null;

            System.out.println("Ingrese la descripción del montaje:");
            String descripcion = leer.nextLine();

            String insertQuery = "INSERT INTO montaje (codigo, descripcion) VALUES (?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setObject(1, codigo, java.sql.Types.INTEGER);
            pstmt.setString(2, descripcion);
            pstmt.executeUpdate();

            System.out.println("Montaje añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void eliminarMontaje() {
        conectar();
        try {
            System.out.println("Número de montaje a borrar (o 'C' para cancelar):");
            String input = leer.nextLine().toUpperCase(Locale.getDefault());
    
            if (input.equals("C")) {
                System.out.println("Operación cancelada.");
                return;
            }
    
            int codigo;
            try {
                codigo = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número o 'C' para cancelar.");
                return;
            }
    
            String checkEventoQuery = "SELECT COUNT(*) FROM evento WHERE montaje = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkEventoQuery);
            checkStmt.setInt(1, codigo);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
    
            if (count > 0) {
                System.out.println("El montaje está asociado con uno o más eventos.");
                System.out.println("Ingrese el número del nuevo montaje:");
                int nuevoMontaje = leer.nextInt();
                leer.nextLine(); 
    
                String updateEventoQuery = "UPDATE evento SET montaje = ? WHERE montaje = ?";
                PreparedStatement updateEventoStmt = connect.prepareStatement(updateEventoQuery);
                updateEventoStmt.setInt(1, nuevoMontaje);
                updateEventoStmt.setInt(2, codigo);
                updateEventoStmt.executeUpdate();
    
                System.out.println("Eventos reasignados al nuevo montaje.");
            }
    
            String deleteMontajeQuery = "DELETE FROM montaje WHERE codigo = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteMontajeQuery);
            pstmt.setInt(1, codigo);
            pstmt.executeUpdate();
    
            System.out.println("Montaje eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
