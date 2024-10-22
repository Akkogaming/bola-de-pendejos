import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

//consultas para el empleado y el admin
public class Consultas {

 

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
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();

            // Imprimir datos de filas
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", resultSet.getString(i));
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

    public static void consulta1() {
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
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' '))  cliente, " +
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

    public static void consulta2() {
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

    public static void consulta3() {
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

    public static void consulta4() {
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

    public static void consulta5() {
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

    public static void consulta6() {
        Scanner read = new Scanner(System.in);
        int clienteCodigo = -1;

        while (clienteCodigo <= 0) {
            System.out.println("Ingrese el código del cliente:");

            try {
                clienteCodigo = read.nextInt();

                // Validar el código ingresado
                if (clienteCodigo <= 0) {
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
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta7() {
        Scanner read = new Scanner(System.in);
        int servicioCodigo = -1;

        while (servicioCodigo <= 0) {
            System.out.println("Ingrese el código del servicio:");

            try {
                servicioCodigo = read.nextInt();

                // Validar el código ingresado
                if (servicioCodigo <= 0) {
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
                "R.codigo as 'numero de la reservacion', " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "S.nombreSalon as 'nombre del salon', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "tp.descripcion as 'tipo de evento', " +
                "R.Cant_Inv as 'Cantidad de invitados', " +
                "eq.nombre as Equipamiento, " +
                "eq.precio as 'Precio del equipamiento' " +
                "FROM reservaciones as R " +
                "INNER JOIN cliente as C on R.cliente = c.codigo " +
                "INNER JOIN salon as S on R.salon = S.codigo " +
                "INNER JOIN evento as e on R.evento = e.numeroEvento " +
                "INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo " +
                "INNER JOIN equipo_reservado as er on er.reservacion = R.codigo " +
                "INNER JOIN equipamiento as eq on er.equipamiento = eq.codigo " +
                "INNER JOIN servicios_cliente as sc on sc.cliente = c.codigo " +
                "INNER JOIN servicios as ser on sc.servicios = ser.codigo " +
                "WHERE ser.codigo = " + servicioCodigo;
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta8() {
        Scanner read = new Scanner(System.in);
        int equipamientoCodigo = -1;

        while (equipamientoCodigo <= 0) {
            System.out.println("Ingrese el código del equipamiento:");

            try {
                equipamientoCodigo = read.nextInt();

                // Validar el código ingresado
                if (equipamientoCodigo <= 0) {
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
                "R.codigo as 'numero de la reservacion', " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "S.nombreSalon as 'nombre del salon', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "tp.descripcion as 'tipo de evento', " +
                "R.Cant_Inv as 'Cantidad de invitados', " +
                "eq.nombre as Equipamiento, " +
                "eq.precio as 'Precio del equipamiento' " +
                "FROM reservaciones as R " +
                "INNER JOIN cliente as C on R.cliente = c.codigo " +
                "INNER JOIN salon as S on R.salon = S.codigo " +
                "INNER JOIN evento as e on R.evento = e.numeroEvento " +
                "INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo " +
                "INNER JOIN equipo_reservado as er on er.reservacion = R.codigo " +
                "INNER JOIN equipamiento as eq on er.equipamiento = eq.codigo " +
                "WHERE eq.codigo = " + equipamientoCodigo;
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta9() {
        Scanner read = new Scanner(System.in);
        int montajeCodigo = -1;

        while (montajeCodigo <= 0) {
            System.out.println("Ingrese el código del montaje:");

            try {
                montajeCodigo = read.nextInt();

                // Validar el código ingresado
                if (montajeCodigo <= 0) {
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
                "R.codigo as 'Numero de la reservacion', " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "S.nombreSalon as 'nombre del salon', " +
                "CONCAT(c.nombre,' ', c.apaterno,' ', IFNULL(CONCAT(c.amaterno,' '),' ')) as cliente, " +
                "e.numeroEvento as 'Numero de evento', " +
                "m.descripcion as 'Descripcion montaje' " +
                "FROM reservaciones as R " +
                "INNER JOIN cliente as C on R.cliente = c.codigo " +
                "INNER JOIN salon as S on R.salon = S.codigo " +
                "INNER JOIN evento as e on R.evento = e.numeroEvento " +
                "INNER JOIN montaje as m on e.montaje = m.codigo " +
                "WHERE m.codigo = " + montajeCodigo;
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("ay wey, se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta10() {
        Scanner read = new Scanner(System.in); 
        
        // Solicitar al usuario el mes
        int mes = -1; // Inicializamos con un valor no válido
        while (mes < 1 || mes > 12) {
            System.out.println("Elija el mes del 1 al 12:");
            
            try {
                mes = read.nextInt();
                // Validar el mes ingresado
                if (mes < 1 || mes > 12) {
                    System.out.println("El mes ingresado no es válido. Por favor, elija un valor entre 1 y 12.");
                }
            } catch (InputMismatchException e) {
                // Manejar el caso cuando el usuario ingresa un valor no numérico
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 12.");
                read.next(); // Limpiar el buffer del scanner
            }
        }
        
        // Preparar la consulta SQL
        String query = "SELECT " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha de la reservacion', " +
                "s.nombreSalon as 'nombre del salon', " +
                "tp.descripcion as 'tipo de evento', " +
                "R.Cant_Inv as 'Cantidad de invitados' " +
                "FROM reservaciones as R " +
                "INNER JOIN salon as s on R.salon = s.codigo " +
                "INNER JOIN evento as e on R.evento = e.numeroEvento " +
                "INNER JOIN tipo_evento as tp on e.tipo_evento = tp.codigo " +
                "WHERE MONTH(R.fechaevento) = " + mes;
        
        // Ejecutar la consulta y manejar posibles errores
        try {
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }
    
    public static void consulta11(){     
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println("Ingrese el codigo del empleado");
        num = read.nextInt();
        
        // Define the SQL query with proper spacing
        String query = "SELECT " +
                        "e.numero AS numero_empleado, " +
                        "c.codigo AS numero_cliente " +
                        "FROM " +
                        "cliente as c " +
                        "JOIN " +
                        "empleados e ON c.empleado = e.numero "+
                        "WHERE e.numero = " + num;

                      
                        
 
        
        // Use try-with-resources to ensure resources are closed properly
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Process the result set
            while (rs.next()) {
                int empleadoNumero = rs.getInt("numero_empleado");
                int clienteCodigo = rs.getInt("numero_cliente");
                System.out.println("Número de empleado: " + empleadoNumero + ", Número de de cliente: " + clienteCodigo);
            }
            
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

   
    public static void consulta12() {
        String query = "SELECT * FROM empleados";
        ejecutarConsulta(query);
    }

    public static void consulta13() {
        String query = "SELECT * FROM equipamiento";
        ejecutarConsulta(query);
    }

    public static void consulta14() {
        String query = "SELECT * FROM servicios"; // Define la consulta SQL
    
        // Ejecutar la consulta usando el método ejecutarConsulta
        try {
            // Aquí simplemente llamamos a `ejecutarConsulta` que ya maneja la conexión y la ejecución
            ejecutarConsulta(query);
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }
    

    public static void consulta15() {
       
        String query = "SELECT * FROM montaje ";
        ejecutarConsulta(query);
    }
    
    public static void consulta16() {
        String query = "SELECT * FROM evento ";
        ejecutarConsulta(query);
    }

    public static void consulta17(){
        
        
        System.out.println("Cancelando.........");


        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    

    public static void main(String[] args) {
        // Ejemplo de llamada a las consultas
        consulta1();
        consulta2();
        consulta3();
        consulta4();
        consulta5();
        consulta6();
        consulta7();
        consulta8();
        consulta9();
        consulta10();
        consulta11();
        consulta12();
        consulta13();
        consulta14();
        consulta15();
        consulta16();
        consulta17();
    }
}
