-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema TP_FINAL
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TP_FINAL
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TP_FINAL` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema TP_FINAL2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TP_FINAL2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TP_FINAL2` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema UDEE
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema UDEE
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `UDEE` DEFAULT CHARACTER SET utf8 ;
USE `TP_FINAL` ;

-- -----------------------------------------------------
-- Table `TP_FINAL`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`clientes` (
  `dni` INT(8) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`domicilios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`domicilios` (
  `id_domicilio` INT(10) NOT NULL AUTO_INCREMENT,
  `direccion` VARCHAR(100) NULL DEFAULT NULL,
  `localidad` VARCHAR(100) NULL DEFAULT NULL,
  `codigo_postal` INT(8) NULL DEFAULT NULL,
  PRIMARY KEY (`id_domicilio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`tarifas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`tarifas` (
  `id_tarifa` INT(10) NOT NULL AUTO_INCREMENT,
  `importe` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id_tarifa`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`marcas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`marcas` (
  `id_marca` INT(10) NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_marca`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`modelos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`modelos` (
  `id_modelo` INT(10) NOT NULL AUTO_INCREMENT,
  `id_marca` INT(10) NULL DEFAULT NULL,
  `numero_seria` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_modelo`),
  INDEX `modelos_ibfk_1` (`id_marca` ASC) VISIBLE,
  CONSTRAINT `modelos_ibfk_1`
    FOREIGN KEY (`id_marca`)
    REFERENCES `TP_FINAL`.`marcas` (`id_marca`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`medidores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`medidores` (
  `id_medidor` INT(10) NOT NULL AUTO_INCREMENT,
  `id_modelo` INT(10) NOT NULL,
  PRIMARY KEY (`id_medidor`),
  INDEX `id_modelo` (`id_modelo` ASC) VISIBLE,
  CONSTRAINT `id_modelo`
    FOREIGN KEY (`id_modelo`)
    REFERENCES `TP_FINAL`.`modelos` (`id_modelo`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`mediciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`mediciones` (
  `id_medicion` INT(10) NOT NULL AUTO_INCREMENT,
  `id_medidor` INT(10) NOT NULL,
  `fecha_medicion` DATETIME NOT NULL,
  `medicion_kwh` INT(20) NOT NULL,
  `facturada` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`id_medicion`),
  INDEX `mediciones_ibfk_1` (`id_medidor` ASC) VISIBLE,
  CONSTRAINT `mediciones_ibfk_1`
    FOREIGN KEY (`id_medidor`)
    REFERENCES `TP_FINAL`.`medidores` (`id_medidor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`usuarios` (
  `id_usuario` INT(8) NOT NULL AUTO_INCREMENT,
  `dni` INT(8) NOT NULL,
  `id_medidor` INT(10) NOT NULL,
  `mail` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  INDEX `usuarios_ibfk_1` (`dni` ASC) VISIBLE,
  INDEX `usuarios_ibfk_2` (`id_medidor` ASC) VISIBLE,
  CONSTRAINT `usuarios_ibfk_1`
    FOREIGN KEY (`dni`)
    REFERENCES `TP_FINAL`.`clientes` (`dni`),
  CONSTRAINT `usuarios_ibfk_2`
    FOREIGN KEY (`id_medidor`)
    REFERENCES `TP_FINAL`.`medidores` (`id_medidor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`facturas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`facturas` (
  `id_factura` INT(10) NOT NULL AUTO_INCREMENT,
  `id_tarifa` INT(10) NOT NULL,
  `id_medicion` INT(10) NOT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `consumo_precio` DOUBLE NULL DEFAULT NULL,
  `total` DOUBLE NULL DEFAULT NULL,
  `id_usuario` INT(10) NOT NULL,
  PRIMARY KEY (`id_factura`),
  INDEX `facturas_ibfk_1` (`id_tarifa` ASC) VISIBLE,
  INDEX `facturas_ibfk_2` (`id_medicion` ASC) VISIBLE,
  INDEX `facturas_ibfk_3_idx` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `facturas_ibfk_1`
    FOREIGN KEY (`id_tarifa`)
    REFERENCES `TP_FINAL`.`tarifas` (`id_tarifa`),
  CONSTRAINT `facturas_ibfk_2`
    FOREIGN KEY (`id_medicion`)
    REFERENCES `TP_FINAL`.`mediciones` (`id_medicion`),
  CONSTRAINT `facturas_ibfk_3`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `TP_FINAL`.`usuarios` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`medidores_has_domicilios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`medidores_has_domicilios` (
  `medidores_id_medidor` INT(10) NOT NULL,
  `domicilios_id_domicilio` INT(10) NOT NULL,
  `fecha_alta` DATETIME NOT NULL,
  `fecha_baja` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`medidores_id_medidor`, `domicilios_id_domicilio`),
  INDEX `fk_medidores_has_domicilios_domicilios1` (`domicilios_id_domicilio` ASC) VISIBLE,
  CONSTRAINT `fk_medidores_has_domicilios_domicilios1`
    FOREIGN KEY (`domicilios_id_domicilio`)
    REFERENCES `TP_FINAL`.`domicilios` (`id_domicilio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medidores_has_domicilios_medidores1`
    FOREIGN KEY (`medidores_id_medidor`)
    REFERENCES `TP_FINAL`.`medidores` (`id_medidor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL`.`Employs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL`.`Employs` (
  `id_employ` INT(11) NOT NULL,
  `name` VARCHAR(30) NULL,
  `last_name` VARCHAR(30) NULL,
  `email` VARCHAR(40) NULL,
  PRIMARY KEY (`id_employ`),
  UNIQUE INDEX `id_employ_UNIQUE` (`id_employ` ASC) VISIBLE)
ENGINE = InnoDB;

USE `TP_FINAL2` ;

-- -----------------------------------------------------
-- Table `TP_FINAL2`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`clientes` (
  `dni` INT(8) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`tarifas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`tarifas` (
  `id_tarifa` INT(10) NOT NULL AUTO_INCREMENT,
  `importe` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id_tarifa`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`domicilios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`domicilios` (
  `id_domicilio` INT(10) NOT NULL AUTO_INCREMENT,
  `direccion` VARCHAR(100) NULL DEFAULT NULL,
  `localidad` VARCHAR(100) NULL DEFAULT NULL,
  `codigo_postal` INT(8) NULL DEFAULT NULL,
  `tarifas_id_tarifa` INT(10) NOT NULL,
  PRIMARY KEY (`id_domicilio`),
  INDEX `fk_domicilios_tarifas1_idx` (`tarifas_id_tarifa` ASC) VISIBLE,
  CONSTRAINT `fk_domicilios_tarifas1`
    FOREIGN KEY (`tarifas_id_tarifa`)
    REFERENCES `TP_FINAL2`.`tarifas` (`id_tarifa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`empleados` (
  `legajo` INT(11) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`legajo`),
  UNIQUE INDEX `legajo_UNIQUE` (`legajo` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`marcas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`marcas` (
  `id_marca` INT(10) NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_marca`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`medidores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`medidores` (
  `id_medidor` INT(10) NOT NULL AUTO_INCREMENT,
  `id_modelo` INT(10) NOT NULL,
  `marcas_id_marca` INT(10) NOT NULL,
  `marcas_id_marca1` INT(10) NOT NULL,
  `domicilios_id_domicilio` INT(10) NOT NULL,
  PRIMARY KEY (`id_medidor`),
  INDEX `fk_medidores_marcas1_idx` (`marcas_id_marca` ASC) VISIBLE,
  INDEX `fk_medidores_marcas2_idx` (`marcas_id_marca1` ASC) VISIBLE,
  INDEX `fk_medidores_domicilios1_idx` (`domicilios_id_domicilio` ASC) VISIBLE,
  CONSTRAINT `fk_medidores_marcas1`
    FOREIGN KEY (`marcas_id_marca`)
    REFERENCES `TP_FINAL`.`marcas` (`id_marca`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medidores_marcas2`
    FOREIGN KEY (`marcas_id_marca1`)
    REFERENCES `TP_FINAL2`.`marcas` (`id_marca`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medidores_domicilios1`
    FOREIGN KEY (`domicilios_id_domicilio`)
    REFERENCES `TP_FINAL2`.`domicilios` (`id_domicilio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`mediciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`mediciones` (
  `id_medicion` INT(10) NOT NULL AUTO_INCREMENT,
  `id_medidor` INT(10) NOT NULL,
  `fecha_medicion` DATETIME NOT NULL,
  `medicion_kwh` INT(20) NOT NULL,
  `facturada` TINYINT(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_medicion`),
  INDEX `mediciones_ibfk_1` (`id_medidor` ASC) VISIBLE,
  CONSTRAINT `mediciones_ibfk_1`
    FOREIGN KEY (`id_medidor`)
    REFERENCES `TP_FINAL2`.`medidores` (`id_medidor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`usuarios` (
  `id_usuario` INT(8) NOT NULL AUTO_INCREMENT,
  `dni` INT(8) NULL DEFAULT NULL,
  `mail` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `empleados_legajo` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `empleados_legajo_UNIQUE` (`empleados_legajo` ASC) VISIBLE,
  INDEX `usuarios_ibfk_1` (`dni` ASC) VISIBLE,
  INDEX `fk_usuarios_empleados1_idx` (`empleados_legajo` ASC) VISIBLE,
  CONSTRAINT `fk_usuarios_empleados1`
    FOREIGN KEY (`empleados_legajo`)
    REFERENCES `TP_FINAL2`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `usuarios_ibfk_1`
    FOREIGN KEY (`dni`)
    REFERENCES `TP_FINAL2`.`clientes` (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `TP_FINAL2`.`facturas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TP_FINAL2`.`facturas` (
  `id_factura` INT(10) NOT NULL AUTO_INCREMENT,
  `id_medicion` INT(10) NOT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `consumo_precio` DOUBLE NULL DEFAULT NULL,
  `total` DOUBLE NULL DEFAULT NULL,
  `id_usuario` INT(10) NOT NULL,
  PRIMARY KEY (`id_factura`),
  UNIQUE INDEX `id_usuario_UNIQUE` (`id_usuario` ASC) VISIBLE,
  UNIQUE INDEX `id_factura_UNIQUE` (`id_factura` ASC) VISIBLE,
  UNIQUE INDEX `id_medicion_UNIQUE` (`id_medicion` ASC) VISIBLE,
  INDEX `facturas_ibfk_2` (`id_medicion` ASC) VISIBLE,
  INDEX `facturas_ibfk_3_idx` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `facturas_ibfk_2`
    FOREIGN KEY (`id_medicion`)
    REFERENCES `TP_FINAL2`.`mediciones` (`id_medicion`),
  CONSTRAINT `facturas_ibfk_3`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `TP_FINAL2`.`usuarios` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `UDEE` ;

-- -----------------------------------------------------
-- Table `UDEE`.`Rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Rates` (
  `id_rate` INT(11) NOT NULL AUTO_INCREMENT,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`id_rate`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Address` (
  `id_address` INT(11) NOT NULL AUTO_INCREMENT,
  `id_rate` INT(11) NOT NULL,
  `street` VARCHAR(30) NOT NULL,
  `number` INT(11) NOT NULL,
  PRIMARY KEY (`id_address`),
  INDEX `FK-Rate` (`id_rate` ASC) VISIBLE,
  CONSTRAINT `FK-Rate`
    FOREIGN KEY (`id_rate`)
    REFERENCES `UDEE`.`Rates` (`id_rate`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Clients` (
  `id_client` INT(11) NOT NULL AUTO_INCREMENT,
  `id_address` INT(11) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id_client`),
  INDEX `FK-IdAddress` (`id_address` ASC) VISIBLE,
  CONSTRAINT `FK-IdAddress`
    FOREIGN KEY (`id_address`)
    REFERENCES `UDEE`.`Address` (`id_address`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Users` (
  `id_user` INT(11) NOT NULL AUTO_INCREMENT,
  `id_client` INT(11) NOT NULL,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_user`),
  INDEX `FK-IdClient` (`id_client` ASC) VISIBLE,
  CONSTRAINT `FK-IdClient`
    FOREIGN KEY (`id_client`)
    REFERENCES `UDEE`.`Clients` (`id_client`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Bills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Bills` (
  `id_bill` INT(11) NOT NULL DEFAULT '1',
  `id_user` INT(11) NOT NULL,
  `id_address` INT(11) NOT NULL,
  `id_measurement` INT(11) NOT NULL,
  `measure_start` INT(11) NOT NULL,
  `measure_end` INT(11) NOT NULL,
  `consumption_total` DOUBLE NOT NULL,
  `date_time_start` DATETIME NOT NULL,
  `date_time_End` DATETIME NOT NULL,
  `id_rate` INT(11) NOT NULL,
  `total` DOUBLE NOT NULL,
  PRIMARY KEY (`id_bill`),
  UNIQUE INDEX `id_bill_UNIQUE` (`id_bill` ASC) VISIBLE,
  INDEX `FK-IdUser` (`id_user` ASC) VISIBLE,
  INDEX `FK-IdAddres` (`id_address` ASC) VISIBLE,
  INDEX `FK-IdRate` (`id_rate` ASC) VISIBLE,
  CONSTRAINT `FK-IdAddres`
    FOREIGN KEY (`id_address`)
    REFERENCES `UDEE`.`Address` (`id_address`),
  CONSTRAINT `FK-IdRate`
    FOREIGN KEY (`id_rate`)
    REFERENCES `UDEE`.`Rates` (`id_rate`),
  CONSTRAINT `FK-IdUser`
    FOREIGN KEY (`id_user`)
    REFERENCES `UDEE`.`Users` (`id_user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Brands` (
  `id_brand` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_brand`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Models`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Models` (
  `id_model` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_model`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`meters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`meters` (
  `id_meter` INT(11) NOT NULL AUTO_INCREMENT,
  `id_address` INT(11) NOT NULL,
  `serial_number` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `id_brand` INT(11) NOT NULL,
  `id_model` INT(11) NOT NULL,
  PRIMARY KEY (`id_meter`),
  INDEX `FK-Brand` (`id_brand` ASC) VISIBLE,
  INDEX `FK-Address` (`id_address` ASC) VISIBLE,
  INDEX `FK-Model` (`id_model` ASC) VISIBLE,
  CONSTRAINT `FK-Address`
    FOREIGN KEY (`id_address`)
    REFERENCES `UDEE`.`Address` (`id_address`),
  CONSTRAINT `FK-Brand`
    FOREIGN KEY (`id_brand`)
    REFERENCES `UDEE`.`Brands` (`id_brand`),
  CONSTRAINT `FK-Model`
    FOREIGN KEY (`id_model`)
    REFERENCES `UDEE`.`Models` (`id_model`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `UDEE`.`Measurements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UDEE`.`Measurements` (
  `id_measurement` INT(11) NOT NULL AUTO_INCREMENT,
  `id_bill` INT(11) NULL DEFAULT NULL,
  `id_meter` INT(11) NOT NULL,
  `measurement` DOUBLE NOT NULL,
  `date` DATE NOT NULL,
  `invoiced` INT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_measurement`),
  INDEX `FK-Meter` (`id_meter` ASC) VISIBLE,
  INDEX `FK-Bill` (`id_bill` ASC) VISIBLE,
  CONSTRAINT `FK-Bill`
    FOREIGN KEY (`id_bill`)
    REFERENCES `UDEE`.`bills` (`id_bill`),
  CONSTRAINT `FK-Meter`
    FOREIGN KEY (`id_meter`)
    REFERENCES `UDEE`.`meters` (`id_meter`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;

USE `TP_FINAL` ;

-- -----------------------------------------------------
-- procedure ALTA_CLIENTE_USUARIO
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ALTA_CLIENTE_USUARIO`(IN DNI_new INT(8),IN NOMBRE_new VARCHAR(50),IN APELLIDO_new VARCHAR(50),IN MAIL_NEW VARCHAR(100), IN PASS_NEW VARCHAR(100), IN ID_MEDIDOR_NEW INT(10), IN ID_FACTURA_NEW INT(10))
BEGIN
		INSERT INTO clientes(dni,nombre, apellido)
        VALUES(DNI_new,NOMBRE_new,APELLIDO_new);
        
        INSERT INTO usuarios(id_medidor,id_factura,mail,password)
        VALUES(DNI_new,ID_MEDIDOR_NEW,ID_FACTURA_NEW,MAIL_NEW,PASS_NEW);
        
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_address`()
BEGIN
		SELECT id_domicilio,direccion,localidad,codigo_postal FROM domicilios;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_bills`()
BEGIN
		SELECT id_factura,id_tarifa,id_medicion,fecha,consumo_precio,total FROM facturas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_client`()
BEGIN
		SELECT dni,nombre,apellido FROM clientes;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_marca`()
BEGIN
		SELECT id_marca,marca FROM marcas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_measurements`()
BEGIN
		SELECT id_mediciones,id_medidor,fecha_medicion,medicion_kwh,facturada FROM mediciones;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_measurer`()
BEGIN
		SELECT id_medidor,id_modelo FROM medidores;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_measurer_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_measurer_address`()
BEGIN
		SELECT medidores_id_medidor,domicilios_id_domicilio,fecha_alta,fecha_baja FROM medidores_has_domicilios;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_model
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_model`()
BEGIN
		SELECT id_modelo,id_marca,numero_seria FROM modelos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_rates`()
BEGIN
		SELECT id_tarifa,importe FROM tarifas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_users`()
BEGIN
		SELECT id_usuario,dni,id_medidor,id_factura,mail,password FROM usuarios;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_address`(in ID_new int(10))
BEGIN
		DELETE FROM domicilios WHERE id_domicilio = ID_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_bills`(IN ID_FACTURA_NEW INT(10))
BEGIN
		DELETE FROM facturas WHERE id_factura = ID_FACTURA_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_client`(in DNI_new int(8))
BEGIN
		DELETE FROM clientes WHERE dni = DNI_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_marca`(IN marca_new VARCHAR(100))
BEGIN
	
    DELETE FROM marcas WHERE marca = marca_new;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurements`(IN ID_MEDICION_NEW INT(10))
BEGIN
		DELETE FROM mediciones WHERE id_mediciones = ID_MEDICION_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurer`(IN medidor_id_new INT(10))
BEGIN
		DELETE FROM medidores WHERE id_medidor = medidor_id_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurer_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurer_address`(IN MEDIDORES_ID_MEDIDOR_NEW INT(10),IN DOMICILIOS_ID_DOMICILIO_NEW INT(10))
BEGIN
		DELETE FROM medidores_has_domicilios WHERE medidores_id_medidor=MEDIDORES_ID_MEDIDOR_NEW AND domicilios_id_domicilio=DOMICILIOS_ID_DOMICILIO_NEW ;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_modelo`(IN modelo_new VARCHAR(100))
BEGIN
	DELETE FROM modelos WHERE numero_seria = modelo_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_rates`(in importe_new DOUBLE)
BEGIN
		DECLARE new_id INT(10);
        SET new_id = (SELECT id_tarifa FROM tarifas WHERE importe = importe_new);
		DELETE FROM tarifas WHERE id_tarifa = new_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_users`(IN DNI_new INT(8), IN MAIL_NEW VARCHAR(100))
BEGIN
		DELETE FROM usuarios WHERE dni = DNI_new AND mail = MAIL_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_address`(STREET_new varchar(100), CITY_new varchar(100), CP int(8))
BEGIN
		INSERT INTO domicilios(direccion,localidad,codigo_postal)
        VALUES (STREET_new,CITY_new,CP);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_bills`(IN ID_TARIFA_NEW INT(10),IN ID_MEDICION_NEW INT(10),IN FECHA_NEW DATE,IN CONSUMO_NEW DOUBLE,IN TOTAL_NEW DOUBLE)
BEGIN
		INSERT INTO facturas(id_tarifa,id_medicion,fecha,consumo_precio,total)
        values(ID_TARIFA_NEW,ID_MEDICION_NEW,FECHA_NEW,CONSUMO_NEW,TOTAL_NEW);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_client`(in DNI_new int(8), in NOMBRE_new varchar(50), in APELLIDO_new varchar(50))
BEGIN
		INSERT INTO clientes(dni,nombre, apellido)
        values(DNI,NOMBRE,APELLIDO);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_marca`(IN marca_new VARCHAR(100))
BEGIN
	
    INSERT INTO marcas (marca)
    VALUES(marca_new);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurements`(IN ID_MEDIDOR_NEW INT(10),IN FECHA_NEW DATE,IN MEDICION_NEW INT(20),IN FACTURADA_NEW TINYINT(4))
BEGIN
		INSERT INTO mediciones(id_medidor,fecha_medicion,medicion_kwh,facturada)
        values(ID_MEDIDOR_NEW,FECHA_NEW,MEDICION_NEW,FACTURADA_NEW);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurer`(IN modelo_id_new INT(10))
BEGIN
		INSERT INTO medidores(id_modelo)
        values(modelo_id_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurer_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurer_address`(IN MEDIDORES_ID_MEDIDOR_NEW INT(10),IN DOMICILIOS_ID_DOMICILIO_NEW INT(10),IN FECHA_ALTA_NEW DATE, IN FECHA_BAJA_NEW DATE)
BEGIN
		INSERT INTO medidores_has_domicilios(medidores_id_medidor,domicilios_id_domicilio,fecha_alta,fecha_baja)
        values(MEDIDORES_ID_MEDIDOR_NEW,DOMICILIOS_ID_DOMICILIO_NEW,FECHA_ALTA_NEW,FECHA_BAJA_NEW);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_modelo`(IN modelo_new VARCHAR(100), IN marca_new VARCHAR(100))
BEGIN
	DECLARE marca_id INT(10);
    SET marca_id = (SELECT id_marca FROM marcas WHERE marca = marca_new);
    IF (marca_id is TRUE)
    THEN
		INSERT INTO modelos (id_marca, numero_seria)
		VALUES(marca_id,modelo_new);
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_rates`(in importe_new DOUBLE)
BEGIN
		INSERT INTO tarifas(importe)
        values(importe_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_users`(IN DNI_new INT(8),IN MAIL_new varchar(100),IN PASS_new VARCHAR(100),IN ID_MEDIDOR_NEW INT(10),IN ID_FACTURA_NEW INT(10))
BEGIN
		INSERT INTO usuarios(dni,id_medidor,id_factura,mail,password)
        values(DNI_new,ID_MEDIDOR_NEW,ID_FACTURA_NEW,MAIL_new,PASS_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_address`(in ID_new int(8), in STREET_new varchar(50), in CITY_new varchar(50), CP int(8))
BEGIN
		UPDATE domicilios SET direccion = STREET_new, localidad = CITY_new, codigo_postal = CP WHERE id_domicilio = ID_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_bills`(IN ID_FACTURA_NEW INT(10),IN ID_TARIFA_NEW INT(10),IN ID_MEDICION_NEW INT(10),IN FECHA_NEW DATE,IN CONSUMO_NEW DOUBLE,IN TOTAL_NEW DOUBLE)
BEGIN
		UPDATE faturas SET id_tarifa = ID_TARIFA_NEW,id_medicion = ID_MEDICION_NEW,fecha = FECHA_NEW,consumo_precio = CONSUMO_NEW,total = TOTAL_NEW WHERE id_factura = ID_FACTURA_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_client`(in DNI_new int(8), in NOMBRE_new varchar(50), in APELLIDO_new varchar(50))
BEGIN
		UPDATE clientes SET nombre = NOMBRE_new, apellido = APELLIDO_new WHERE dni = DNI_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_marca`(IN marca_new VARCHAR(100), in marca_old VARCHAR(100))
BEGIN
	
    UPDATE marcas SET marca = marca_new WHERE marca = marca_old;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_measurements`(IN ID_MEDICIONES_NEW INT(10),IN ID_MEDIDOR_NEW INT(10),IN FECHA_NEW DATE,IN MEDICION_NEW INT(20),IN FACTURADA_NEW TINYINT(4))
BEGIN
		UPDATE mediciones SET id_medidor = ID_MEDIDOR_NEW,fecha_medicion = FECHA_NEW,medicion_kwh = MEDICION_NEW,facturada = FACTURADA_NEW WHERE id_mediciones = ID_MEDICIONES_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_measurer`(IN medidor_id_new INT(10), IN modelo_id_new INT(10))
BEGIN
		UPDATE medidores SET id_modelo = modelo_id_new WHERE id_medidor = medidor_id_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_measurer_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_measurer_address`(IN MEDIDORES_ID_MEDIDOR_NEW INT(10),IN DOMICILIOS_ID_DOMICILIO_NEW INT(10),IN FECHA_ALTA_NEW DATE, IN FECHA_BAJA_NEW DATE)
BEGIN
		UPDATE medidores_has_domicilios SET fecha_alta = FECHA_ALTA_NEW, fecha_baja = FECHA_BAJA_NEW WHERE medidores_id_medidor=MEDIDORES_ID_MEDIDOR_NEW AND domicilios_id_domicilio=DOMICILIOS_ID_DOMICILIO_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_modelo`(IN modelo_new VARCHAR(100), in modelo_old VARCHAR(100))
BEGIN
	
    UPDATE modelos SET numero_seria = modelo_new WHERE numero_seria = modelo_old;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_rates`(in importe_new DOUBLE, in importe_old DOUBLE)
BEGIN
		UPDATE tarifas SET importe = importe_new WHERE importe = importe_old;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_users`(IN DNI_new INT(8),IN MAIL_new varchar(100),IN PASS_new VARCHAR(100),IN ID_MEDIDOR_NEW INT(10),IN ID_FACTURA_NEW INT(10))
BEGIN
		UPDATE usuarios SET dni=DNI_NEW,id_medidor=ID_MEDIDOR_NEW,id_factura=ID_FACTURA_NEW,mail=MAIL_NEW,password=PASS_NEW WHERE dni = DNI_new;
END$$

DELIMITER ;
USE `TP_FINAL2` ;

-- -----------------------------------------------------
-- procedure ALTA_CLIENTE_USUARIO
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ALTA_CLIENTE_USUARIO`(IN DNI_new INT(8),IN NOMBRE_new VARCHAR(50),IN APELLIDO_new VARCHAR(50),IN MAIL_NEW VARCHAR(100), IN PASS_NEW VARCHAR(100), IN ID_MEDIDOR_NEW INT(10), IN ID_FACTURA_NEW INT(10))
BEGIN
		INSERT INTO clientes(dni,nombre, apellido)
        VALUES(DNI_new,NOMBRE_new,APELLIDO_new);
        
        INSERT INTO usuarios(id_medidor,id_factura,mail,password)
        VALUES(DNI_new,ID_MEDIDOR_NEW,ID_FACTURA_NEW,MAIL_NEW,PASS_NEW);
        
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_address`()
BEGIN
		SELECT id_domicilio,direccion,localidad,codigo_postal FROM domicilios;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_bills`()
BEGIN
		SELECT id_factura,id_tarifa,id_medicion,fecha,consumo_precio,total FROM facturas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_client`()
BEGIN
		SELECT dni,nombre,apellido FROM clientes;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_marca`()
BEGIN
		SELECT id_marca,marca FROM marcas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_measurements`()
BEGIN
		SELECT id_mediciones,id_medidor,fecha_medicion,medicion_kwh,facturada FROM mediciones;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_measurer`()
BEGIN
		SELECT id_medidor,id_modelo FROM medidores;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_model
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_model`()
BEGIN
		SELECT id_modelo,id_marca,numero_seria FROM modelos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_rates`()
BEGIN
		SELECT id_tarifa,importe FROM tarifas;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure all_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_users`()
BEGIN
		SELECT id_usuario,dni,id_medidor,id_factura,mail,password FROM usuarios;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_address`(in ID_new int(10))
BEGIN
		DELETE FROM domicilios WHERE id_domicilio = ID_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_bills`(IN ID_FACTURA_NEW INT(10))
BEGIN
		DELETE FROM facturas WHERE id_factura = ID_FACTURA_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_client`(in DNI_new int(8))
BEGIN
		DELETE FROM clientes WHERE dni = DNI_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_marca`(IN marca_new VARCHAR(100))
BEGIN
	
    DELETE FROM marcas WHERE marca = marca_new;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurements`(IN ID_MEDICION_NEW INT(10))
BEGIN
		DELETE FROM mediciones WHERE id_mediciones = ID_MEDICION_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurer`(IN medidor_id_new INT(10))
BEGIN
		DELETE FROM medidores WHERE id_medidor = medidor_id_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_modelo`(IN modelo_new VARCHAR(100))
BEGIN
	DELETE FROM modelos WHERE numero_seria = modelo_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_rates`(in importe_new DOUBLE)
BEGIN
		DECLARE new_id INT(10);
        SET new_id = (SELECT id_tarifa FROM tarifas WHERE importe = importe_new);
		DELETE FROM tarifas WHERE id_tarifa = new_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_users`(IN DNI_new INT(8), IN MAIL_NEW VARCHAR(100))
BEGIN
		DELETE FROM usuarios WHERE dni = DNI_new AND mail = MAIL_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_address`(STREET_new varchar(100), CITY_new varchar(100), CP int(8))
BEGIN
		INSERT INTO domicilios(direccion,localidad,codigo_postal)
        VALUES (STREET_new,CITY_new,CP);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_bills`(IN ID_TARIFA_NEW INT(10),IN ID_MEDICION_NEW INT(10),IN FECHA_NEW DATE,IN CONSUMO_NEW DOUBLE,IN TOTAL_NEW DOUBLE)
BEGIN
		INSERT INTO facturas(id_tarifa,id_medicion,fecha,consumo_precio,total)
        values(ID_TARIFA_NEW,ID_MEDICION_NEW,FECHA_NEW,CONSUMO_NEW,TOTAL_NEW);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_client`(in DNI_new int(8), in NOMBRE_new varchar(50), in APELLIDO_new varchar(50))
BEGIN
		INSERT INTO clientes(dni,nombre, apellido)
        values(DNI,NOMBRE,APELLIDO);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_marca`(IN marca_new VARCHAR(100))
BEGIN
	
    INSERT INTO marcas (marca)
    VALUES(marca_new);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurements`(IN ID_MEDIDOR_NEW INT(10),IN FECHA_NEW DATE,IN MEDICION_NEW INT(20),IN FACTURADA_NEW TINYINT(4))
BEGIN
		INSERT INTO mediciones(id_medidor,fecha_medicion,medicion_kwh,facturada)
        values(ID_MEDIDOR_NEW,FECHA_NEW,MEDICION_NEW,FACTURADA_NEW);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurer`(IN modelo_id_new INT(10))
BEGIN
		INSERT INTO medidores(id_modelo)
        values(modelo_id_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_modelo`(IN modelo_new VARCHAR(100), IN marca_new VARCHAR(100))
BEGIN
	DECLARE marca_id INT(10);
    SET marca_id = (SELECT id_marca FROM marcas WHERE marca = marca_new);
    IF (marca_id is TRUE)
    THEN
		INSERT INTO modelos (id_marca, numero_seria)
		VALUES(marca_id,modelo_new);
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_rates`(in importe_new DOUBLE)
BEGIN
		INSERT INTO tarifas(importe)
        values(importe_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_users`(IN DNI_new INT(8),IN MAIL_new varchar(100),IN PASS_new VARCHAR(100),IN ID_MEDIDOR_NEW INT(10),IN ID_FACTURA_NEW INT(10))
BEGIN
		INSERT INTO usuarios(dni,id_medidor,id_factura,mail,password)
        values(DNI_new,ID_MEDIDOR_NEW,ID_FACTURA_NEW,MAIL_new,PASS_new);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_address
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_address`(in ID_new int(8), in STREET_new varchar(50), in CITY_new varchar(50), CP int(8))
BEGIN
		UPDATE domicilios SET direccion = STREET_new, localidad = CITY_new, codigo_postal = CP WHERE id_domicilio = ID_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_bills
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_bills`(IN ID_FACTURA_NEW INT(10),IN ID_TARIFA_NEW INT(10),IN ID_MEDICION_NEW INT(10),IN FECHA_NEW DATE,IN CONSUMO_NEW DOUBLE,IN TOTAL_NEW DOUBLE)
BEGIN
		UPDATE faturas SET id_tarifa = ID_TARIFA_NEW,id_medicion = ID_MEDICION_NEW,fecha = FECHA_NEW,consumo_precio = CONSUMO_NEW,total = TOTAL_NEW WHERE id_factura = ID_FACTURA_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_client
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_client`(in DNI_new int(8), in NOMBRE_new varchar(50), in APELLIDO_new varchar(50))
BEGIN
		UPDATE clientes SET nombre = NOMBRE_new, apellido = APELLIDO_new WHERE dni = DNI_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_marca
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_marca`(IN marca_new VARCHAR(100), in marca_old VARCHAR(100))
BEGIN
	
    UPDATE marcas SET marca = marca_new WHERE marca = marca_old;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_measurements`(IN ID_MEDICIONES_NEW INT(10),IN ID_MEDIDOR_NEW INT(10),IN FECHA_NEW DATE,IN MEDICION_NEW INT(20),IN FACTURADA_NEW TINYINT(4))
BEGIN
		UPDATE mediciones SET id_medidor = ID_MEDIDOR_NEW,fecha_medicion = FECHA_NEW,medicion_kwh = MEDICION_NEW,facturada = FACTURADA_NEW WHERE id_mediciones = ID_MEDICIONES_NEW;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_measurer
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_measurer`(IN medidor_id_new INT(10), IN modelo_id_new INT(10))
BEGIN
		UPDATE medidores SET id_modelo = modelo_id_new WHERE id_medidor = medidor_id_new;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_modelo
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_modelo`(IN modelo_new VARCHAR(100), in modelo_old VARCHAR(100))
BEGIN
	
    UPDATE modelos SET numero_seria = modelo_new WHERE numero_seria = modelo_old;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_rates
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_rates`(in importe_new DOUBLE, in importe_old DOUBLE)
BEGIN
		UPDATE tarifas SET importe = importe_new WHERE importe = importe_old;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updat_users
-- -----------------------------------------------------

DELIMITER $$
USE `TP_FINAL2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updat_users`(IN DNI_new INT(8),IN MAIL_new varchar(100),IN PASS_new VARCHAR(100),IN ID_MEDIDOR_NEW INT(10),IN ID_FACTURA_NEW INT(10))
BEGIN
		UPDATE usuarios SET dni=DNI_NEW,id_medidor=ID_MEDIDOR_NEW,id_factura=ID_FACTURA_NEW,mail=MAIL_NEW,password=PASS_NEW WHERE dni = DNI_new;
END$$

DELIMITER ;
USE `UDEE` ;

-- -----------------------------------------------------
-- procedure cal_total
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cal_total`(IN new_id_bill INT (11))
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
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_address
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_address`(new_id_address INT(11))
BEGIN 
        DELETE FROM Address WHERE id_address = new_id_address;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_bills
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_bills`(new_id_bill INT(11))
BEGIN 
        DELETE FROM Bills WHERE id_bill = new_id_bill;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_brands
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_brands`(new_id_brand INT(11))
BEGIN 
        DELETE FROM Brands WHERE id_brand = new_id_brand;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_clients
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_clients`(new_id_client INT(11))
BEGIN 
        DELETE FROM Clients WHERE id_address = new_id_client;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_measurements`(new_id_measurement INT(11))
BEGIN 
        DELETE FROM Measurements WHERE id_measurement = new_id_measurement;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_meters
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_meters`(new_id_meter INT(11))
BEGIN 
        DELETE FROM meters WHERE id_meter = new_id_meter;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_models
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_models`(new_id_model INT(11))
BEGIN 
        DELETE FROM Models WHERE id_model = new_id_model;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_rates
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_rates`(new_id_rate INT(11))
BEGIN 
        DELETE FROM Rates WHERE id_rate = new_id_rate;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure down_users
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `down_users`(new_id_user INT(11))
BEGIN 
        DELETE FROM Users WHERE id_user = new_id_user;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_address
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_address`()
BEGIN 
        SELECT id_address,id_rate,street,number FROM Address;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_bills
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_bills`()
BEGIN 
        SELECT id_bill,id_address,id_measurement,measure_start,measure_end,consumption_total,date_time_start,date_time_End,id_rate,total FROM Bills;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_brands
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_brands`()
BEGIN 
        SELECT id_brand,name FROM Brands;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_clients
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_clients`()
BEGIN 
        SELECT id_client,id_address,name,last_name,email FROM Clients;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_measurements`()
BEGIN 
        SELECT id_measurement,id_bill,id_meter,measurement,date FROM Measurements;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_meters
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_meters`()
BEGIN 
        SELECT id_meter,id_address,serial_number,password,id_brand,id_model FROM meters;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_models
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_models`()
BEGIN 
        SELECT id_model,name FROM Models;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_rates
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_rates`()
BEGIN 
        SELECT id_rate,price FROM Rates;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_all_users
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_users`()
BEGIN 
        SELECT id_user,id_client,username,password FROM Users;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure select_clients
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_clients`(new_id_client INT(11))
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
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_address
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_address`(new_id_rate INT(11),new_street VARCHAR(30), new_number INT(11))
BEGIN
		INSERT INTO Address(id_rate,street,number)
        VALUES(new_id_rate,new_street,new_number);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_bills
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_bills`(new_id_bill INT(11), new_id_user INT(11), new_id_address INT(11), new_id_measurement INT(11), new_measure_start INT(11), new_measure_end INT(11), new_date_time_start datetime, new_date_time_end datetime, new_id_rate INT(11))
BEGIN
	DECLARE new_total DOUBLE;
        DECLARE new_consumption_total DOUBLE;

        SET new_consumption_total = (SELECT measurement FROM Measurements WHERE new_id_measurement = id_measurement);
        SET new_total = new_consumption_total * (SELECT  price 
		FROM Rates
		WHERE id_rate = new_id_rate);
        INSERT INTO Bills(id_bill,id_user,id_address,id_measurement,measure_start,measure_end,consumption_total,date_time_start,date_time_End,id_rate,total)
	VALUES(new_id_bill,new_id_user,new_id_address,new_id_measurement,new_measure_start,new_measure_end,new_consumption_total,new_date_time_start,new_date_time_end,new_id_rate,new_total);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_brands
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_brands`(new_name VARCHAR(30))
BEGIN
		INSERT INTO Brands(name)
        VALUES(new_name);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_clients
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_clients`(new_id_address INT(11),new_name VARCHAR(30), new_last_name VARCHAR(30),new_email VARCHAR(40))
BEGIN
		INSERT INTO Clients(id_address,name,last_name,email)
        VALUES(new_id_address,new_name,new_last_name,new_email);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_measurements`(new_id_bill INT(11),new_id_meter INT(11),new_measurement VARCHAR(30), new_date DATE)
BEGIN
	INSERT INTO Measurements(id_bill,id_meter,measurement,date)
        VALUES(new_id_bill,new_id_meter,new_measurement,new_date);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_meters
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_meters`(new_id_address INT(11), new_serial_numbers VARCHAR(30), new_password VARCHAR(30), new_id_brand INT(11), new_id_model INT(11))
BEGIN
		INSERT INTO meters(id_address,serial_number,password,id_brand,id_model)
        VALUES(new_id_address,new_serial_numbers,new_password,new_id_brand,new_id_model);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_models
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_models`(new_name VARCHAR(30))
BEGIN
		INSERT INTO Models(name)
        VALUES(new_name);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_rates
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_rates`(new_price FLOAT)
BEGIN
		INSERT INTO Rates(price)
        VALUES(new_price);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure up_users
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_users`(new_id_client INT(11), new_username VARCHAR(30), new_password VARCHAR(30))
BEGIN
		INSERT INTO Users(id_client, username, password)
        VALUES (new_id_client,new_username,new_password);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_address
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_address`(new_id_address INT(11),new_id_rate INT(11),new_street VARCHAR(30),new_number INT(11))
BEGIN 
        UPDATE Address SET id_rate = new_id_rate,street = new_street,number = new_number WHERE id_address = new_id_address;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_bills
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_bills`(new_id_bill INT(11), new_id_user INT(11), new_id_address INT(11), new_id_measurement INT(11), new_measure_start INT(11), new_measure_end INT(11), new_date_time_start datetime, new_date_time_end datetime, new_id_rate INT(11))
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
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_brands
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_brands`(new_id_brand INT(11),new_name VARCHAR(30))
BEGIN 
        UPDATE Brands SET name = new_name WHERE id_brand = new_id_brand;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_clients
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_clients`(new_id_client INT(11),new_id_address INT(11),new_name VARCHAR(30),new_last_name VARCHAR(30),new_email VARCHAR(40))
BEGIN 
        UPDATE Clients SET id_address = new_id_address,name = new_name,last_name = new_last_name,email = new_email WHERE id_client = new_id_client;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_measurements
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_measurements`(new_id_measurement INT(11),new_id_bill INT(11),new_id_meter INT(11), new_measurement VARCHAR(30),new_date DATE,new_invoiced INT(1))
BEGIN 
        UPDATE Measurements SET id_bill = new_id_bill,id_meter = new_id_meter,measurement = new_measurement,date = new_date, invoiced = new_invoiced WHERE id_measurement = new_id_measurement;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_meters
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_meters`(new_id_meter INT(11),new_id_address INT(11),new_serial_number VARCHAR(30),new_password VARCHAR(30),new_id_brand INT(11),new_id_model INT(11))
BEGIN 
        UPDATE meters SET id_address = new_id_address,serial_number = new_serial_number,password = new_password,id_brand = new_id_brand,id_model = new_id_model WHERE id_meter = new_id_meter;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_models
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_models`(new_id_model INT(11),new_name VARCHAR(30))
BEGIN   
        UPDATE Models SET name = new_name WHERE id_model = new_id_model;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_rates
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_rates`(new_id_rate INT(11),new_price FLOAT)
BEGIN 
        UPDATE Rates SET price = new_price WHERE id_rate = new_id_rate;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_users
-- -----------------------------------------------------

DELIMITER $$
USE `UDEE`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_users`(new_id_user INT(11),new_id_client INT(11),new_username VARCHAR(30),new_password VARCHAR(30))
BEGIN 
        UPDATE Users SET id_client = new_id_client,username = new_username,password = new_password WHERE id_user = new_id_user;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
