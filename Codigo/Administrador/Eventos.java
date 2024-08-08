package Administrador;

import java.sql.*;
import java.util.*;

public class Eventos {
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

    public static void añadirEvento() {
        conectar();
        try {
            
            Scanner leer = new Scanner(System.in);
            System.out.println("Ingrese la descripción del evento:");
            String descripcion = leer.nextLine();
    
    
            System.out.println("Ingrese el código del salón:");
            int salonCodigo = leer.nextInt();
            leer.nextLine(); 
    
            System.out.println("Ingrese el código del montaje:");
            int montajeCodigo = leer.nextInt();
            leer.nextLine(); 
    
            System.out.println("Ingrese el código del tipo de evento:");
            int tipoEventoCodigo = leer.nextInt();
            leer.nextLine(); 
    
            String insertQuery = "INSERT INTO evento (descripcion, salon, montaje, tipo_evento) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, descripcion);
            pstmt.setInt(2, salonCodigo);
            pstmt.setInt(3, montajeCodigo);
            pstmt.setInt(4, tipoEventoCodigo);
    
            pstmt.executeUpdate();
            System.out.println("Evento añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
    public static void eliminarEvento() {
        conectar();
        try {
            
            Scanner leer = new Scanner(System.in);
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
    
            String deleteQuery = "DELETE FROM evento WHERE numeroEvento = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();
    
            System.out.println("Evento eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarEvento() {
        conectar();
        Scanner leer = new Scanner(System.in);
    
        try {
            // Solicitar el código del evento a editar
            System.out.println("Ingrese el número del evento que desea editar:");
            int numeroEvento = leer.nextInt();
            leer.nextLine();  // Limpiar el buffer del scanner
    
            // Consultar los datos actuales del evento
            String selectQuery = "SELECT * FROM evento WHERE numeroEvento = ?";
            PreparedStatement selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setInt(1, numeroEvento);
            ResultSet resultSet = selectStmt.executeQuery();
    
            if (resultSet.next()) {
                // Mostrar los datos actuales
                System.out.println("Datos actuales del evento:");
                System.out.println("Descripción: " + resultSet.getString("descripcion"));
                System.out.println("Código del salón: " + resultSet.getInt("salon"));
                System.out.println("Código del montaje: " + resultSet.getInt("montaje"));
                System.out.println("Código del tipo de evento: " + resultSet.getInt("tipo_evento"));
    
                // Solicitar los nuevos datos
                System.out.println("Ingrese la nueva descripción del evento (o presione Enter para mantener el valor actual):");
                String descripcion = leer.nextLine();
                if (descripcion.isEmpty()) descripcion = resultSet.getString("descripcion");
    
                System.out.println("Ingrese el nuevo código del salón (o presione Enter para mantener el valor actual):");
                String input = leer.nextLine();
                int salonCodigo = input.isEmpty() ? resultSet.getInt("salon") : Integer.parseInt(input);
    
                System.out.println("Ingrese el nuevo código del montaje (o presione Enter para mantener el valor actual):");
                input = leer.nextLine();
                int montajeCodigo = input.isEmpty() ? resultSet.getInt("montaje") : Integer.parseInt(input);
    
                System.out.println("Ingrese el nuevo código del tipo de evento (o presione Enter para mantener el valor actual):");
                input = leer.nextLine();
                int tipoEventoCodigo = input.isEmpty() ? resultSet.getInt("tipo_evento") : Integer.parseInt(input);
    
                // Actualizar los datos del evento
                String updateQuery = "UPDATE evento SET descripcion = ?, salon = ?, montaje = ?, tipo_evento = ? WHERE numeroEvento = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, descripcion);
                updateStmt.setInt(2, salonCodigo);
                updateStmt.setInt(3, montajeCodigo);
                updateStmt.setInt(4, tipoEventoCodigo);
                updateStmt.setInt(5, numeroEvento);
    
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Datos del evento actualizados exitosamente.");
                } else {
                    System.out.println("No se encontró el evento con el número proporcionado.");
                }
            } else {
                System.out.println("No se encontró el evento con el número especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
    
}
    


