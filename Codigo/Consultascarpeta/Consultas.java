package Consultascarpeta;

import java.sql.*;
import java.util.*;


//* la carpeta es irrelevante pero hace que el programa se vea mas complicado de lo que es xd
    
    public class Consultas {
    
Scanner keyboard=new Scanner(System.in);
            String enter;

           
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
                    int columnWidth = 65; // Ajusta el ancho según lo necesites
        
                    while (resultSet.next()) {
                        printDataInBlocks(resultSet, metaData, columnCount, columnWidth);
                    }
        
                } catch (SQLException e) {
                    System.out.println("An error occurred while executing the query: " + e.getMessage());
                } finally {
                    // Close JDBC resources
                    try {
                        if (resultSet != null) resultSet.close();
                        if (statement != null) statement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing resources: " + e.getMessage());
                    }
                }
            }
        
            private static void printDataInBlocks(ResultSet resultSet, ResultSetMetaData metaData, int columnCount, int columnWidth) throws SQLException {
                // Print the top border
                printBorder(columnWidth, "═", "╔", "╗");
        
                // Print each column's data
                for (int i = 1; i <= columnCount; i++) {
                    String header = metaData.getColumnLabel(i);
                    String cellValue = resultSet.getString(i);
        
                    // Print header and value
                    printRow(header, cellValue, columnWidth);
        
                    // Print a blank line after each pair
                    printBlankLine(columnWidth);
                }
        
                // Print the bottom border
                printBorder(columnWidth, "═", "╚", "╝");
        
                System.out.println();  // Space between blocks (optional)
            }
        
            private static void printBorder(int columnWidth, String borderChar, String startChar, String endChar) {
                String border = startChar + borderChar.repeat(columnWidth - 0) + endChar;
                System.out.println(border);
            }
        
            private static void printRow(String header, String cellValue, int columnWidth) {
                String headerLine = String.format("%-" + (columnWidth - 2) + "s", header + ":");
                String valueLine = String.format("%-" + (columnWidth - 2) + "s", cellValue);
        
                System.out.println("║ " + headerLine + " ║");
                System.out.println("║ " + valueLine + " ║");
            }
        
            private static void printBlankLine(int columnWidth) {
                System.out.println("║ " + " ".repeat(columnWidth - 2) + " ║");
            }
        
        
        
        
    
    ///////////////////////////////////////////////////////////////////////

    //! basado en consultas admin

    public static void consulta1() {
    //!consutlas de clientes
        String query = "SELECT " +
                "Date_format(R.fechaevento, \"%d-%m-%y\") as 'Fecha reservacion', " +
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
                "JOIN montaje m ON e.montaje = m.codigo ";
         //       "WHERE r.cliente = " + clienteCodigo;
        

        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta2() {
        //! equpipamento requerido
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
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta3() {
        //!servicios requeridos
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
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta4() {
        //!mismo salon
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
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta5() {

        //!mismo servicio
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
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta6() {
        //!reservaciones cliente especifico
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
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta7() {
        //!servicio espeficico
        Scanner read = new Scanner(System.in);


        int servicioCodigo = -1;

        while (servicioCodigo <= 0) {
            System.out.println("Ingrese el código del servicio:");

            try {
                servicioCodigo = read.nextInt();

                
                if (servicioCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
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
        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta8() {
        //!equipamiento especifico
        Scanner read = new Scanner(System.in);
        int equipamientoCodigo = -1;

        while (equipamientoCodigo <= 0) {
            System.out.println("Ingrese el código del equipamiento:");

            try {
                equipamientoCodigo = read.nextInt();

                
                if (equipamientoCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
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
        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta9() {
        //!montaje especifico
        Scanner read = new Scanner(System.in);
        int montajeCodigo = -1;

        while (montajeCodigo <= 0) {
            System.out.println("Ingrese el código del montaje:");

            try {
                montajeCodigo = read.nextInt();

                
                if (montajeCodigo <= 0) {
                    System.out.println("Código inválido. Por favor, ingrese un número.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                read.next(); 
            }
        }

        
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
        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("ay wey, se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta10() {
        //!mismo mes
        Scanner read = new Scanner(System.in); 
        
        
        int mes = -1; 
        while (mes < 1 || mes > 12) {
            System.out.println("Elija el mes del 1 al 12:");
            
            try {
                mes = read.nextInt();
                
                if (mes < 1 || mes > 12) {
                    System.out.println("El mes ingresado no es válido. Por favor, elija un valor entre 1 y 12.");
                }
            } catch (InputMismatchException e) {
                
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 12.");
                read.next(); 
            }
        }
        
        
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
        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }
    
    public static void consulta11(){     
        //!reservaciones a unn empleadoempleados
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println("Ingrese el codigo del empleado");
        num = read.nextInt();
        
        
        String query = "SELECT " +
                        "e.numero AS numero_empleado, " +
                        "r.codigo AS numero_reserva " +
                        "FROM " +
                        "reservaciones r " +
                        "JOIN cliente c ON r.cliente = c.codigo " +
                        "JOIN empleados e ON c.empleado = e.numero " +
                        "WHERE e.numero = " + num;
        
        
        try {
            ejecutarConsulta(query);
            Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();

        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void consulta12() {
        //!consultar empleados
        String query = "SELECT * FROM empleados";
        ejecutarConsulta(query);
        Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();
    }

    public static void consulta13() {
        String query = "SELECT * FROM salon";
        ejecutarConsulta(query);
        Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();
    }

    public static void consulta14() {
        String query = "SELECT * FROM servicios";         
        ejecutarConsulta(query);      
        Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine(); 
    }
    
    public static void consulta15() {
       
        String query = "SELECT * FROM montaje ";
        ejecutarConsulta(query);
        Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();
    }
    
    public static void consulta16() {
        String query = "SELECT * FROM evento ";
        ejecutarConsulta(query);
        Scanner keyboard=new Scanner(System.in);
        String enter;
        System.out.println("Press Enter to continue");
        enter=keyboard.nextLine();
    }

    public static void consulta17(){
        
        
        
    }
    

    //!para probar las consultas seguidas manualmente
    //!remover si es necesario
    public static void main(String[] args) {
        
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
