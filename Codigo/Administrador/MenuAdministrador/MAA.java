package Administrador.MenuAdministrador;

import java.sql.*;
import java.util.Scanner;

public class MAA {
    public static Connection connect;
    public static Statement statement;
    public static Scanner leer;

    // menu admin all

    public static boolean MAA() {
        MA.MA();
        return true;
    }
}
