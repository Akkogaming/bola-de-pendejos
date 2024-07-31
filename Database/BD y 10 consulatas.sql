-- Active: 1720794583945@@127.0.0.1@3306@eventos
create database eventos

use eventos



create table empleados(
    numero int PRIMARY KEY AUTO_INCREMENT ,
    nombre VARCHAR (30) NOT NULL,
    A_paterno VARCHAR (30) NOT NULL,
    A_materno VARCHAR (30),
    telefono VARCHAR (10) NOT NULL UNIQUE,
    correo_E varchar (50) not NULL UNIQUE
)

create table tipo_evento(
    codigo int AUTO_INCREMENT PRIMARY KEY,
    precio float NOT NULL,
    descripcion VARCHAR (100) NOT NULL
)

create table montaje(
    codigo int PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR (100) NOT NULL
)

create table equipamiento (
    codigo int PRIMARY KEY AUTO_INCREMENT,
    stock int NOT NULL,
    precio float NOT NULL,
    nombre VARCHAR (20) NOT NULL
)

create Table tipo_servicio(
codigo int PRIMARY KEY AUTO_INCREMENT,
descripcion VARCHAR (100) not NULL,
costo float NOT NULL
)

create table servicios(
    codigo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (25) not NULL,
    descripcion VARCHAR (100) not NULL,
    Precio float not NULL,
    tipo_servicio int NOT NULL,
    FOREIGN KEY (tipo_servicio) REFERENCES tipo_servicio(codigo)
)

create table cliente(
    codigo INT PRIMARY KEY AUTO_INCREMENT ,
    nombre VARCHAR(30) NOT NULL,
    apaterno varchar (30) not NULL,
    amaterno VARCHAR (30),
    telefono VARCHAR (10) not NULL UNIQUE,
    empleado INT NOT NULL,
    FOREIGN KEY (empleado) REFERENCES empleados(numero)
)

create table servicios_cliente(
servicios INT,
cliente INT,
PRIMARY KEY (servicios, cliente),
FOREIGN KEY (servicios) REFERENCES servicios(codigo),
FOREIGN KEY (cliente) REFERENCES cliente(codigo)
)

create table salon(
codigo int PRIMARY KEY AUTO_INCREMENT,
capacidad int NOT NULL,
nombreSalon VARCHAR(30) NOT NULL,
codigopostal int NOT NULL,
calle VARCHAR(25) NOT NULL,
ciudad VARCHAR(20) NOT NULL,
Estado VARCHAR(27) not NULL,
empleado int NOT NULL,
FOREIGN KEY (empleado) REFERENCES empleados(numero) 
)

create table evento(
    numeroEvento int PRIMARY KEY,
    descripcion VARCHAR(30),
    salon INT NOT NULL,
    montaje INT NOT NULL,
    tipo_evento INT NOT NULL,
    FOREIGN KEY (salon) REFERENCES salon(codigo),
    FOREIGN KEY (montaje) REFERENCES montaje(codigo),
    FOREIGN KEY (tipo_evento) REFERENCES tipo_evento(codigo)
)

