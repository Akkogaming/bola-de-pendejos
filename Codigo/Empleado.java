import java.sql.*;
import java.util.Scanner;
import java.util.Locale;

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
          return;
      }

      leer = new Scanner(System.in);
      boolean isRunning = true;

      while (isRunning) {
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
                  
                          System.out.println("----------------------------------------------------------------"); // 64 caracteres
                          System.out.println("|               Selecciona la consulta a realizar:             |");
                          System.out.println("|                       1. Reservaciones                       |");
                          System.out.println("|                   2. Equipamiento requerido                  |");
                          System.out.println("|                     3. Servicios requeridos                  |");
                          System.out.println("|             4. Reservaciones para el mismo salón             |");
                          System.out.println("|                  5. Servicios del mismo tipo                 |");
                          //System.out.println("|                  6. Reservaciones del cliente                |");
                          //System.out.println("|           7. Reservaciones con un servicio específico        |");
                          //System.out.println("|            8. Reservaciones con un equipo específico         |");
                          //System.out.println("|           9. Reservaciones con un montaje específico         |");
                          //System.out.println("|               10. Reservaciones en el mismo mes              |");
                          System.out.println("|                           6. Salir                          |");
                          System.out.println("----------------------------------------------------------------"); // 64 caracteres

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

                              default:
                              ConsultasE.consulta6E();
                          }
                          break;

              case "2":
                ReservarSalon();
                  break;

              case "3":
                eliminarReservacion();
                  break;
              case "4":
              isRunning = false;
              System.out.println("Saliendo del menú de administrador.");
              default:
                  System.out.println("Opción no válida.");
                break;
          }
      }

      leer.close();
  }
  //Metodo para realizar la reservacion de un salon (Si no termino soy puto)
  public static void ReservarSalon(){
    try{
      boolean crearReservacion=true;
      int codigoSalon;
      int codigocliente;
      int cant_inv;
      int evento;
      int montaje;
      int equipamiento;
      int servicio;
      float monto=4455;
      float montoT=5541;
      do{
        System.out.println("----------------------------------------------------------------"); // 64 caracteres
        System.out.println("|             Creando una nueva reservacion                    |");
        System.out.println("|            Quien fue el que le pidio el salon?               |");
        System.out.println("|          Favor de ingresar el codigo del cliente             |");
        System.out.println("----------------------------------------------------------------");
        codigocliente=leer.nextInt();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|                 Que salon se va a requerir?                  |");
        System.out.println("|             Favor de introducir el codigo del Salon          |");
        System.out.println("----------------------------------------------------------------");
        codigoSalon=leer.nextInt();
        leer.nextLine(); //sino hace parkour el Scan

        System.out.println("----------------------------------------------------------------");
        System.out.println("|            A que hora necesita el cliente ese salon?         |");
        System.out.println("----------------------------------------------------------------");
        String horaInicio=leer.nextLine();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|             A que hora se desocupara el salon?               |");
        System.out.println("----------------------------------------------------------------");
        String horaFinal=leer.nextLine();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|      Cual es la fecha en la que se requerira el salon?       |");
        System.out.println("|      Por favor escriba en el siguiente orden: DD/MM/AA       |");
        System.out.println("|              Favor de solo introducir numeros                |");
        System.out.println("----------------------------------------------------------------");
        String Fecha=leer.nextLine();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|          Cuantos invitados asistiran al evento?               |");
        System.out.println("----------------------------------------------------------------");
        cant_inv=leer.nextInt();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|        Que tipo de servicio ha pedido el cliente?            |");
        System.out.println("|        Favor de introducir el codigo del servicio            |");
        System.out.println("----------------------------------------------------------------");
        servicio=leer.nextInt();

        System.out.println("----------------------------------------------------------------");
        System.out.println("|      Ingrese el codigo del evento que se solicitara          |");
        System.out.println("----------------------------------------------------------------");
        evento=leer.nextInt();

        System.out.println("----------------------------------------------------------------");
        System.out.println("     |Ingrese el codigo del montaje que se realizara           |");
        System.out.println("----------------------------------------------------------------");
        montaje=leer.nextInt();

        System.out.println("-----------------------------------------------------------------");
        System.out.println("|Por ultimo, ingrese el codigo del equipamiento que se requerira|");
        System.out.println("-----------------------------------------------------------------");
        equipamiento=leer.nextInt();
        //No supe como añadirle un monto y monto total reales, esto se cambiara mas adelante
        String comando = "INSERT INTO reservaciones(fechaevento, Cant_Inv, HoraI, HoraF, monto, montoT, cliente, salon, evento) VALUES('"+
        Fecha+"','"+cant_inv+"','"+horaInicio+"','"+horaFinal+"','"+monto+"','"+montoT+"','"+codigocliente+"','"+codigoSalon+"','"+evento+"')";
        statement.executeUpdate(comando);
        System.out.println("----------------------------------------------------------------");
        System.out.println("|              Reservacion creada con exito                    |");
        System.out.println("----------------------------------------------------------------");
        crearReservacion=false;
      }while(crearReservacion=false);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public static void eliminarReservacion(){
    try{
        System.out.println("Número de código a borrar:");
        int numero = leer.nextInt();
        leer.nextLine();

        System.out.println("------------------------------------------------------------");
        System.out.println("|                     ¿ESTA SEGURO?                        |");
        System.out.println("|           ASEGURECE QUE EL NUMERO SEA CORRECTO           |");
        System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
        System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
        System.out.println("------------------------------------------------------------");

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
    }catch(Exception e){
    }
}
}

