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
            Statement deleteEmployee = connect.createStatement("DELETE FROM empleados where codigo="+numberEmployee);
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
        break;

        case "C":
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE LOS DATOS DEL SALON QUE DESEE AÑADIR       |");
            System.out.println("|       Y EL NUMERO DEL EMPLEADO ENCARGADO DEL SALON       |");
            System.out.println("------------------------------------------------------------");
        do{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     S)SALIR                                 A)AÑADIR     |");
            System.out.println("------------------------------------------------------------");
                String select=read.nextLine().toUpperCase(Locale.getDefault());
                        if(select.equals("S"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|               REGRESANDO AL MENU DE ADMIN                |");
            System.out.println("------------------------------------------------------------");
                                addingEmployee++;
                                break;
                        }
                        if(select.equals("A"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NOMBRE DEL SALON                 |");
            System.out.println("------------------------------------------------------------");
                        String salonName=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           INGRESE LA CAPACIDAD MAXIMA DE PERSONAS        |");
            System.out.println("|              QUE PUEDE HABER DENTRO DEL SALON            |");
            System.out.println("------------------------------------------------------------");
                        String salonCapacity=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           INGRESE EL CODIGO POSTAL DEL SALON             |");
            System.out.println("------------------------------------------------------------");
                        String salonCp=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE LA CALLE DONDE SE ENCUENTRA EL SALON       |");
            System.out.println("------------------------------------------------------------");
                        String salonWay=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE LA CIUDAD DONDE SE ENCUENTRA EL SALON      |");
            System.out.println("------------------------------------------------------------");
                        String salonCity=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE EL ESTADO DONDE SE ENCUENTRA EL SALON      |");
            System.out.println("------------------------------------------------------------");
                        String salonState=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|    INGRESE EL NUMERO DE EMPLEADO ENCARGADO DEL SALON     |");
            System.out.println("------------------------------------------------------------");
                        String employeeNumber=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                        PERFECTO                          |");
            System.out.println("|                      SALON AÑADIDO                       |");
            System.out.println("------------------------------------------------------------");
Statement addSalon = connect.createStatement("INSERT INTO salon(capacidad,codigoPostal,calle,ciudad,Estado) VALUES ("+salonName+","+salonCapacity+","+salonCp+","+salonWay+","+salonCity+","+salonState+","+employeeNumber")");
            addingEmployee++;
                        }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|             POR FAVOR INGRESE UN VALOR VALIDO            |");
            System.out.println("------------------------------------------------------------");
                        }
        }while(addingEmployee==0);
                                
                           
                
}
