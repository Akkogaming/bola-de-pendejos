
import java.sql.*;
import java.util.*;


public class ConsultasE {
    
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
            return String.format("%-" + maxLength + "s", "").replace(' ', '.');
        } else if (cellValue.length() > maxLength) {
            String truncatedValue = cellValue.substring(0, maxLength - 3) + "...";
            return String.format("%-" + maxLength + "s", truncatedValue).replace(' ', '.');
        } else {
            return String.format("%-" + maxLength + "s", cellValue).replace(' ', '.');
        }
    }

    
    public static void consulta1E() {
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

    public static void consulta2E() {
        Scanner read = new Scanner(System.in);
        int equipamientoCodigo = -1;

        while (equipamientoCodigo <= 0) {
            System.out.println("Ingrese el código del equipamiento :");

            try {
                equipamientoCodigo = read.nextInt();

                
                if (equipamientoCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "s.nombreSalon AS Nombre_Salon, " +
                "eq.nombre AS Descripcion, " +
                "eq.precio AS Costo_Equipo " +
                "FROM equipo_reservado er " +
                "JOIN reservaciones r ON er.reservacion = r.codigo " +
                "JOIN cliente c ON r.cliente = c.codigo " +
                "JOIN salon s ON r.salon = s.codigo " +
                "JOIN equipamiento eq ON er.equipamiento = eq.codigo " +
                "WHERE eq.codigo = " + equipamientoCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta3E() {
        Scanner read = new Scanner(System.in);
        int tipoServicioCodigo = -1;

        while (tipoServicioCodigo <= 0) {
            System.out.println("Ingrese el código del tipo de servicio:");

            try {
                tipoServicioCodigo = read.nextInt();

                
                if (tipoServicioCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "s.nombreSalon AS Nombre_Salon, " +
                "sv.descripcion AS Descripcion_Servicio, " +
                "ts.descripcion AS Tipo_Servicio, " +
                "sv.Precio AS Costo_Servicio " +
                "FROM servicios_cliente sc " +
                "JOIN reservaciones r ON sc.cliente = r.cliente " +
                "JOIN servicios sv ON sc.servicios = sv.codigo " +
                "JOIN tipo_servicio ts ON sv.tipo_servicio = ts.codigo " +
                "JOIN cliente c ON r.cliente = c.codigo " +
                "JOIN salon s ON r.salon = s.codigo " +
                "WHERE ts.codigo = " + tipoServicioCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta4E() {
        Scanner read = new Scanner(System.in);
        int salonCodigo = -1;

        while (salonCodigo <= 0) {
            System.out.println("Ingrese el código del salón:");

            try {
                salonCodigo = read.nextInt();

                
                if (salonCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "s.nombreSalon AS Nombre_Salon, " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "te.descripcion AS Tipo_Evento " +
                "FROM reservaciones r " +
                "JOIN salon s ON r.salon = s.codigo " +
                "JOIN cliente c ON r.cliente = c.codigo " +
                "JOIN evento e ON r.evento = e.numeroEvento " +
                "JOIN tipo_evento te ON e.tipo_evento = te.codigo " +
                "WHERE s.codigo = " + salonCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta5E() {
        Scanner read = new Scanner(System.in);
        int tipoServicioCodigo = -1;

        while (tipoServicioCodigo <= 0) {
            System.out.println("Ingrese el código del tipo de servicio:");

            try {
                tipoServicioCodigo = read.nextInt();

                
                if (tipoServicioCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
        String query = "SELECT " +
                "ts.descripcion AS Descripcion_Tipo_Servicio, " +
                "sv.descripcion AS Descripcion_Servicio, " +
                "sv.Precio AS Precio " +
                "FROM servicios sv " +
                "JOIN tipo_servicio ts ON sv.tipo_servicio = ts.codigo " +
                "WHERE ts.codigo = " + tipoServicioCodigo;
        
        
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }
    public static void consulta11(){
        
        
        System.out.println("Cancelando.........");


        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }


    }


    public static void main(String[] args) {
        
        consulta1E();
        consulta2E();
        consulta3E();
        consulta4E();
        consulta5E();
        consulta11();
    }
}
