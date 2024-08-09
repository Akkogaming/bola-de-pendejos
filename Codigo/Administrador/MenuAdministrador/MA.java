package Administrador.MenuAdministrador;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Administrador.Admin;

public class MA {

    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    public static boolean MA() {

        leer = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

            System.out.print("\033\143");
            System.out.flush();

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                       MENU ADMINISTRADOR                     ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║                      1. Consultas                            ║");
            System.out.println("║                      2. Registrar datos                      ║");
            System.out.println("║                      3. Modificar datos                      ║");
            System.out.println("║                      4. Eliminar datos                       ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║                      5. Salir                                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            String answer = leer.nextLine();

            switch (answer) {
                case "1":
                    Admin.handleConsultas();
                    break;
                case "2":
                    R();
                    break;
                case "3":
                    M();
                    break;
                case "4":
                    E();
                    break;
                case "5":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        return true;
    }

    public static void R() {
        System.out.print("\033\143");
        System.out.flush();
        leer = new Scanner(System.in);
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                       REGISTRAR DATOS                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      1. Registrar Empleados                  ║");
        System.out.println("║                      2. Registrar Salones                    ║");
        System.out.println("║                      3. Registrar Servicios                  ║");
        System.out.println("║                      4. Registrar Montajes                   ║");
        System.out.println("║                      5. Registrar Eventos                    ║");
        System.out.println("║                      6. Registrar Cliente                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      7. Cancelar                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String answer = leer.nextLine();
        switch (answer) {
            case "1":
                Admin.insertarEmpleado();
                break;
            case "2":
                Admin.añadirSalon();
                break;
            case "3":
                Admin.añadirServicio();
                break;
            case "4":
                Admin.añadirMontaje();
                break;
            case "5":
                Admin.añadirEvento();
                break;
            case "6":
                Admin.añadirCliente();
                break;
            case "7":
                MA();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

    public static void M() {
        leer = new Scanner(System.in);
        System.out.print("\033\143");
        System.out.flush();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      MODIFICAR DATOS                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      1. Modificar Empleados                  ║");
        System.out.println("║                      2. Modificar Salones                    ║");
        System.out.println("║                      3. Modificar Servicios                  ║");
        System.out.println("║                      4. Modificar Montajes                   ║");
        System.out.println("║                      5. Modificar Eventos                    ║");
        System.out.println("║                      6. Modificar Cliente                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      7. Cancelar                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String answer = leer.nextLine();
        switch (answer) {
            case "1":
                Admin.editarEmpleado();
                break;
            case "2":
                Admin.editarSalon();
                break;
            case "3":
                Admin.editarServicio();
                break;
            case "4":
                Admin.editarMontaje();
                break;
            case "5":
                Admin.editarEvento();
                break;
            case "6":
                Admin.editarCliente();
                break;
            case "7":
                MA();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

    public static void E() {
        leer = new Scanner(System.in);

        System.out.print("\033\143");
        System.out.flush();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                       ELIMINAR DATOS                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      1. Eliminar Empleados                   ║");
        System.out.println("║                      2. Eliminar Salones                     ║");
        System.out.println("║                      3. Eliminar Servicios                   ║");
        System.out.println("║                      4. Eliminar Montajes                    ║");
        System.out.println("║                      5. Eliminar Eventos                     ║");
        System.out.println("║                      6. Eliminar Clientes                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      7. Cancelar                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        String answer = leer.nextLine();
        switch (answer) {
            case "1":
                Admin.eliminarEmpleado();
                break;
            case "2":
                Admin.eliminarSalon();
                break;
            case "3":
                Admin.eliminarServicio();
                break;
            case "4":
                Admin.eliminarMontaje();
                break;
            case "5":
                Admin.eliminarEvento();
                break;
            case "6":
                Admin.eliminarCliente();
                break;
            case "7":
                MA();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

    }
}
