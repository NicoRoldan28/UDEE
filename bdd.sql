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

-------------------------------------------------------------------------------------



DELIMITER //
CREATE PROCEDURE up_clients(new_id_address INT(11),new_name VARCHAR(30), new_last_name VARCHAR(30),new_email VARCHAR(40))
BEGIN
	INSERT INTO Clients(id_address,name,last_name,email)
        VALUES(new_id_address,new_name,new_last_name,new_email);
END //
DELIMITER //
CREATE PROCEDURE up_users(new_id_client INT(11), new_username VARCHAR(30), new_password VARCHAR(30))
BEGIN
	INSERT INTO Users(id_client, username, password)
        VALUES (new_id_client,new_username,new_password);
END //
DELIMITER //
CREATE PROCEDURE up_rates(new_price FLOAT)
BEGIN
	INSERT INTO Rates(price)
        VALUES(new_price);
END //
DELIMITER //
CREATE PROCEDURE up_address(new_id_rate INT(11),new_street VARCHAR(30), new_number INT(11))
BEGIN
	INSERT INTO Address(id_rate,street,number)
        VALUES(new_id_rate,new_street,new_number);
END //
DELIMITER //
CREATE PROCEDURE up_brands(new_name VARCHAR(30))
BEGIN
	INSERT INTO Brands(name)
        VALUES(new_name);
END //
DELIMITER //
CREATE PROCEDURE up_models(new_name VARCHAR(30))
BEGIN
	INSERT INTO Models(name)
        VALUES(new_name);
END//
DELIMITER //
CREATE PROCEDURE up_meters(new_id_address INT(11), new_serial_numbers VARCHAR(30), new_password VARCHAR(30), new_id_brand INT(11), new_id_model INT(11))
BEGIN
	INSERT INTO meters(id_address,serial_number,password,id_brand,id_model)
        VALUES(new_id_address,new_serial_numbers,new_password,new_id_brand,new_id_model);
END //
DELIMITER //
CREATE PROCEDURE up_measurements(new_id_bill INT(11),new_id_meter INT(11),new_measurement VARCHAR(30), new_date DATE)
BEGIN
	INSERT INTO Measurements(id_bill,id_meter,measurement,date)
        VALUES(new_id_bill,new_id_meter,new_measurement,new_date);
END //
DELIMITER //
CREATE PROCEDURE up_bills(new_id_bill INT(11), new_id_user INT(11), new_id_address INT(11), new_id_measurement INT(11), new_measure_start INT(11), new_measure_end INT(11), new_date_time_start datetime, new_date_time_end datetime, new_id_rate INT(11))
BEGIN
	DECLARE new_total DOUBLE;
        DECLARE new_consumption_total DOUBLE;
        SET new_consumption_total = (SELECT measurement FROM Measurements WHERE new_id_measurement = id_measurement);
        SET new_total = new_consumption_total * (SELECT  price 
		FROM Rates
		WHERE id_rate = new_id_rate);
        INSERT INTO Bills(id_bill,id_user,id_address,id_measurement,measure_start,measure_end,consumption_total,date_time_start,date_time_End,id_rate,total)
	VALUES(new_id_bill,new_id_user,new_id_address,new_id_measurement,new_measure_start,new_measure_end,new_consumption_total,new_date_time_start,new_date_time_end,new_id_rate,new_total);
END //
-----------------------------------------------------------------------
DELIMITER //
CREATE PROCEDURE down_clients(new_id_client INT(11))
BEGIN 
        DELETE FROM Clients WHERE id_address = new_id_client;
END //
DELIMITER //
CREATE PROCEDURE down_users(new_id_user INT(11))
BEGIN 
        DELETE FROM Users WHERE id_user = new_id_user;
END //
DELIMITER //
CREATE PROCEDURE down_rates(new_id_rate INT(11))
BEGIN 
        DELETE FROM Rates WHERE id_rate = new_id_rate;
END //
DELIMITER //
CREATE PROCEDURE down_address(new_id_address INT(11))
BEGIN 
        DELETE FROM Address WHERE id_address = new_id_address;
END //
DELIMITER //
CREATE PROCEDURE down_brands(new_id_brand INT(11))
BEGIN 
        DELETE FROM Brands WHERE id_brand = new_id_brand;
END //
DELIMITER //
CREATE PROCEDURE down_models(new_id_model INT(11))
BEGIN 
        DELETE FROM Models WHERE id_model = new_id_model;
