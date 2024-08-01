import java.util.Scanner;
public class LoginA {
    public static void LoginA() {
         //Variables
          Scanner read=new Scanner(System.in);
          boolean mommyYesmommy=false;
      System.out.println("------------------------------------------------------------");
      System.out.println("-                #LOGIN DEL ADMINISTRADOR#                 -");
      do{
        System.out.println("------------------------------------------------------------");
        System.out.println("-                   INGRESE SU NOMBRE                      -");
        System.out.println("------------------------------------------------------------");
        String name=read.nextLine();
        System.out.println("------------------------------------------------------------");
        System.out.println("-                   INGRESE APELLIDO(s)                    -");
        System.out.println("------------------------------------------------------------");
        String lastName=read.nextLine();
        if(name.equals("MAURICIO") && lastName.equals("GUERRERO") ){
            System.out.println("------------------------------------------------------------");
            System.out.println("-                   INGRESE CONTRASEÑA                     -");
            System.out.println("------------------------------------------------------------");
            String password=read.nextLine();
                if(password.equals("URIGOD")){
                System.out.println("------------------------------------------------------------");
                System.out.println("-          BIENVENIDO ADMINISTRADOR MAURICIO               -");
                System.out.println("------------------------------------------------------------");
                    mommyYesmommy=true;
                }else{
                        System.out.println("------------------------------------------------------------");
                        System.out.println("-              NOMBRE O CONTRASEÑA INCORRECTOS             -");
                        System.out.println("-                VUELVA A INGRESAR SUS DATOS               -");
                        System.out.println("------------------------------------------------------------");
                            }
                }
                    else{
                        System.out.println("------------------------------------------------------------");
                        System.out.println("-              NOMBRE O CONTRASEÑA INCORRECTOS             -");
                        System.out.println("-                VUELVA A INGRESAR SUS DATOS               -");
                        System.out.println("------------------------------------------------------------");
                            }
    }while(mommyYesmommy==false);
        

}
