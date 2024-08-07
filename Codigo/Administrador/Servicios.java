package Administrador;

import java.sql.*;
import java.util.*;

public class Servicios {
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

    public static void añadirServicio() {
    conectar();
        try {
            
            Integer codigo;
            String nombre, descripcion;
            float precio = 0;
            Integer tipo_servicio;
            
            
            String maxCodeQuery = "SELECT MAX(codigo) AS max_codigo FROM servicios";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(maxCodeQuery);
        
            if (rs.next()) {
                int maxCodigo = rs.getInt("max_codigo");
                codigo = maxCodigo + 1; 
            } else {
                codigo = 1; 
            }
        
            
            System.out.println("Ingrese el nombre del servicio:");
            nombre = leer.nextLine();
        
            
            System.out.println("Ingrese la descripción del servicio:");
            descripcion = leer.nextLine();
        
            
            boolean precioValido = false;
            while (!precioValido) {
                System.out.println("Ingrese el costo del servicio:");
                try {
                    precio = leer.nextFloat();
                    precioValido = true; 
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
                    leer.next(); 
                }
            }
            leer.nextLine(); 
        
            
            System.out.println("Ingrese el código del tipo de servicio (1 al 50):");
            tipo_servicio = leer.nextInt();
            leer.nextLine(); 
        
            
            String checkTypeServiceQuery = "SELECT codigo FROM tipo_servicio WHERE codigo = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkTypeServiceQuery);
            checkStmt.setInt(1, tipo_servicio);
            ResultSet checkRs = checkStmt.executeQuery();
        
            if (!checkRs.next()) {
                throw new SQLException("El tipo de servicio especificado no existe en la base de datos.");
            }
        
            
            String insertQuery = "INSERT INTO servicios (codigo, nombre, descripcion, Precio, tipo_servicio) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setInt(1, codigo);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setFloat(4, precio);
            pstmt.setInt(5, tipo_servicio); 
    
            
            pstmt.executeUpdate();
            System.out.println("Servicio añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            if (leer != null) {
                
            }
        }
    }
    
    
    public static void eliminarServicio() {
        conectar();
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
    
            
            String checkServiciosClienteQuery = "SELECT COUNT(*) FROM servicios_cliente WHERE servicios = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkServiciosClienteQuery);
            checkStmt.setInt(1, numero);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
    
            if (count > 0) {
                System.out.println("El servicio está asociado con uno o más clientes.");
                System.out.println("¿Desea desvincular estos servicios de los clientes? (S/N)");
                String decision = leer.nextLine().toUpperCase(Locale.getDefault());
    
                if (decision.equals("S")) {
                    
                    String deleteFromSCQuery = "DELETE FROM servicios_cliente WHERE servicios = ?";
                    PreparedStatement deleteSCStmt = connect.prepareStatement(deleteFromSCQuery);
                    deleteSCStmt.setInt(1, numero);
                    deleteSCStmt.executeUpdate();
    
                    System.out.println("Asociaciones de servicios con clientes eliminadas.");
    
                    System.out.println("¿Desea eliminar el servicio original? (S/N)");
                    String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                    if (confirmDecision.equals("S")) {
                        String deleteServicioQuery = "DELETE FROM servicios WHERE codigo = ?";
                        PreparedStatement deleteStmt = connect.prepareStatement(deleteServicioQuery);
                        deleteStmt.setInt(1, numero);
                        deleteStmt.executeUpdate();
                        System.out.println("Servicio eliminado exitosamente.");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("¿Desea eliminar el servicio? (S/N)");
                String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
                if (confirmDecision.equals("S")) {
                    String deleteServicioQuery = "DELETE FROM servicios WHERE codigo = ?";
                    PreparedStatement deleteStmt = connect.prepareStatement(deleteServicioQuery);
                    deleteStmt.setInt(1, numero);
                    deleteStmt.executeUpdate();
                    System.out.println("Servicio eliminado exitosamente.");
                } else {
                    System.out.println("Operación cancelada.");
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
