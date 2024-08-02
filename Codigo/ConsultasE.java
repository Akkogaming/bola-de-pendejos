import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

//consultas para el empleado y el admin
public class ConsultasE {
    // Método para ejecutar y mostrar una consulta SQL
    public static void ejecutarConsulta(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos", "root", ""); //puerto
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Imprimir encabezados de columna
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-40s", metaData.getColumnName(i));
            }
            System.out.println();

            // Imprimir datos de filas
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-40s", resultSet.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al ejecutar la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally para asegurarse de que se cierren siempre
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public static void consulta1E() {
        Scanner read = new Scanner(System.in);
        int clienteCodigo = -1;

        while (clienteCodigo <= 0) {
            System.out.println("Ingrese el código del cliente");

            try {
                clienteCodigo = read.nextInt();

                // Validar el código ingresado
                if (clienteCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); // Limpiar el buffer del scanner
            }
        }

        // Preparar la consulta SQL
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
        
        // Ejecutar la consulta y manejar posibles errores
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

                // Validar el código ingresado
                if (equipamientoCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número");
                read.next(); // Limpiar el buffer del scanner
            }
        }

        // Preparar la consulta SQL
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
        
        // Ejecutar la consulta y manejar posibles errores
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

                // Validar el código ingresado
                if (tipoServicioCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número");
                read.next(); // Limpiar el buffer del scanner
            }
        }

        // Preparar la consulta SQL
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
        
        // Ejecutar la consulta y manejar posibles errores
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

                // Validar el código ingresado
                if (salonCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); // Limpiar el buffer del scanner
            }
        }

        // Preparar la consulta SQL
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
        
        // Ejecutar la consulta y manejar posibles errores
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

                // Validar el código ingresado
                if (tipoServicioCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); // Limpiar el buffer del scanner
            }
        }

        // Preparar la consulta SQL
        String query = "SELECT " +
                "ts.descripcion AS Descripcion_Tipo_Servicio, " +
                "sv.descripcion AS Descripcion_Servicio, " +
                "sv.Precio AS Precio " +
                "FROM servicios sv " +
                "JOIN tipo_servicio ts ON sv.tipo_servicio = ts.codigo " +
                "WHERE ts.codigo = " + tipoServicioCodigo;
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }
    public static boolean consulta11() {
        System.out.println("Cancelando.........");
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return true; // Indica que se debe regresar al menú anterior
    }
    

    public static void main(String[] args) {
        // Ejemplo de llamada a las consultas
        consulta1E();
        consulta2E();
        consulta3E();
        consulta4E();
        consulta5E();
        consulta11();
    }
}
