
import java.sql.*;
import java.util.*;


public class ConsultasC {
    
    public static void ejecutarConsulta(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos", "root", ""); 
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-40s", metaData.getColumnName(i));
            }
            System.out.println();

            
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-40s", resultSet.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al ejecutar la consulta: " + e.getMessage());
        } finally {
            
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public static void consulta1() {
        Scanner read = new Scanner(System.in);
        int clienteCodigo = -1;

        while (clienteCodigo <= 0) {
            System.out.println("Ingrese el código del cliente");

            try {
                clienteCodigo = read.nextInt();

                
                if (clienteCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "r.HoraI AS Hora_Inicio, " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "te.descripcion AS Tipo_Evento, " +
                "s.nombreSalon AS Nombre_Salon, " +
                "CONCAT(s.calle, ', ', s.ciudad, ', ', s.Estado, ' ', s.codigopostal) AS Direccion_Salon, " +
                "m.descripcion AS Tipo_Montaje, " +
                "r.Cant_Inv AS Cantidad_Invitados, " +
                "r.MontoT AS Costo_Total " +
                "FROM reservaciones r " +
                "JOIN cliente c ON r.cliente = c.codigo " +
                "JOIN evento e ON r.evento = e.numeroEvento " +
                "JOIN salon s ON r.salon = s.codigo " +
                "JOIN tipo_evento te ON e.tipo_evento = te.codigo " +
                "JOIN montaje m ON e.montaje = m.codigo " +
                "WHERE r.cliente = " + clienteCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta2() {
        Scanner read = new Scanner(System.in);
        int clienteCodigo = -1;

        while (clienteCodigo <= 0) {
            System.out.println("Ingrese el código del cliente:");

            try {
                clienteCodigo = read.nextInt();

                
                if (clienteCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "date_format(R.fechaevento, \"%d-%m-%y\") as Fecha, " +
                "s.codigo as Salon, " +
                "tp.descripcion as 'Tipo de evento', " +
                "R.Cant_Inv as 'cantidad de invitados' " +
                "FROM Reservaciones as R " +
                "INNER JOIN cliente as C on R.cliente = c.codigo " +
                "INNER JOIN salon as S on R.salon = s.codigo " +
                "INNER JOIN evento as e on R.evento = e.numeroEvento " +
                "INNER JOIN tipo_evento as tp on e.tipo_evento = tp.codigo " +
                "WHERE C.codigo = " + clienteCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta3(){
        
        
        System.out.println("Cancelando.........");


        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    

    public static void main(String[] args) {
        
        consulta1();
        consulta2();
        consulta3();
       
    }
}
