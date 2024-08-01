import java.sql.*;
import java.util.Locale;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        String direction = "jdbc:mysql://localhost:3306/eventos"; //luis tu le cambias el puerto aqui xd
        Connection connect = DriverManager.getConnection(direction, "root", "");
        Statement statement = connect.createStatement(); //lo usare luego... creo.
        boolean isLoggedIn = false;
        
        //System.out.println("                                                            ");
        System.out.println("");
        System.out.println("------------------------------------------------------------"); //60 caracteres
        System.out.println("|                       BLUE PALACE                        |");
        System.out.println("------------------------------------------------------------");
        System.out.println("|           ELIGA UNA OPCION DE LAS SIGUENTES              |");
        System.out.println("|                                                          |");
        System.out.println("|          1. iniciar sesion como administrador            |");
        System.out.println("|         2. iniciar sesion como empleado regular          |");
        System.out.println("|        3. consultar una reservacion como cliente         |");
        System.out.println("|                                                          |");
        System.out.println("------------------------------------------------------------");   

        //normalizar la letra a mayuscula

        Scanner read = new Scanner(System.in); 
        String answer = read.nextLine().toUpperCase(Locale.getDefault());

        while (!isLoggedIn) {
            
        
            switch (answer) {
                case "1":
                    LoginA.LoginA();
                    break;
                case "2":
                    LoginE.LoginE();
                    break;  
                case "3":
                    Consultas.Consultas();
                    break;        
                default:
                    System.out.println("Eliga una de las opciones correctas por favor");
                    break;
        }

    }

        
        read.close();   //asi se cierra el scanner???????xd
       
    }
}
