public class Admin{
        System.out.println("------------------------------------------------------------");
        System.out.println("|                   ¿QUÉ DESEA REALIZAR?                   |");
        System.out.println("|             A.CONSULTAS  B.ELIMINAR EMPLEADOS            |");
        System.out.println("|             C.AÑADIR SALONES D.AÑADIR EMPLEADOS          |");
        System.out.println("|             E.ELIMINAR SALONES F.AÑADIR SERVICIOS        |");
        System.out.println("|             G.ELIMINAR SERVICIOS  H.AÑADIR MONTAJES      |");
        System.out.println("|             I.ELIMINAR MONTAJES  J.AÑADIR EVENTOS        |");
        System.out.println("|             K.ELIMINAR EVENTOS  L.SALIR                  |");
        System.out.println("------------------------------------------------------------");
        String answer=read.nextLine().toUpperCase(Locale.getDefault());

        switch(answer){
          case "A":
            Consultas consultProgram=new Consultas();
            break;

          case "B":
            
        }
}
