package Administrador;

import java.sql.*;
import java.util.*;

public class Eventos {
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

    // Método para validar la longitud de la cadena
    private static boolean validarLongitud(String valor, int maxLongitud) {
        if (valor.length() > maxLongitud) {
            System.out.println("El valor ingresado excede la longitud máxima permitida de " + maxLongitud + " caracteres.");
            return false;
        }
        return true;
    }

    // Método para validar que el valor es un número
    private static boolean esNumero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado debe ser un número.");
            return false;
        }
    }

    public static void añadirEvento() {
        conectar();
        try {
            Scanner leer = new Scanner(System.in);

            System.out.println("Ingrese la descripción del evento (máx. 255 caracteres):");
            String descripcion = leer.nextLine();
            if (!validarLongitud(descripcion, 255)) return;

            System.out.println("Ingrese el código del salón:");
            String salonCodigoStr = leer.nextLine();
            if (!esNumero(salonCodigoStr)) return;
            int salonCodigo = Integer.parseInt(salonCodigoStr);

            System.out.println("Ingrese el código del montaje:");
            String montajeCodigoStr = leer.nextLine();
            if (!esNumero(montajeCodigoStr)) return;
            int montajeCodigo = Integer.parseInt(montajeCodigoStr);

            System.out.println("Ingrese el código del tipo de evento:");
            String tipoEventoCodigoStr = leer.nextLine();
            if (!esNumero(tipoEventoCodigoStr)) return;
            int tipoEventoCodigo = Integer.parseInt(tipoEventoCodigoStr);

            String insertQuery = "INSERT INTO evento (descripcion, salon, montaje, tipo_evento) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, descripcion);
            pstmt.setInt(2, salonCodigo);
            pstmt.setInt(3, montajeCodigo);
            pstmt.setInt(4, tipoEventoCodigo);

            pstmt.executeUpdate();
            System.out.println("Evento añadido exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void eliminarEvento() {
        conectar();
        try {
            Scanner leer = new Scanner(System.in);
            System.out.println("Número de evento a borrar (o 'C' para cancelar):");
            String input = leer.nextLine().toUpperCase(Locale.getDefault());

            if (input.equals("C")) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (!esNumero(input)) return;
            int numero = Integer.parseInt(input);

            String deleteQuery = "DELETE FROM evento WHERE numeroEvento = ?";
            PreparedStatement pstmt = connect.prepareStatement(deleteQuery);
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();

            System.out.println("Evento eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public static void editarEvento() {
        conectar();
        Scanner leer = new Scanner(System.in);
    
        try {
            System.out.println("Ingrese el número del evento que desea editar:");
            String numeroEventoStr = leer.nextLine();
            if (!esNumero(numeroEventoStr)) return;
            int numeroEvento = Integer.parseInt(numeroEventoStr);
    
            // Consultar los datos actuales del evento
            String selectQuery = "SELECT * FROM evento WHERE numeroEvento = ?";
            PreparedStatement selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setInt(1, numeroEvento);
            ResultSet resultSet = selectStmt.executeQuery();
    
            if (resultSet.next()) {
                // Mostrar los datos actuales
                System.out.println("Datos actuales del evento:");
                System.out.println("Descripción: " + resultSet.getString("descripcion"));
                System.out.println("Código del salón: " + resultSet.getInt("salon"));
                System.out.println("Código del montaje: " + resultSet.getInt("montaje"));
                System.out.println("Código del tipo de evento: " + resultSet.getInt("tipo_evento"));
    
                // Solicitar los nuevos datos
                System.out.println("Ingrese la nueva descripción del evento (máx. 255 caracteres, o presione Enter para mantener el valor actual):");
                String descripcion = leer.nextLine();
                if (!descripcion.isEmpty() && !validarLongitud(descripcion, 255)) return;
                descripcion = descripcion.isEmpty() ? resultSet.getString("descripcion") : descripcion;
    
                System.out.println("Ingrese el nuevo código del salón (o presione Enter para mantener el valor actual):");
                String salonCodigoStr = leer.nextLine();
                int salonCodigo = salonCodigoStr.isEmpty() ? resultSet.getInt("salon") : Integer.parseInt(salonCodigoStr);
    
                System.out.println("Ingrese el nuevo código del montaje (o presione Enter para mantener el valor actual):");
                String montajeCodigoStr = leer.nextLine();
                int montajeCodigo = montajeCodigoStr.isEmpty() ? resultSet.getInt("montaje") : Integer.parseInt(montajeCodigoStr);
    
                System.out.println("Ingrese el nuevo código del tipo de evento (o presione Enter para mantener el valor actual):");
                String tipoEventoCodigoStr = leer.nextLine();
                int tipoEventoCodigo = tipoEventoCodigoStr.isEmpty() ? resultSet.getInt("tipo_evento") : Integer.parseInt(tipoEventoCodigoStr);
    
                // Muestra los nuevos datos ingresados
                System.out.println("Nuevos datos del evento:");
                System.out.println("Descripción: " + descripcion);
                System.out.println("Código del salón: " + salonCodigo);
                System.out.println("Código del montaje: " + montajeCodigo);
                System.out.println("Código del tipo de evento: " + tipoEventoCodigo);
    
                // Pregunta al usuario si desea aceptar los cambios
                System.out.println("¿Desea aceptar los cambios? (s/n):");
                String respuesta = leer.nextLine().trim().toLowerCase();
    
                if (respuesta.equals("s")) {
                    // Actualizar los datos del evento
                    String updateQuery = "UPDATE evento SET descripcion = ?, salon = ?, montaje = ?, tipo_evento = ? WHERE numeroEvento = ?";
                    PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                    updateStmt.setString(1, descripcion);
                    updateStmt.setInt(2, salonCodigo);
                    updateStmt.setInt(3, montajeCodigo);
                    updateStmt.setInt(4, tipoEventoCodigo);
                    updateStmt.setInt(5, numeroEvento);
    
                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Datos del evento actualizados exitosamente.");
                    } else {
                        System.out.println("No se encontró el evento con el número proporcionado.");
                    }
                } else {
                    System.out.println("Cambios cancelados. Los datos del evento no han sido modificados.");
                }
            } else {
                System.out.println("No se encontró el evento con el número especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    
        // Retraso de 5 segundos
        System.out.println("Esperando 5 segundos...");
        try {
            Thread.sleep(5000);  // Pausa el hilo actual durante 5000 milisegundos (5 segundos)
        } catch (InterruptedException e) {
            System.err.println("El retraso fue interrumpido.");
            e.printStackTrace();
        }
        System.out.println("¡Tiempo transcurrido!");
    }
}
