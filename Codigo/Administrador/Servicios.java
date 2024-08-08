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
            
            Scanner leer = new Scanner(System.in);
            
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
            
            Scanner leer = new Scanner(System.in);
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

    public static void editarServicio() {
        conectar();
        Scanner leer = new Scanner(System.in);
    
        try {
            // Solicitar el código del servicio a editar
            System.out.println("Ingrese el código del servicio que desea editar:");
            int codigoServicio = leer.nextInt();
            leer.nextLine();  // Limpiar el buffer del scanner
    
            // Consultar los datos actuales del servicio
            String selectQuery = "SELECT * FROM servicios WHERE codigo = ?";
            PreparedStatement selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setInt(1, codigoServicio);
            ResultSet resultSet = selectStmt.executeQuery();
    
            if (resultSet.next()) {
                // Mostrar los datos actuales
                System.out.println("Datos actuales del servicio:");
                System.out.println("Nombre: " + resultSet.getString("nombre"));
                System.out.println("Descripción: " + resultSet.getString("descripcion"));
                System.out.println("Precio: " + resultSet.getFloat("precio"));
                System.out.println("Tipo de servicio: " + resultSet.getInt("tipo_servicio"));
    
                // Solicitar los nuevos datos
                System.out.println("Ingrese el nuevo nombre del servicio (o presione Enter para mantener el valor actual):");
                String nombre = leer.nextLine();
                if (nombre.isEmpty()) nombre = resultSet.getString("nombre");
    
                System.out.println("Ingrese la nueva descripción del servicio (o presione Enter para mantener el valor actual):");
                String descripcion = leer.nextLine();
                if (descripcion.isEmpty()) descripcion = resultSet.getString("descripcion");
    
                System.out.println("Ingrese el nuevo costo del servicio (o presione Enter para mantener el valor actual):");
                String input = leer.nextLine();
                float precio = input.isEmpty() ? resultSet.getFloat("precio") : Float.parseFloat(input);
    
                System.out.println("Ingrese el nuevo código del tipo de servicio (1 al 50) (o presione Enter para mantener el valor actual):");
                input = leer.nextLine();
                int tipoServicio = input.isEmpty() ? resultSet.getInt("tipo_servicio") : Integer.parseInt(input);
    
                // Verificar si el tipo de servicio es válido
                String checkTypeServiceQuery = "SELECT codigo FROM tipo_servicio WHERE codigo = ?";
                PreparedStatement checkStmt = connect.prepareStatement(checkTypeServiceQuery);
                checkStmt.setInt(1, tipoServicio);
                ResultSet checkRs = checkStmt.executeQuery();
                if (!checkRs.next()) {
                    throw new SQLException("El tipo de servicio especificado no existe en la base de datos.");
                }
    
                // Actualizar los datos del servicio
                String updateQuery = "UPDATE servicios SET nombre = ?, descripcion = ?, Precio = ?, tipo_servicio = ? WHERE codigo = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, nombre);
                updateStmt.setString(2, descripcion);
                updateStmt.setFloat(3, precio);
                updateStmt.setInt(4, tipoServicio);
                updateStmt.setInt(5, codigoServicio);
    
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Datos del servicio actualizados exitosamente.");
                } else {
                    System.out.println("No se encontró el servicio con el código proporcionado.");
                }
            } else {
                System.out.println("No se encontró el servicio con el código especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
    
}
