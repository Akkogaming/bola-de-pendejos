public class Admin{
        System.out.println("------------------------------------------------------------");
        System.out.println("|                   ¿QUÉ DESEA REALIZAR?                   |");
        System.out.println("------------------------------------------------------------");
        System.out.println("|        A.CONSULTAS              B.ELIMINAR EMPLEADOS     |");
        System.out.println("|        C.AÑADIR SALONES         D.AÑADIR EMPLEADOS       |");
        System.out.println("|        E.ELIMINAR SALONES       F.AÑADIR SERVICIOS       |");
        System.out.println("|        G.ELIMINAR SERVICIOS     H.AÑADIR MONTAJES        |");
        System.out.println("|        I.ELIMINAR MONTAJES      J.AÑADIR EVENTOS         |");
        System.out.println("|        K.ELIMINAR EVENTOS       L.SALIR                  |");
        System.out.println("------------------------------------------------------------");
        String answer=read.nextLine().toUpperCase(Locale.getDefault());

        switch(answer){
          case "A"://REALIZAR CONSULTAS
            Consultas consultProgram=new Consultas();
            break;

          case "B"://ELIMINAR EMPLEADO
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

        case "C"://Añadir Salon
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE LOS DATOS DEL SALON QUE DESEE AÑADIR       |");
            System.out.println("|       Y EL NUMERO DEL EMPLEADO ENCARGADO DEL SALON       |");
            System.out.println("------------------------------------------------------------");
        do{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     S)SALIR AL MENU                         A)AÑADIR     |");
            System.out.println("------------------------------------------------------------");
                String select=read.nextLine().toUpperCase(Locale.getDefault());
                        if(select.equals("S"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|               REGRESANDO AL MENU DE ADMIN                |");
            System.out.println("------------------------------------------------------------");
                                addingSalon++;
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
            addingSalon++;
                        }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|             POR FAVOR INGRESE UN VALOR VALIDO            |");
            System.out.println("------------------------------------------------------------");
                        }
        }while(addingSalon==0);
                        break;

                case "D"://AÑADIR EMPLEADO
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     INGRESE LOS DATOS DEL EMPLEADO QUE DESEE AÑADIR      |");
            System.out.println("------------------------------------------------------------");
                do{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     S)SALIR AL MENU                         A)AÑADIR     |");
            System.out.println("------------------------------------------------------------");
                String select2=read.nextLine().toUpperCase(Locale.getDefault());

                if(select2.equals("S"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|             REGRESANDO AL MENU DE ADMINISTRADOR          |");
            System.out.println("------------------------------------------------------------");
                        break;
                }
                if(select2.equals("A"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NOMBRE DEL EMPLEADO              |");
            System.out.println("------------------------------------------------------------");
                        String employeeName=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|          INGRESE EL APELLIDO PATERNO DEL EMPLEADO        |");
            System.out.println("------------------------------------------------------------");
                        String employee1stSurname=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|          INGRESE EL APELLIDO MATERNO DEL EMPLEADO        |");
            System.out.println("------------------------------------------------------------");
                        String employee2ndSurname=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|          INGRESE EL NUMERO TELEFONICO DEL EMPLEADO       |");
            System.out.println("------------------------------------------------------------");
                        String employeePhone=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|          INGRESE ELCORREO ELECTRONICO DEL EMPLEADO       |");
            System.out.println("------------------------------------------------------------");
                        String employeeEmail=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                        PERFECTO                          |");
            System.out.println("|                    EMPLEADO AÑADIDO                      |");
            System.out.println("------------------------------------------------------------");
Statement addEmployee = connect.createStatement("INSERT INTO empleado(nombre,A_paterno,A_materno,telefono,correo_E) VALUES ("+employeeName+","+employee1stSurname+","+employee2ndSurname+","+employeePhone+","+employeeEmail));
                        addingEmployee++;
                }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                   CARACTER INVALIDO                      |");
            System.out.println("|                INGRESE S/A SEGUN EL CASO                 |");
            System.out.println("------------------------------------------------------------");
                }
                }while(addingEmployee==0);
                        break;
                case "E":
        boolean deleteSalonDecision=false;
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NUMERO DEL SALON                 |");
            System.out.println("|                   QUE DESEE ELIMINAR                     |");
            System.out.println("------------------------------------------------------------");
                do{
            int numberSalon=read.nextInt();
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                     ¿ESTA SEGURO?                        |");
            System.out.println("|           ASEGURECE QUE EL NUMERO SEA CORRECTO           |");
            System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
            System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
            System.out.println("------------------------------------------------------------");
            String confirmSalom=read.nextLine().toUpperCase(Locale.getDefault());    
            if(confirmSalon.equals("Y")){            
            Statement deleteSalon = connect.createStatement("DELETE FROM salon where codigo="+numberSalon);
                    deleteSalonDecision=true;
        }if(confirmSalon.equals("N")){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           FAVOR DE INGRESAR UN NUMERO CORRECTO           |");
            System.out.println("------------------------------------------------------------");
        }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           SELECCIONE UNICAMENTE Y/N SEGUN EL CASO         |");
            System.out.println("------------------------------------------------------------");
                }while(deleteSalonDecision==false);
        break;

         case "F":
                boolean addingService=false;
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     INGRESE LOS DATOS DEL SERVICIO QUE DESEE AÑADIR      |");
            System.out.println("------------------------------------------------------------");
                do{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|     S)SALIR AL MENU                         A)AÑADIR     |");
            System.out.println("------------------------------------------------------------");
                String select3=read.nextLine().toUpperCase(Locale.getDefault());

                if(select3.equals("S"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|             REGRESANDO AL MENU DE ADMINISTRADOR          |");
            System.out.println("------------------------------------------------------------");
                        break;
                }
                if(select3.equals("A"){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NOMBRE DEL SERVICIO              |");
            System.out.println("------------------------------------------------------------");
                        String serviceName=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           INGRESE UNA DESCRIPCION DEL SERVICIO           |");
            System.out.println("------------------------------------------------------------");
                        String serviceDescription=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|               INGRESE EL PRECIO DEL SERVICIO             |");
            System.out.println("------------------------------------------------------------");
                        String servicePrice=read.nextLine().toLowerCase(Locale.getDefault());
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|       INGRESE EL NUMERO DEL TIPO DE SERVICIO QUE SEA     |");
            System.out.println("------------------------------------------------------------");
                        String serviceTypeService=read.nextInt();
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                        PERFECTO                          |");
            System.out.println("|                    SERVICIO AÑADIDO                      |");
            System.out.println("------------------------------------------------------------");
Statement addService = connect.createStatement("INSERT INTO servicio(nombre,descripcion,Precio,tipo_servicio) VALUES ("+serviceName+","+serviceDescription+","+servicePrice+","+serviceTypeService));
                        addingService=true;
                }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                   CARACTER INVALIDO                      |");
            System.out.println("|                INGRESE S/A SEGUN EL CASO                 |");
            System.out.println("------------------------------------------------------------");
                }
                }while(addingService==false);
                        break;
                        
                case "G"://ELIMINAR SERVICIOS
            boolean deleteServices=false;
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|              INGRESE EL NUMERO DEL SERVICIO              |");
            System.out.println("|                   QUE DESEE ELIMINAR                     |");
            System.out.println("------------------------------------------------------------");
                do{
            int numberService=read.nextInt();
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|                     ¿ESTA SEGURO?                        |");
            System.out.println("|           ASEGURECE QUE EL NUMERO SEA CORRECTO           |");
            System.out.println("|               ESTA ACCIÓN ES IRREVERSIBLE                |");
            System.out.println("|                 Y)CONFIRMAR N)DECLINAR                   |");
            System.out.println("------------------------------------------------------------");
            String confirmDecision2=read.nextLine().toUpperCase(Locale.getDefault());    
            if(confirmDecision2.equals("Y")){            
            Statement deleteService = connect.createStatement("DELETE FROM servicios where codigo="+numberService);
                    deleteServices=true;
        }if(confirmDecision.equals("N")){
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           FAVOR DE INGRESAR UN NUMERO CORRECTO           |");
            System.out.println("------------------------------------------------------------");
        }else{
            System.out.println("------------------------------------------------------------");                                                        
            System.out.println("|           SELECCIONE UNICAMENTE Y/N SEGUN EL CASO         |");
            System.out.println("------------------------------------------------------------");
                }while(deleteServices==false);
        break;
                        

                        
                                
                           
                
}