create table reservaciones(
    codigo INT PRIMARY KEY AUTO_INCREMENT,
    fechaevento DATE NOT NULL,
    Cant_Inv VARCHAR (5) NOT NULL,
    HoraI TIME not NULL,
    HoraF TIME NOT NULL,
    monto float NOT NULL,
    MontoT float NOT NULL,
    cliente INT NOT NULL,
    salon INT NOT NULL,
    evento INT NOT NULL,
    FOREIGN KEY (cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (salon) REFERENCES salon(codigo),
    FOREIGN KEY (evento) REFERENCES evento(numeroEvento)
)

create table equipo_reservado(
equipamiento INT,
reservacion int,
PRIMARY KEY (equipamiento, reservacion),
FOREIGN KEY (equipamiento) REFERENCES equipamiento(codigo),
FOREIGN KEY (reservacion) REFERENCES reservaciones(codigo)
)

/*
1. Información de una reservación
a. Fecha de la reservación
b. Hora de la reservación
c. Nombre del cliente
d. Tipo de evento
e. Nombre del salón
f. Dirección del salón
g. Tipo de montaje
h. Cantidad de invitados
i. Costo total del evento
*/

SELECT 
    Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion", /* date format */
    r.HoraI AS Hora_Inicio, 
    CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,  /* concat nombre */
    te.descripcion AS Tipo_Evento,
    s.nombreSalon AS Nombre_Salon,
    CONCAT(s.calle, ', ', s.ciudad, ', ', s.Estado, ' ', s.codigopostal) AS Direccion_Salon,
    m.descripcion AS Tipo_Montaje,
    r.Cant_Inv AS Cantidad_Invitados,
    r.MontoT AS Costo_Total
FROM 
    reservaciones r
JOIN 
    cliente c ON r.cliente = c.codigo
JOIN 
    evento e ON r.evento = e.numeroEvento
JOIN 
    salon s ON r.salon = s.codigo
JOIN 
    tipo_evento te ON e.tipo_evento = te.codigo
JOIN 
    montaje m ON e.montaje = m.codigo

    
/*
2. Equipamiento requerido en una reservación
a. Fecha de la reservación
b. Nombre del cliente
c. Nombre del salón
d. Descripción de cada quipo
e. Costo del equipo
*/ 
  SELECT 
    Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion", /* date format */
    CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,  /* concat nombre cliente */
    s.nombreSalon AS Nombre_Salon,
    eq.nombre AS Descripcion,
    eq.precio AS Costo_Equipo
FROM 
    equipo_reservado er
JOIN 
    reservaciones r ON er.reservacion = r.codigo
JOIN 
    cliente c ON r.cliente = c.codigo
JOIN 
    salon s ON r.salon = s.codigo
JOIN 
    equipamiento eq ON er.equipamiento = eq.codigo

/*
3. Servicios requeridos en una reservación
a. Fecha de reservación
b. Nombre del cliente
c. Nombre del salón
d. Descripción de cada servicio
e. Tipo de servicio
f. Costo del servicio
*/

SELECT 
    Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion", /* date format */
    CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,  /* concat nombre cliente */
    s.nombreSalon AS Nombre_Salon,
    sv.descripcion AS Descripcion_Servicio,
    ts.descripcion AS Tipo_Servicio,
    sv.Precio AS Costo_Servicio
FROM 
    servicios_cliente sc
JOIN 
    reservaciones r ON sc.cliente = r.cliente
JOIN 
    servicios sv ON sc.servicios = sv.codigo
JOIN 
    tipo_servicio ts ON sv.tipo_servicio = ts.codigo
JOIN 
    cliente c ON r.cliente = c.codigo
JOIN 
    salon s ON r.salon = s.codigo


/*
4. Reservaciones para el mismo salón
a. Nombre del salón
b. Fecha de la reservación
c. Nombre del cliente
d. Tipo de evento
*/



SELECT 
    s.nombreSalon AS Nombre_Salon,
    Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion",,   /* date format */
    CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,    /* concat nombre */
    te.descripcion AS Tipo_Evento
FROM 
    reservaciones r
JOIN 
    salon s ON r.salon = s.codigo
JOIN 
    cliente c ON r.cliente = c.codigo
JOIN 
    evento e ON r.evento = e.numeroEvento
JOIN 
    tipo_evento te ON e.tipo_evento = te.codigo





/*
5. Servicios del mismo tipo
a. Descripción del tipo de servicio
b. Descripción del servicio
c. Precio
*/

SELECT 
    ts.descripcion AS Descripcion_Tipo_Servicio,
    sv.descripcion AS Descripcion_Servicio,
    sv.Precio AS Precio
FROM 
    servicios sv
JOIN 
    tipo_servicio ts ON sv.tipo_servicio = ts.codigo

/* 6. Reservaciones que ha realizado el cliente
    --nombre del cliente
    --Fecha de la reservacion 
    --Salon de la reservacion
    --Tipo de evento
    --Cantidad de invitados
*/

SELECT
CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,
date_format(R.fechaevento,"%d-%m-%y") as Fecha,
s.codigo as Salon,
tp.descripcion as "Tipo de evento",
R.Cant_Inv as "cantidad de invitados"
FROM Reservaciones as R
Inner JOIN cliente as C on R.cliente = c.codigo
INNER JOIN salon as S on R.salon = s.codigo
INNER JOIN evento as e on R.evento = e.numeroEvento
INNER JOIN tipo_evento as tp on e.tipo_evento = tp.codigo

/*" 7.Reservaciones donde se ha solicitado un mismo servicio
    --Numero de la reservacion
    --Fecha de la reservacion 
    --Nombre del salon
    --Nombre del cliente
    --Tipo de evento
    --Cantidad de invitados
    --Descripcion del equipo
    --Costo del equipo
*/

SELECT
R.codigo as "numero de la reservacion",
Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion",
S.nombreSalon as "nombre del salon",
CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,
tp.descripcion as "tipo de evento",
R.Cant_Inv as "Cantidad de invitados",
eq.nombre as Equipamiento,
eq.precio as "Precio del equipamiento"
FROM reservaciones as R
INNER JOIN cliente as C on R.cliente = c.codigo
INNER JOIN salon as S on R.salon = S.codigo
INNER JOIN evento as e on R.evento = e.numeroEvento
INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo
INNER JOIN equipo_reservado as er on er.reservacion = R.codigo
iNNER JOIN equipamiento as eq on er.equipamiento = eq.codigo
INNER JOIN servicios_cliente as sc on sc.cliente = c.codigo
INNER JOIN servicios as ser on sc.servicios = ser.codigo 
WHERE ser.codigo = 44

/*" 8. Reservaciones que ha solicitado el mismo equipo
    --Numero de la reservacion
    --Fecha de la reservacion
    --Nombre del salon
    --Nombre del cliente
    --tipo de evento
    --Cantiadad de invitados
    --Descripcion del equipo
    --Costo del equipo
*/
SELECT
R.codigo as "numero de la reservacion",
Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion",
S.nombreSalon as "nombre del salon",
CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,
tp.descripcion as "tipo de evento",
R.Cant_Inv as "Cantidad de invitados",
eq.nombre as Equipamiento,
eq.precio as "Precio del equipamiento"
FROM reservaciones as R
INNER JOIN cliente as C on R.cliente = c.codigo
INNER JOIN salon as S on R.salon = S.codigo
INNER JOIN evento as e on R.evento = e.numeroEvento
INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo
INNER JOIN equipo_reservado as er on er.reservacion = R.codigo
iNNER JOIN equipamiento as eq on er.equipamiento = eq.codigo
WHERE eq.codigo=22

/*" 9. Reservaciones que han solicitado el mismo montaje
    --Numero de la reservacion
    --Fecha de la reservacion
    --Nombre del salon
    --Nombre del cliente
    --Tipo de evento
    --Cantidad de invitados
    --Descripcion del montaje
*/

SELECT
R.codigo as "Numero de la reservacion",
Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion",
S.nombreSalon as Salon,
CONCAT(c.nombre,' ', c.apaterno,' ',    IFNULL (CONCAT(c.amaterno,' '),' ') ) as cliente,
tp.descripcion as "Tipo de evento",
R.Cant_Inv as "cantidad de invitados",
m.descripcion as "Descripcion del montaje"
FROM reservaciones as R
INNER JOIN cliente as C on R.cliente = c.codigo
INNER JOIN salon as S on R.salon = S.codigo
INNER JOIN evento as e on R.evento = e.numeroEvento
INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo
INNER JOIN montaje as m on e.numeroEvento = m.codigo
WHERE m.codigo = 1

/*" 10.Reservaciones realizadas para el mismo mes
    --Fecha
    --Nombre del salon
    --tipo de evento
    --Cantidad de invitados
*/
SELECT
Date_format(R.fechaevento,"%d-%m-%y") as "Fecha de la reservacion",
s.nombreSalon as "nombre del salon",
tp.descripcion as "tipo de evento",
R.Cant_Inv as "Cantidad de invitados"
FROM reservaciones as R
INNER JOIN salon as s on R.salon = S.codigo
INNER JOIN evento as e on R.evento = e.numeroEvento
INNER JOIN tipo_evento as tp on e.numeroEvento = tp.codigo
WHERE month(r.fechaevento)=9