END //
DELIMITER //
CREATE PROCEDURE down_meters(new_id_meter INT(11))
BEGIN 
        DELETE FROM meters WHERE id_meter = new_id_meter;
END //
DELIMITER //
CREATE PROCEDURE down_measurements(new_id_measurement INT(11))
BEGIN 
        DELETE FROM Measurements WHERE id_measurement = new_id_measurement;
END //
DELIMITER //
CREATE PROCEDURE down_bills(new_id_bill INT(11))
BEGIN 
        DELETE FROM Bills WHERE id_bill = new_id_bill;
END //

-----------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE update_clients(new_id_client INT(11),new_id_address INT(11),new_name VARCHAR(30),new_last_name VARCHAR(30),new_email VARCHAR(40))
BEGIN 
        UPDATE Clients SET id_address = new_id_address,name = new_name,last_name = new_last_name,email = new_email WHERE id_client = new_id_client;
END //
DELIMITER //
CREATE PROCEDURE update_users(new_id_user INT(11),new_id_client INT(11),new_username VARCHAR(30),new_password VARCHAR(30))
BEGIN 
        UPDATE Users SET id_client = new_id_client,username = new_username,password = new_password WHERE id_user = new_id_user;
END //
DELIMITER //
CREATE PROCEDURE update_rates(new_id_rate INT(11),new_price FLOAT)
BEGIN 
        UPDATE Rates SET price = new_price WHERE id_rate = new_id_rate;
END //
DELIMITER //
CREATE PROCEDURE update_address(new_id_address INT(11),new_id_rate INT(11),new_street VARCHAR(30),new_number INT(11))
BEGIN 
        UPDATE Address SET id_rate = new_id_rate,street = new_street,number = new_number WHERE id_address = new_id_address;
END //
DELIMITER //
CREATE PROCEDURE update_brands(new_id_brand INT(11),new_name VARCHAR(30))
BEGIN 
        UPDATE Brands SET name = new_name WHERE id_brand = new_id_brand;
END //
DELIMITER //
CREATE PROCEDURE update_models(new_id_model INT(11),new_name VARCHAR(30))
BEGIN   
        UPDATE Models SET name = new_name WHERE id_model = new_id_model;
END //
DELIMITER //
CREATE PROCEDURE update_meters(new_id_meter INT(11),new_id_address INT(11),new_serial_number VARCHAR(30),new_password VARCHAR(30),new_id_brand INT(11),new_id_model INT(11))
BEGIN 
        UPDATE meters SET id_address = new_id_address,serial_number = new_serial_number,password = new_password,id_brand = new_id_brand,id_model = new_id_model WHERE id_meter = new_id_meter;
END //
DELIMITER //
CREATE PROCEDURE update_measurements(new_id_measurement INT(11),new_id_bill INT(11),new_id_meter INT(11), new_measurement VARCHAR(30),new_date DATE)
BEGIN 
        UPDATE Measurements SET id_bill = new_id_bill,id_meter = new_id_meter,measurement = new_measurement,date = new_date WHERE id_measurement = new_id_measurement;
END //
DELIMITER //
CREATE PROCEDURE update_bills(new_id_bill INT(11), new_id_user INT(11), new_id_address INT(11), new_id_measurement INT(11), new_measure_start INT(11), new_measure_end INT(11), new_date_time_start datetime, new_date_time_end datetime, new_id_rate INT(11))
BEGIN 
        DECLARE new_total DOUBLE;
        DECLARE new_consumption_total DOUBLE;
        SET new_consumption_total = (SELECT measurement FROM Measurements WHERE new_id_measurement = id_measurement);
	SET new_total = new_consumption_total * (SELECT  price 
			                                FROM Rates
			                                 WHERE id_rate = new_id_rate);
        UPDATE Bills 
        SET id_user=new_id_user,id_address=new_id_address,id_measurement=new_id_measurement,measure_start=new_measure_start,measure_end=new_measure_end,consumption_total=new_consumption_total,date_time_start=new_date_time_start,date_time_End=new_date_time_end,id_rate=new_id_rate,total=new_total 
        WHERE id_bill = new_id_bill;
END //

