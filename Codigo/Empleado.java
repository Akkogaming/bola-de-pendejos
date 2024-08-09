
import java.sql.*;
import java.util.*;

import Consultascarpeta.Consultas;

public class Empleado {

  private static Connection connect;
  private static Statement statement;
  private static Scanner leer;

  public static void empleado(Connection conn) {
    connect = conn;
    try {
      statement = connect.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    leer = new Scanner(System.in);
    boolean isRunning2 = true;

    while (isRunning2) {
      System.out.print("\033\143");
      System.out.flush();
      System.out.println("╔═════════════════════════════════════════════════╗");
      System.out.println("║                 MENU EMPLEADO                   ║");
      System.out.println("╠═════════════════════════════════════════════════╣");
      System.out.println("║     1. Consultas                                ║");
      System.out.println("║     2. Realizar la reservacion de un salon      ║");
      System.out.println("║     3. Eliminar la reservacion de un salon      ║");
      System.out.println("║     4. Añadir un cliente                        ║");
      System.out.println("║     5. Eliminar un cliente                      ║");
      System.out.println("║     6. Salir                                    ║");
      System.out.println("╚═════════════════════════════════════════════════╝");

      String answer = leer.nextLine().toUpperCase(Locale.getDefault());

      switch (answer) {
        case "1":
          System.out.print("\033\143");
          System.out.flush();
          System.out.println("╔══════════════════════════════════════════════════════════════╗");
          System.out.println("║               Selecciona la consulta a realizar:             ║");
          System.out.println("║                   1. Reservaciones                           ║");
          System.out.println("║                   2. Equipamiento requerido                  ║");
          System.out.println("║                   3. Servicios requeridos                    ║");
          System.out.println("║                   4. Reservaciones para el mismo salón       ║");
          System.out.println("║                   5. Servicios del mismo tipo                ║");
          System.out.println("║                   6. Salir                                   ║");
          System.out.println("╚══════════════════════════════════════════════════════════════╝");

          String consultaOption = leer.nextLine();
          switch (consultaOption) {
            case "1":
              Consultas.consulta1();
              break;
            case "2":
              Consultas.consulta2();
              break;
            case "3":
              Consultas.consulta3();
              break;
            case "4":
              Consultas.consulta4();
              break;
            case "5":
              Consultas.consulta5();
              break;
            case "6":
              Consultas.consulta17();
              break;
            default:
              System.out.println("Opción no válida.");
          }
          break;

        case "2":
          ReservarSalon();
          break;

        case "3":
          eliminarReservacion();
          break;
        case "4":
          AñadirCliente();
          break;
        case "5":
          eliminarCliente();
          break;
        case "6":
          System.out.println("Saliendo del menú de empleado.");
          isRunning2 = false;
          break;
        default:
          System.out.println("Opción no válida.");
          isRunning2 = false;
          break;
      }
    }
  }

  public static void ReservarSalon() {
    try {
      boolean crearReservacion;
      int codigoSalon;
      int codigocliente;
      int cant_inv;
      int evento;
      int equipamiento;
      int servicio;
      double monto;
      double montoT;
      do {
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             Creando una nueva reservacion                    ║");
        System.out.println("║            Quien fue el que le pidio el salon?               ║");
        System.out.println("║          Favor de ingresar el codigo del cliente             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        codigocliente = leer.nextInt();
        leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 Que salon se va a requerir?                  ║");
        System.out.println("║             Favor de introducir el codigo del Salon          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        codigoSalon = leer.nextInt();
        leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            A que hora necesita el cliente ese salon?         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String horaInicio = leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             A que hora se desocupara el salon?               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String horaFinal = leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║      Cual es la fecha en la que se requerira el salon?       ║");
        System.out.println("║      Por favor escriba en el siguiente orden: AA/MM/DD       ║");
        System.out.println("║              Favor de solo introducir numeros                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String Fecha = leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          Cuantos invitados asistiran al evento?              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        cant_inv = leer.nextInt();
        leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        Que tipo de servicio ha pedido el cliente?            ║");
        System.out.println("║        Favor de introducir el codigo del servicio            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        servicio = leer.nextInt();
        leer.nextLine();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║      Ingrese el codigo del evento que se solicitara          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        evento = leer.nextInt();
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║Por ultimo, ingrese el codigo del equipamiento que se requerira ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        equipamiento = leer.nextInt();

        // Ejecutar consultas para obtener los precios de la base de datos
        String consultaServicio = "SELECT Precio FROM servicios WHERE codigo = ?";
        PreparedStatement psServicio = connect.prepareStatement(consultaServicio);
        psServicio.setInt(1, servicio);
        ResultSet rsServicio = psServicio.executeQuery();
        rsServicio.next();
        double precioServicio = rsServicio.getFloat("Precio");

        String consultaTipoEvento = "SELECT precio FROM tipo_evento WHERE codigo = ?";
        PreparedStatement psTipoEvento = connect.prepareStatement(consultaTipoEvento);
        psTipoEvento.setInt(1, evento);
        ResultSet rsEvento = psTipoEvento.executeQuery();
        rsEvento.next();
        double precioMontaje = rsEvento.getFloat("precio");

        String consultarEquipamiento = "SELECT precio FROM equipamiento WHERE codigo = ?";
        PreparedStatement psEquipamiento = connect.prepareStatement(consultarEquipamiento);
        psEquipamiento.setInt(1, equipamiento);
        ResultSet rsEquipamiento = psEquipamiento.executeQuery();
        rsEquipamiento.next();
        double precioEquipamiento = rsEquipamiento.getFloat("precio");

        // Calcular monto y montoT
        monto = precioServicio + precioMontaje + precioEquipamiento;
        montoT = monto * (1.16); // Asumiendo 16% del impuesto

        String comando = "INSERT INTO reservaciones(fechaevento, Cant_Inv, HoraI, HoraF, monto, montoT, cliente, salon, evento) VALUES('"
            +
            Fecha + "','" + cant_inv + "','" + horaInicio + "','" + horaFinal + "','" + monto + "','" + montoT + "','"
            + codigocliente + "','" + codigoSalon + "','" + evento + "')";
        statement.executeUpdate(comando);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              Reservacion creada con exito                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        crearReservacion = false;
      } while (crearReservacion);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void eliminarReservacion() {
    try {
      System.out.println("Número de código a borrar:");
      int numero = leer.nextInt();
      leer.nextLine();
      System.out.print("\033\143");
      System.out.flush();
      System.out.println("╔══════════════════════════════════════════════════════════╗");
      System.out.println("║                     ¿ESTA SEGURO?                        ║");
      System.out.println("║           ASEGURECE QUE EL NUMERO SEA CORRECTO           ║");
      System.out.println("║               ESTA ACCIÓN ES IRREVERSIBLE                ║");
      System.out.println("║                 Y)CONFIRMAR N)DECLINAR                   ║");
      System.out.println("╚══════════════════════════════════════════════════════════╝");

      String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
      if (confirmDecision.equals("Y")) {
        String comando = "DELETE FROM reservacion WHERE codigo = " + numero;
        statement.executeUpdate(comando);
        System.out.println("Salon eliminado exitosamente.");
      } else if (confirmDecision.equals("N")) {
        System.out.println("Operación cancelada.");
      } else {
        System.out.println("Selección no válida. Debe ingresar Y o N.");
      }
    } catch (Exception e) {
    }
  }

  public static void AñadirCliente() {
    try {
      int empleado;
      System.out.println("Ingrese el nombre del cliente:");
      String nombre = leer.nextLine();

      System.out.println("Ingrese el primer apellido del cliente:");
      String a_paterno = leer.nextLine();

      System.out.println("Ingrese el segundo apellido del cliente:");
      String a_materno = leer.nextLine();

      System.out.println("Ingrese el telefono del cliente:");
      String telefono = leer.nextLine();

      System.out.println("Ingrese su numero de empleado");
      empleado = leer.nextInt();
      leer.nextLine();

      String insertQuery = "INSERT INTO cliente(nombre, apaterno, amaterno, telefono, empleado) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement pstmt = connect.prepareStatement(insertQuery);
      pstmt.setString(1, nombre);
      pstmt.setString(2, a_paterno);
      pstmt.setString(3, a_materno);
      pstmt.setString(4, telefono);
      pstmt.setInt(5, empleado);
      pstmt.executeUpdate();

      System.out.println("Empleado añadido exitosamente.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void eliminarCliente() {
    try {
      System.out.println("Número de código a borrar:");
      int numero = leer.nextInt();
      leer.nextLine();
      System.out.print("\033\143");
      System.out.flush();
      System.out.println("╔══════════════════════════════════════════════════════════╗");
      System.out.println("║                     ¿ESTA SEGURO?                        ║");
      System.out.println("║           ASEGURECE QUE EL NUMERO SEA CORRECTO           ║");
      System.out.println("║               ESTA ACCIÓN ES IRREVERSIBLE                ║");
      System.out.println("║                 Y)CONFIRMAR N)DECLINAR                   ║");
      System.out.println("╚══════════════════════════════════════════════════════════╝");

      String confirmDecision = leer.nextLine().toUpperCase(Locale.getDefault());
      if (confirmDecision.equals("Y")) {
        String comando = "DELETE FROM cliente WHERE codigo = " + numero;
        statement.executeUpdate(comando);
        System.out.println("Cliente eliminado exitosamente.");
      } else if (confirmDecision.equals("N")) {
        System.out.println("Operación cancelada.");
      } else {
        System.out.println("Selección no válida. Debe ingresar Y o N.");
      }
    } catch (Exception e) {
    }
  }
}
