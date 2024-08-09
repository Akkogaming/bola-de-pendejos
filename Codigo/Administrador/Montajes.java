package Administrador;

import java.sql.*;
import java.util.*;

public class Montajes {
    private static final String URL = "jdbc:mysql://localhost:3306/eventos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connect;

    private static void conectar() {
        try {
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }
    }

    private static void cerrarConexion() {
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

    public static void añadirMontaje() {
        conectar();
        try (Scanner leer = new Scanner(System.in)) {
            System.out.println("Ingrese la descripción del montaje (máx. 100 caracteres):");
            String descripcion = leer.nextLine();
            if (descripcion.length() > 100) {
                System.out.println("La descripción no puede exceder los 100 caracteres.");
                return;
            }

            String insertQuery = "INSERT INTO montaje (descripcion) VALUES (?)";
            try (PreparedStatement pstmt = connect.prepareStatement(insertQuery)) {
                pstmt.setString(1, descripcion);
                pstmt.executeUpdate();
                System.out.println("Montaje añadido exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void eliminarMontaje() {
        conectar();
        try (Scanner leer = new Scanner(System.in)) {
            System.out.println("Número de montaje a borrar (o 'C' para cancelar):");
            String input = leer.nextLine().toUpperCase(Locale.getDefault());

            if (input.equals("C")) {
                System.out.println("Operación cancelada.");
                return;
            }

            int codigoMontaje;
            try {
                codigoMontaje = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número o 'C' para cancelar.");
                return;
            }

            String checkEventoQuery = "SELECT COUNT(*) FROM evento WHERE montaje = ?";
            try (PreparedStatement checkStmt = connect.prepareStatement(checkEventoQuery)) {
                checkStmt.setInt(1, codigoMontaje);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);

                    if (count > 0) {
                        System.out.println("El montaje está asociado con uno o más eventos.");
                        System.out.println("Ingrese el código del nuevo montaje:");
                        int nuevoMontaje = leer.nextInt();
                        leer.nextLine();

                        String updateEventoQuery = "UPDATE evento SET montaje = ? WHERE montaje = ?";
                        try (PreparedStatement updateEventoStmt = connect.prepareStatement(updateEventoQuery)) {
                            updateEventoStmt.setInt(1, nuevoMontaje);
                            updateEventoStmt.setInt(2, codigoMontaje);
                            updateEventoStmt.executeUpdate();
                            System.out.println("Eventos reasignados al nuevo montaje.");
                        }
                    }

                    String deleteMontajeQuery = "DELETE FROM montaje WHERE codigo = ?";
                    try (PreparedStatement pstmt = connect.prepareStatement(deleteMontajeQuery)) {
                        pstmt.setInt(1, codigoMontaje);
                        pstmt.executeUpdate();
                        System.out.println("Montaje eliminado exitosamente.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void editarMontaje() {
        conectar();
        try (Scanner leer = new Scanner(System.in)) {
            System.out.println("Ingrese el código del montaje que desea editar:");
            int codigoMontaje = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer del scanner

            String selectQuery = "SELECT * FROM montaje WHERE codigo = ?";
            try (PreparedStatement selectStmt = connect.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, codigoMontaje);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Datos actuales del montaje:");
                        System.out.println("Descripción: " + resultSet.getString("descripcion"));

                        System.out.println("Ingrese la nueva descripción del montaje (máx. 100 caracteres) (o presione Enter para mantener el valor actual):");
                        String descripcion = leer.nextLine();
                        if (descripcion.isEmpty()) {
                            descripcion = resultSet.getString("descripcion");
                        } else if (descripcion.length() > 100) {
                            System.out.println("La descripción no puede exceder los 100 caracteres.");
                            return;
                        }

                        System.out.println("Nuevos datos del montaje:");
                        System.out.println("Descripción: " + descripcion);

                        System.out.println("¿Desea aceptar los cambios? (s/n):");
                        String respuesta = leer.nextLine().trim().toLowerCase();

                        if (respuesta.equals("s")) {
                            String updateQuery = "UPDATE montaje SET descripcion = ? WHERE codigo = ?";
                            try (PreparedStatement updateStmt = connect.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, descripcion);
                                updateStmt.setInt(2, codigoMontaje);

                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("Datos del montaje actualizados exitosamente.");
                                } else {
                                    System.out.println("No se encontró el montaje con el código proporcionado.");
                                }
                            }
                        } else {
                            System.out.println("Cambios cancelados. Los datos del montaje no han sido modificados.");
                        }
                    } else {
                        System.out.println("No se encontró el montaje con el código especificado.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        System.out.println("Esperando 5 segundos...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("El retraso fue interrumpido.");
            e.printStackTrace();
        }
        System.out.println("¡Tiempo transcurrido!");
    }
}