-----------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE select_clients(new_id_client INT(11))
BEGIN 
        SELECT C.id_client,C.name,C.last_name,C.email,U.id_user,U.username,U.password,A.id_address,A.street,A.number,R.id_rate,R.price,M.id_meter,M.serial_number,M.password,ME.id_measurement,ME.measurement,ME.date,B.id_bill,B.id_measurement,B.measure_start,B.measure_end,B.consumption_total,B.date_time_start,B.date_time_End,B.total
            FROM Clients C 
        INNER JOIN Address A 
                ON C.id_address = A.id_address 
        INNER JOIN Rates R 
                ON R.id_rate = A.id_rate 
        INNER JOIN meters M
                ON M.id_address = A.id_address
        INNER JOIN Measurements ME
                ON ME.id_meter = M.id_meter
        INNER JOIN Bills B
                ON B.id_address = A.id_address
        INNER JOIN Users U
                ON U.id_client = C.id_client
            WHERE C.id_client = new_id_client; 
END //

DELIMITER //
CREATE PROCEDURE cal_total(IN new_id_bill INT (11))
BEGIN 
        DECLARE price2 DOUBLE;
        DECLARE total2 INT;
        SET price2 = (SELECT  price 
                        FROM Rates R 
                        INNER JOIN Bills B 
                        ON R.id_rate = B.id_rate
                        WHERE B.id_bill = new_id_bill);
        SET total2 = (price2 * (SELECT consumption_total 
                                        FROM Bills 
                                        WHERE id_bill = new_id_bill));
        SELECT total2;
END //

-----------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE select_all_address()
BEGIN 
        SELECT id_address,id_rate,street,number FROM Address;
END //
DELIMITER //
CREATE PROCEDURE select_all_bills()
BEGIN 
        SELECT id_bill,id_address,id_measurement,measure_start,measure_end,consumption_total,date_time_start,date_time_End,id_rate,total FROM Bills;
END //
DELIMITER //
CREATE PROCEDURE select_all_brands()
BEGIN 
        SELECT id_brand,name FROM Brands;
END //
DELIMITER //
CREATE PROCEDURE select_all_clients()
BEGIN 
        SELECT id_client,id_address,name,last_name,email FROM Clients;
END //
DELIMITER //
CREATE PROCEDURE select_all_measurements()
BEGIN 
        SELECT id_measurement,id_bill,id_meter,measurement,date FROM Measurements;
END //
DELIMITER //
CREATE PROCEDURE select_all_meters()
BEGIN 
        SELECT id_meter,id_address,serial_number,password,id_brand,id_model FROM meters;
END //
DELIMITER //
CREATE PROCEDURE select_all_models()
BEGIN 
        SELECT id_model,name FROM Models;
END //
DELIMITER //
CREATE PROCEDURE select_all_rates()
BEGIN 
        SELECT id_rate,price FROM Rates;
END //
DELIMITER //
CREATE PROCEDURE select_all_users()
BEGIN 
        SELECT id_user,id_client,username,password FROM Users;
END //

-----------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE bills_for_date(IN new_id_client INT(11), IN new_date_start DATE, IN new_date_end DATE)
BEGIN 
       SELECT C.id_client,C.name,C.last_name,C.email,U.id_user,U.username,U.password,A.id_address,A.street,A.number,R.id_rate,R.price,M.id_meter,M.serial_number,M.password,ME.id_measurement,ME.measurement,ME.date,B.id_bill,B.id_measurement,B.measure_start,B.measure_end,B.consumption_total,B.date_time_start,B.date_time_End,B.total
            FROM Clients C 
        INNER JOIN Address A 
                ON C.id_address = A.id_address 
        INNER JOIN Rates R 
                ON R.id_rate = A.id_rate 
        INNER JOIN meters M
                ON M.id_address = A.id_address
        INNER JOIN Measurements ME
                ON ME.id_meter = M.id_meter
        INNER JOIN Bills B
                ON B.id_address = A.id_address
        INNER JOIN Users U
                ON U.id_client = C.id_client
            WHERE C.id_client = new_id_client AND B.date_time_start = new_date_start AND B.date_time_End = new_date_end;
END //

DELIMITER //
CREATE PROCEDURE bills_unpaid (IN new_id_client INT(11))
BEGIN 
        SELECT B.id_bill, B.id_address, B.id_measurement, B.id_rate, B.id_user, B.measure_start,B.measure_end,B.date_time_start,B.date_time_End,B.consumption_total,B.total,B.paid
        FROM Bills B
        INNER JOIN Clients C
        ON B.id_address = C.id_address
        WHERE C.id_client = new_id_client AND B.paid = 0;
END //

DELIMITER //
CREATE PROCEDURE price_kwh (IN new_id_client INT(11), IN new_date_start DATE, IN new_date_end DATE)
BEGIN 
        SELECT id_client 
        FROM Bills B
        INNER JOIN Clients C
        ON B.id_address = C.id_address
        WHERE 

END //