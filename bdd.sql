-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema UDEE
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema UDEE
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `UDEE` DEFAULT CHARACTER SET utf8 ;
USE `UDEE` ;

-- -----------------------------------------------------
-- Table `UDEE`.`Rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Rates` (
  `id_rate` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` FLOAT NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_rate`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Brands` (
  `id_brand` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_brand`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Models`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Models` (
  `id_model` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `Brands_id_brand` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_model`),
  INDEX `fk_Models_Brands1_idx` (`Brands_id_brand` ASC),
  CONSTRAINT `fk_Models_Brands1`
    FOREIGN KEY (`Brands_id_brand`)
    REFERENCES `UDEE`.`Brands` (`id_brand`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Meters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Meters` (
  `id_meter` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `serial_number` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `Models_id_model` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_meter`),
  INDEX `fk_meters_Models1_idx` (`Models_id_model` ASC),
  CONSTRAINT `fk_meters_Models1`
    FOREIGN KEY (`Models_id_model`)
    REFERENCES `UDEE`.`Models` (`id_model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Users` (
  `id_user` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  `user_type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Address` (
  `id_address` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(30) NOT NULL,
  `number` INT(11) NOT NULL,
  `Rates_id_rate` INT(11) UNSIGNED NOT NULL,
  `Meters_id_meter` INT(11) UNSIGNED NOT NULL,
  `Users_id_user` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_address`),
  INDEX `fk_Address_Rates1_idx` (`Rates_id_rate` ASC),
  INDEX `fk_Address_Meters1_idx` (`Meters_id_meter` ASC),
  INDEX `fk_Address_Users1_idx` (`Users_id_user` ASC),
  CONSTRAINT `fk_Address_Rates1`
    FOREIGN KEY (`Rates_id_rate`)
    REFERENCES `UDEE`.`Rates` (`id_rate`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Address_Meters1`
    FOREIGN KEY (`Meters_id_meter`)
    REFERENCES `UDEE`.`Meters` (`id_meter`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Address_Users1`
    FOREIGN KEY (`Users_id_user`)
    REFERENCES `UDEE`.`Users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Bills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Bills` (
  `id_bill` INT(11) UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `total` FLOAT NOT NULL,
  `paid` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id_bill`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Measurements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Measurements` (
  `id_measurement` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `value` FLOAT NOT NULL,
  `measurement_start` FLOAT NOT NULL,
  `masurement_end` FLOAT NOT NULL,
  `date_start` DATETIME NOT NULL,
  `date_end` DATETIME NOT NULL,
  `invoiced` TINYINT(1) NOT NULL,
  `Address_id_address` INT(11) UNSIGNED NOT NULL,
  `Bills_id_bill` INT(11) UNSIGNED NULL,
  PRIMARY KEY (`id_measurement`),
  INDEX `fk_Measurements_Address1_idx` (`Address_id_address` ASC),
  INDEX `fk_Measurements_Bills1_idx` (`Bills_id_bill` ASC),
  CONSTRAINT `fk_Measurements_Address1`
    FOREIGN KEY (`Address_id_address`)
    REFERENCES `UDEE`.`Address` (`id_address`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measurements_Bills1`
    FOREIGN KEY (`Bills_id_bill`)
    REFERENCES `UDEE`.`Bills` (`id_bill`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-------------------------------------------------------------------------------------


DELIMITER //
CREATE PROCEDURE up_users(new_username VARCHAR (30), new_password VARCHAR (30), new_name VARCHAR (30), new_last_name VARCHAR (30), new_email VARCHAR (30), new_type VARCHAR (20))
BEGIN
	INSERT INTO Users(id_user,username,password,name,last_name,email,user_type)
        VALUES(new_username,new_password,new_name,new_last_name,new_email,new_type);
END //

DELIMITER //
CREATE PROCEDURE up_rates(new_price FLOAT,new_type VARCHAR (30))
BEGIN
	INSERT INTO Rates(price,type)
        VALUES(new_price,new_type);
END //

DELIMITER //
CREATE PROCEDURE up_address(new_street VARCHAR (30), new_number INT (11), new_id_rates INT (11), new_id_meter INT (11), new_id_users INT (11))
BEGIN
	INSERT INTO Address(street,number,Rates_id_rate,Meters_id_meter,Users_id_user)
        VALUES(new_street,new_number,new_id_rates,new_id_meter),new_id_users;
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
CREATE PROCEDURE up_meters(new_serial_numbers VARCHAR (30), new_password VARCHAR (30), new_id_model INT (11))
BEGIN
	INSERT INTO meters(serial_number,password,Models_id_model)
        VALUES(new_serial_numbers,new_password,new_id_model);
END //

DELIMITER //
CREATE PROCEDURE up_measurements(new_value FLOAT, new_measurement_start FLOAT, new_measurement_end FLOAT, new_date_start DATETIME, new_date_end DATETIME, new_invoiced TINYINT (1), new_id_address INT (11), new_id_bill INT (11))
BEGIN
	INSERT INTO Measurements(value,measurement_start,masurement_end,date_start,date_end,invoiced,Address_id_address,Bills_id_bill)
        VALUES(new_value,new_measurement_start,new_measurement_end,new_date_start,new_date_end,new_invoiced,new_id_address,new_id_bill);
END //

/*DELIMITER //
CREATE PROCEDURE up_bills(new_date DATE, new_paid TINYINT (1))
BEGIN
        DECLARE new_total FLOAT;
        if B.id_bill is NOT NULL 
        BEGIN 
                SET new_total = (SELECT price FROM Rates R
                                INNER JOIN Measurements M 
                                ON M.Address_id_address = A.id_address
                                INNER JOIN Address A 
                                ON A.Rates_id_rate = R.id_rate
                                INNER JOIN Bills B
                                WHERE M.Bills_id_bill = B.id_bill );
        END IF;

        INSERT INTO Bills(date,new_total,paid)
        VALUES (new_date,new_total,new_paid)
END //*/

-----------------------------------------------------------------------

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
        DELETE FROM Meters WHERE id_meter = new_id_meter;
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
CREATE PROCEDURE update_users(new_id_user INT (11), new_username VARCHAR (30), new_password VARCHAR (30), new_name VARCHAR (30), new_last_name VARCHAR (30), new_email VARCHAR (30), new_type VARCHAR (20))
BEGIN 
        UPDATE Users SET username = new_username,password = new_password,name = new_name,last_name = new_last_name, email = new_email,user_type = new_type WHERE id_user = new_id_user;
END //

DELIMITER //
CREATE PROCEDURE update_rates(new_id_rate INT(11),new_price FLOAT)
BEGIN 
        UPDATE Rates SET price = new_price WHERE id_rate = new_id_rate;
END //

DELIMITER //
CREATE PROCEDURE update_address(new_id_address INT(11),new_street VARCHAR(30),new_number INT(11),new_id_rate INT (11), new_id_meter INT (11), new_id_user INT (11))
BEGIN 
        UPDATE Address SET street = new_street,number = new_number, Rates_id_rate = new_id_rate, Meters_id_meter = new_id_meter, Users_id_user = new_id_user WHERE id_address = new_id_address;
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
CREATE PROCEDURE update_meters(new_id_meter INT(11),new_serial_number VARCHAR(30),new_password VARCHAR(30),new_id_model INT(11))
BEGIN 
        UPDATE Meters SET serial_number = new_serial_number,password = new_password,id_model = new_id_model WHERE id_meter = new_id_meter;
END //

DELIMITER //
CREATE PROCEDURE update_measurements(new_id_measurement INT (11), new_value FLOAT, new_measurement_start FLOAT, new_measurement_end FLOAT, new_date_start DATETIME, new_date_end DATETIME, new_invoiced TINYINT (1), new_id_address INT (11), new_id_bill INT (11))
BEGIN 
        UPDATE Measurements SET value = new_value, measurement_start = new_measurement_start, masurement_end = new_measurement_end, date_start = new_date_start, date_end = new_date_end, invoiced = new_invoiced, Address_id_address = new_id_address, Bills_id_bill = new_id_bill WHERE id_measurement = new_id_measurement;
END //

/*DELIMITER //
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
END //*/

-----------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE select_users(new_id_users INT(11))
BEGIN 
        SELECT U.id_user,U.username,U.password,U.name,U.last_name,U.email,U.user_type,A.id_address,A.street,A.number,R.id_rate,R.price,R.type,M.id_meter,M.serial_number,M.password,MO.id_model,MO.name,BR.id_brand,BR.name,ME.id_measurement,ME.value,ME.measurement_start,ME.masurement_end,ME.date_start,ME.date_end,ME.invoiced,B.id_bill,B.date,B.total,B.paid
            FROM Users U 
        INNER JOIN Address A 
                ON U.id_user = A.Users_id_user 
        INNER JOIN Rates R 
                ON R.id_rate = A.Rates_id_rate 
        INNER JOIN Meters M
                ON M.id_meter = A.Meters_id_meter
        INNER JOIN Models MO
                ON MO.id_model = M.Models_id_model
        INNER JOIN Brands BR
                ON  BR.id_brand = MO.Brands_id_brand
        INNER JOIN Measurements ME
                ON ME.Address_id_address = A.id_address
        INNER JOIN Bills B
                ON B.id_bill = ME.Bills_id_bill
            WHERE id_user = new_id_users; 
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