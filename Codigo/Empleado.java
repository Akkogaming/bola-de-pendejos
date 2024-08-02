import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Empleado {

    private static Connection connect;
    private static Statement statement;
    private static Scanner leer;

    // Método estático para inicializar el objeto Empleado
    public static void empleado(Connection conn) {
        connect = conn;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // Salir si no se puede crear la declaración
        }

        leer = new Scanner(System.in);
        boolean isRunning2 = true;

        while (isRunning2) {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                    MENU EMPLEADO                         |");
            System.out.println("------------------------------------------------------------");
            System.out.println("| 1. Consultas                                             |");
            System.out.println("| 2. Realizar la reservacion de un salon                   |");
            System.out.println("| 3. Eliminar la reservacion de un salon                   |");
            System.out.println("| 4. Salir                                                 |");
            System.out.println("------------------------------------------------------------");

            String answer = leer.nextLine().toUpperCase(Locale.getDefault());

            switch (answer) {
                case "1":
                    // Manejo de consultas
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("|               Selecciona la consulta a realizar:             |");
                    System.out.println("|                       1. Reservaciones                       |");
                    System.out.println("|                   2. Equipamiento requerido                  |");
                    System.out.println("|                     3. Servicios requeridos                  |");
                    System.out.println("|             4. Reservaciones para el mismo salón             |");
                    System.out.println("|                  5. Servicios del mismo tipo                 |");
                    System.out.println("|                           6. Salir                           |");
                    System.out.println("----------------------------------------------------------------");

                    String consultaOption = leer.nextLine();
                    switch (consultaOption) {
                        case "1":
                            ConsultasE.consulta1E();
                            break;
                        case "2":
                            ConsultasE.consulta2E();
                            break;
                        case "3":
                            ConsultasE.consulta3E();
                            break;
                        case "4":
                            ConsultasE.consulta4E();
                            break;
                        case "5":
                            ConsultasE.consulta5E();
                            break;
                        case "6":
                            ConsultasE.consulta11();
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
                    break;

                case "2":
                    ReservarSalon();
                    break;

                case "3":
                    eliminarReservacion();
                    break;

                case "4":
                    ConsultasE.consulta11();
                    System.out.println("Saliendo del menú de empleado.");
                    isRunning2 = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        leer.close(); // Cerrar el scanner al finalizar
    }

    // Método para realizar la reservación de un salón
    public static void ReservarSalon() {
        try {
            boolean crearReservacion = true; // Iniciar como verdadero para permitir la creación de reservas
            while (crearReservacion) {
                System.out.println("----------------------------------------------------------------");
                System.out.println("|             Creando una nueva reservacion                    |");
                System.out.println("|            ¿Quién fue el que pidió el salón?                |");
                System.out.println("|          Favor de ingresar el código del cliente             |");
                System.out.println("----------------------------------------------------------------");
                int codigocliente = leer.nextInt();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|                 ¿Qué salón se va a requerir?                |");
                System.out.println("|          Favor de introducir el código del salón            |");
                System.out.println("----------------------------------------------------------------");
                int codigoSalon = leer.nextInt();
                leer.nextLine(); // Limpiar el buffer del scanner

                System.out.println("----------------------------------------------------------------");
                System.out.println("|            ¿A qué hora necesita el cliente ese salón?       |");
                System.out.println("----------------------------------------------------------------");
                String horaInicio = leer.nextLine();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|             ¿A qué hora se desocupara el salón?             |");
                System.out.println("----------------------------------------------------------------");
                String horaFinal = leer.nextLine();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|      ¿Cuál es la fecha en la que se requerirá el salón?       |");
                System.out.println("|      Por favor escribe en el siguiente orden: AA/MM/DD       |");
                System.out.println("|              Favor de solo introducir números                |");
                System.out.println("----------------------------------------------------------------");
                String fecha = leer.nextLine();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|          ¿Cuántos invitados asistirán al evento?             |");
                System.out.println("----------------------------------------------------------------");
                int cantInv = leer.nextInt();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|      ¿Qué tipo de servicio ha pedido el cliente?             |");
                System.out.println("|      Favor de introducir el código del servicio              |");
                System.out.println("----------------------------------------------------------------");
                int servicio = leer.nextInt();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|     Ingrese el código del evento que se solicitará           |");
                System.out.println("----------------------------------------------------------------");
                int evento = leer.nextInt();

                System.out.println("----------------------------------------------------------------");
                System.out.println("|     Ingrese el código del montaje que se realizará           |");
                System.out.println("----------------------------------------------------------------");
                int montaje = leer.nextInt();

                System.out.println("-----------------------------------------------------------------");
                System.out.println("| Por último, ingrese el código del equipamiento que se requerirá |");
                System.out.println("-----------------------------------------------------------------");
                int equipamiento = leer.nextInt();

                // No supe cómo añadirle un monto y monto total reales, esto se cambiará más adelante
                String comando = "INSERT INTO reservaciones(fechaevento, Cant_Inv, HoraI, HoraF, monto, montoT, cliente, salon, evento) VALUES('" +
                        fecha + "','" + cantInv + "','" + horaInicio + "','" + horaFinal + "','" + 4455 + "','" + 5541 + "','" + codigocliente + "','" + codigoSalon + "','" + evento + "')";

                try {
                    statement.executeUpdate(comando);
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("|              Reservación creada con éxito                    |");
                    System.out.println("----------------------------------------------------------------");
                } catch (SQLException e) {
                    System.out.println("Error al crear la reservación:");
                    e.printStackTrace();
                }

                // Pregunta si desea crear otra reservación
                System.out.println("¿Desea crear otra reservación? (S/N)");
                leer.nextLine(); // Limpiar el buffer del scanner
                String respuesta = leer.nextLine().toUpperCase(Locale.getDefault());
                if (respuesta.equals("N")) {
                    crearReservacion = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al crear la reservación:");
            e.printStackTrace();
        }
    }

    public static void eliminarReservacion() {
        try {
            System.out.println("Número de código a borrar:");
            int numero = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer del scanner

            System.out.println("------------------------------------------------------------");
            System.out.println("|                     ¿ESTÁ SEGURO?                         |");
            System.out.println("|           ASEGÚRESE DE QUE EL NÚMERO SEA CORRECTO         |");
            System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
            System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
            System.out.println("------------------------------------------------------------");

            String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
            if (confirmDecision.equals("Y")) {
                String comando = "DELETE FROM reservacion WHERE codigo = " + numero;
                try {
                    statement.executeUpdate(comando);
                    System.out.println("Salón eliminado exitosamente.");
                } catch (SQLException e) {
                    System.out.println("Error al eliminar la reservación:");
                    e.printStackTrace();
                }
            } else if (confirmDecision.equals("N")) {
                System.out.println("Operación cancelada.");
            } else {
                System.out.println("Selección no válida. Debe ingresar Y o N.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la reservación:");
            e.printStackTrace();
        }
    }
}
