package Consultascarpeta;

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
            int columnWidth = 26;  // Ajusta el ancho de la columna aquí

            // Generar la fila del encabezado con caracteres de dibujo de caja
            StringBuilder headerRowBuilder = new StringBuilder("╔");
            for (int i = 1; i <= columnCount; i++) {
                headerRowBuilder.append("═".repeat(columnWidth)).append("╦");
            }
            headerRowBuilder.setLength(headerRowBuilder.length() - 1); // Remover el último '╦'
            headerRowBuilder.append("╗");
            System.out.println(headerRowBuilder.toString());

            // Imprimir los nombres de las columnas
            StringBuilder headerNamesBuilder = new StringBuilder("║");
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                headerNamesBuilder.append(String.format("%-" + columnWidth + "s", columnName)).append("║");
            }
            System.out.println(headerNamesBuilder.toString());

            // Generar la fila separadora
            StringBuilder separatorRowBuilder = new StringBuilder("╠");
            for (int i = 1; i <= columnCount; i++) {
                separatorRowBuilder.append("═".repeat(columnWidth)).append("╬");
            }
            separatorRowBuilder.setLength(separatorRowBuilder.length() - 1); // Remover el último '╬'
            separatorRowBuilder.append("╣");
            System.out.println(separatorRowBuilder.toString());

            // Imprimir cada fila de datos
            while (resultSet.next()) {
                StringBuilder dataRowBuilder = new StringBuilder("║");
                for (int i = 1; i <= columnCount; i++) {
                    String cellValue = resultSet.getString(i);
                    cellValue = truncateCellValue(cellValue, columnWidth);
                    dataRowBuilder.append(String.format("%-" + columnWidth + "s", cellValue)).append("║");
                }
                System.out.println(dataRowBuilder.toString());

                // Imprimir el borde inferior de la fila actual
                StringBuilder rowBorderBuilder = new StringBuilder("╠");
                for (int i = 1; i <= columnCount; i++) {
                    rowBorderBuilder.append("═".repeat(columnWidth)).append("╬");
                }
                rowBorderBuilder.setLength(rowBorderBuilder.length() - 1); // Remover el último '╬'
                rowBorderBuilder.append("╣");
                System.out.println(rowBorderBuilder.toString());
            }

            // Imprimir el borde inferior final de la tabla
            StringBuilder bottomBorderBuilder = new StringBuilder("╚");
            for (int i = 1; i <= columnCount; i++) {
                bottomBorderBuilder.append("═".repeat(columnWidth)).append("╝");
            }
            System.out.println(bottomBorderBuilder.toString());

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al ejecutar la consulta: " + e.getMessage());
        } finally {
            // Limpiar los recursos JDBC
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    private static String truncateCellValue(String cellValue, int maxLength) {
        if (cellValue == null) {
            return String.format("%-" + maxLength + "s", "").replace(' ', ' ');
        } else if (cellValue.length() > maxLength) {
            String truncatedValue = cellValue.substring(0, maxLength - 3) + "...";
            return String.format("%-" + maxLength + "s", truncatedValue).replace(' ', ' ');
        } else {
            return String.format("%-" + maxLength + "s", cellValue).replace(' ', ' ');
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
