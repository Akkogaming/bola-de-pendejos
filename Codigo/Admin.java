import java.sql.*;
import java.util.Scanner;

public class Admin {

    private Connection connect;
    private Statement statement;

    // Constructor
    public Admin(Connection connect) {
        this.connect = connect;
        try {
            this.statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar un nuevo empleado
    public void insertarEmpleado() {
        try {
            Scanner leer = new Scanner(System.in);

            System.out.println("Insertar un nuevo empleado");

            System.out.println("Nombre:");
            String nombre = leer.nextLine();

            System.out.println("Apellido paterno:");
            String a_paterno = leer.nextLine();

            System.out.println("Apellido materno:");
            String a_materno = leer.nextLine();

            System.out.println("Teléfono:");
            String telefono = leer.nextLine();

            System.out.println("Correo electrónico:");
            String correo_e = leer.nextLine();

            String comando = "INSERT INTO empleados(NOMBRE, A_PATERNO, A_MATERNO, TELEFONO, CORREO_E) VALUES ('" +
                    nombre + "', '" + a_paterno + "', '" + a_materno + "', '" + telefono + "', '" + correo_e + "')";

            statement.executeUpdate(comando);
            System.out.println("Empleado insertado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un empleado por código
    public void eliminarEmpleado() {
        try {
            Scanner leer = new Scanner(System.in);

            System.out.println("Número de código a borrar:");
            int numero = leer.nextInt();
            leer.nextLine(); // Consume el newline

            String comando = "DELETE FROM empleados WHERE numero = " + numero;
            statement.executeUpdate(comando);
            System.out.println("Empleado eliminado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para leer la tabla de empleados
    public void leerEmpleados() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM empleados");

            // Obtener metadatos
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

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para gestionar las opciones del administrador
    public void Admin() {
        Scanner leer = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                 MENU ADMINISTRADOR                       |");
            System.out.println("------------------------------------------------------------");
            System.out.println("| 1. Insertar empleado                                     |");
            System.out.println("| 2. Eliminar empleado                                     |");
            System.out.println("| 3. Leer tabla de empleados                               |");
            System.out.println("| 4. Salir                                                 |");
            System.out.println("------------------------------------------------------------");

            int opcion = leer.nextInt();
            leer.nextLine(); // Consume el newline

            switch (opcion) {
                case 1:
                    insertarEmpleado();
                    break;
                case 2:
                    eliminarEmpleado();
                    break;
                case 3:
                    leerEmpleados();
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}
