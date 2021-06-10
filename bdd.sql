create database UDEE;
use UDEE;
drop database UDEE;

/*Facturas*/
create table Bills (
	id_bill int not null,
	id_user int not null,
	id_address int not null,
    number_measurer int not null,
    measure_start int not null,
    measure_end int not null,
    consumption_total int not null,
	date_time_start datetime,/*son solo fechas*/
	date_time_End datetime,/*son solo fechas*/
    id_rate int not null,
    total int not null,/*(Consumo * Tarifa)*/
	CONSTRAINT `PK-Bill` PRIMARY KEY (id_bill),
	CONSTRAINT `FK-IdUser` FOREIGN KEY (id_user) references Users(id_user),
    CONSTRAINT `FK-IdAddress` FOREIGN KEY (id_address) references Address(id_address),
    CONSTRAINT `FK-IdRate` FOREIGN KEY (id_rate) references Rates(id_rate));

/*Usuarios*/
CREATE TABLE Users(
	id_user INT NOT NULL AUTO_INCREMENT,
    id_client int not null,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
	CONSTRAINT `PK-User` PRIMARY KEY (id_user),
    CONSTRAINT `FK-IdClient` FOREIGN KEY (id_client) references Clients(id_client));

/*Clientes*/
CREATE TABLE Clients(
	id_client INT NOT NULL auto_increment,
    id_address int not null,
    name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(40) not null,
    constraint `PK-Client` PRIMARY KEY(id_client),
    CONSTRAINT `FK-IdAddress` FOREIGN KEY (id_address) references Address(id_address));


/*direcciones*/
create table Address(
	id_address int not null auto_increment,
    id_rate int not null,
    street varchar(30) not null,
    number int not null,
    constraint `PK-Address` primary key(id_address),
    constraint `FK-Rate` foreign key(id_rate) references Rates(id_rate)
);

/*tarifas*/
create table Rates(
	id_rate int not null auto_increment,
    price float not null,
    constraint `PK-Rate` primary key(id_rate)
);

/*Medidores*/
create table meters(
	id_meter int not null auto_increment,
    id_address int not null,
	serial_number varchar(30) not null,
    password varchar(30) not null,
    id_brand int not null,
    id_model int not null,
    constraint `PK-Meter` primary key(id_meter),
    constraint `FK-Brand` foreign key(id_brand) references Brands(id_brand),
    constraint `FK-Address` foreign key(id_address) references Address(id_address),
    constraint `FK-Model` foreign key(id_model) references Models(id_model)
);


/*Mediciones*/
create table Measurements(
	id_measurement int not null auto_increment,
    id_bill int not null,
    id_meter int not null,
    measurement varchar(30) not null,
    date date not null,
    constraint `PK-Measurement` primary key(id_measurement),
    constraint `FK-Bill` foreign key(id_bill) references udee.bills(id_bill),
    constraint `FK-Meter` foreign key(id_meter) references udee.meters(id_meter)
);
drop table Measurements;
/*Marcas*/
create table Brands(
	id_brand int not null auto_increment,
    name varchar(30)not null,
    constraint `PK-Brand` primary key(id_brand)
);


/*Modelos*/
create table Models(
	id_model int not null auto_increment,
    name varchar(30) not null,
    constraint `PK-Model` primary key(id_model)
);


select * from Brands;
select * from Models;
select * from Measures;
select * from Rates;
select * from Address;



/*
uen dia

Tenes las relaciones al revés en muchos csasos

Por ej :

Address tiene un id_measurement, no deberia ya que  Measurement 
debería relacionarse con meter y meter con address.

create table Address(
	id_address int not null auto_increment,
    //id_measure int not null,
    id_rate int not null,
    street varchar(30) not null,
    number int not null,
    constraint `PK-Address` primary key(id_address),
    //constraint `FK-Measure` foreign key(id_measure) references Measures(id_measure),
    constraint `FK-Rate` foreign key(id_rate) references Rates(id_rate)
);

create table meters(
	id_meter int not null auto_increment,
    id_address int not null,
	serial_number varchar(30) not null,
    password varchar(30) not null,
    id_brand int not null,
    id_model int not null,
    constraint `PK-Meter` primary key(id_meter),
    constraint `FK-Brand` foreign key(id_brand) references Brands(id_brand),
    constraint `FK-Address` foreign key(id_address) references Address(id_address),
    constraint `FK-Model` foreign key(id_model) references Models(id_model)
);

create table Measurements(
	id_measurement int not null auto_increment,
    id_bill int not null,
    id_meter int not null,
    measurement varchar(30) not null,
    date date not null,
    constraint `PK-Measurement` primary key(id_measurement),
    constraint `FK-Bill` foreign key(id_bill) references Bills(id_bill),
    constraint `FK-Meter` foreign key(id_meter) references Meters(id_meter)
);



En bills también, un measurement debería tener un Bill.

Es decir 1 BILL -> n MEASUREMENTS, en ese caso la relación siempre 
se especifica por la propagación de la clave en el N .

create table Bills (
	id_bill int not null,
	id_user int not null,
	id_address int not null,
    id_bill int not null,
    number_measurer int not null,
    measure_start int not null,
    measure_end int not null,
    consumption_total int not null,
	date_time_start datetime,/*son solo fechas
	date_time_End datetime,/*son solo fechas
    id_rate int not null,
    total int not null,/*(Consumo * Tarifa)
	CONSTRAINT `PK-Bill` PRIMARY KEY (id_bill),
	CONSTRAINT `FK-IdUser` FOREIGN KEY (id_user) references Users(id_user),
    CONSTRAINT `FK-IdAddress` FOREIGN KEY (id_address) references Address(id_address),
    CONSTRAINT `FK-IdRate` FOREIGN KEY (id_rate) references Rates(id_rate));


Por lo demás , esta bien , revisaría esas cosas, tabla de measurements 
/ address / clients / bills y sus relaciones

*/

/*
● Cliente
● Domicilio
● Numero de medidor
● Medición inicial
● Medición final
● Consumo total en Kwh
● Fecha y hora medición inicial
● Fecha y hora medición final
● Tipo de tarifa
● Total a pagar */

