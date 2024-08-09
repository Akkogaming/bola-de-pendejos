package Administrador.MenuConsultas;

import java.util.Scanner;

import Consultascarpeta.Consultas;

public class Paginas {
    public static Scanner leer;

    public static void P1() {

        leer = new Scanner(System.in);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               Selecciona la consulta a realizar:             ║");
        System.out.println("║             1. Reservaciones                                 ║");
        System.out.println("║             2. Equipamiento requerido                        ║");
        System.out.println("║             3. Servicios requeridos                          ║");
        System.out.println("║             4. Reservaciones para el mismo salón             ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║             5. Siguente pagina                               ║");
        System.out.println("║             6. Salir                                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String opinion = leer.nextLine();
        switch (opinion) {
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
                P2();
                break;
            case "6":
                Consultas.consulta17();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

    public static void P2() {

        leer = new Scanner(System.in);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             1. Servicios del mismo tipo                      ║");
        System.out.println("║             2. Reservaciones del cliente                     ║");
        System.out.println("║             3. Reservaciones con un servicio específico      ║");
        System.out.println("║             4. Reservaciones con un equipo específico        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║             5. Pagina siguente                               ║");
        System.out.println("║             6. Pagina anterior                               ║");
        System.out.println("║             7 . Salir                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String opinion = leer.nextLine();
        switch (opinion) {
            case "1":
                Consultas.consulta5();
            case "2":
                Consultas.consulta6();
                break;
            case "3":
                Consultas.consulta7();
                break;
            case "4":
                Consultas.consulta8();
                break;
            case "5":
                P3();
                break;
            case "6":
                P1();
                break;
            case "7":
                // !vacio para nada
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void P3() {

        leer = new Scanner(System.in);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             1. Reservaciones con un montaje específico       ║");
        System.out.println("║             2. Reservaciones en el mismo mes                 ║");
        System.out.println("║             3. Reservaciones asignados a un empleado         ║");
        System.out.println("║             4. consultar empleados                           ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║             5. Pagina siguente                               ║");
        System.out.println("║             6. Pagina anterior                               ║");
        System.out.println("║             7 . Salir                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        String opinion = leer.nextLine();
        switch (opinion) {
            case "1":
                Consultas.consulta9();
                break;
            case "2":
                Consultas.consulta10();
                break;
            case "3":
                Consultas.consulta11();
                break;
            case "4":
                Consultas.consulta12();
                break;
            case "5":
                P4();
                break;
            case "6":
                P2();
                break;
            case "7":
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

    public static void P4() {

        leer = new Scanner(System.in);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             1. consultar salones                             ║");
        System.out.println("║             2. consultar servicios                           ║");
        System.out.println("║             3. consultar montajes                            ║");
        System.out.println("║             4. consultar eventos                             ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║             5. Pagina anterior                               ║");
        System.out.println("║             6 . Salir                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        String opinion = leer.nextLine();
        switch (opinion) {
            case "1":
                Consultas.consulta13();
                break;
            case "2":
                Consultas.consulta14();
                break;
            case "3":
                Consultas.consulta15();
                break;
            case "4":
                Consultas.consulta16();
                break;
            case "5":
                P3();
                break;
            case "6":

                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

}