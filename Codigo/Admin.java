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
                boolean deleteEmployee=false;
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NUMERO DEL EMPLEADO              |");
            System.out.println("|                   QUE DESEE ELIMINAR                     |");
            System.out.println("------------------------------------------------------------");
                do{
            int numberEmployee=read.nextInt();
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                     ¿ESTA SEGURO?                        |");
            System.out.println("|           ASEGURECE QUE EL NUMERO SEA CORRECTO           |");
            System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
            System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
            System.out.println("------------------------------------------------------------");
            String confirmDecision=read.nextLine().toUpperCase(Locale.getDefault());    
            if(confirmDecision.equals("Y")){            
            Statement statement = connect.createStatement("DELETE FROM empleados where codigo="+numberEmployee);
                    deleteEmployee=true;
        }if(confirmDecision.equals("N")){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           FAVOR DE INGRESAR UN NUMERO CORRECTO           |");
            System.out.println("------------------------------------------------------------");
        }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           SELECCIONE UNICAMENTE Y/N SEGUN EL CASO         |");
            System.out.println("------------------------------------------------------------");
                }while(deleteEmployee==0);
}
