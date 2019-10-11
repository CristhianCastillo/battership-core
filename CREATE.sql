-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema battlefieldBest
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema battlefieldBest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `battlefieldBest` DEFAULT CHARACTER SET latin1 ;
USE `battlefieldBest` ;

-- -----------------------------------------------------
-- Table `battlefieldBest`.`field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `battlefieldBest`.`field` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `x` INT(11) NOT NULL,
  `y` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `battlefieldBest`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `battlefieldBest`.`player` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `battlefieldBest`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `battlefieldBest`.`game` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `creation` DATETIME NULL DEFAULT NULL,
  `opponent_id` BIGINT(20) NULL DEFAULT NULL,
  `player_id` BIGINT(20) NULL DEFAULT NULL,
  `start_player_id` BIGINT(20) NULL DEFAULT NULL,
  `turn_player_id` BIGINT(20) NULL DEFAULT NULL,
  `won_player_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK24ki7pws8pvypieg3y9o3i0wp`
    FOREIGN KEY (`turn_player_id`)
    REFERENCES `battlefieldBest`.`player` (`id`),
  CONSTRAINT `FK32gykjb4r2coofdkq61749728`
    FOREIGN KEY (`opponent_id`)
    REFERENCES `battlefieldBest`.`player` (`id`),
  CONSTRAINT `FK69kxn13hw2qili6x6em4ur6kd`
    FOREIGN KEY (`player_id`)
    REFERENCES `battlefieldBest`.`player` (`id`),
  CONSTRAINT `FKi1lpejep2wvxl1c8mvjylxwmt`
    FOREIGN KEY (`won_player_id`)
    REFERENCES `battlefieldBest`.`player` (`id`),
  CONSTRAINT `FKtge668d3u0bnim88f7ofse8x9`
    FOREIGN KEY (`start_player_id`)
    REFERENCES `battlefieldBest`.`player` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `FK32gykjb4r2coofdkq61749728` ON `battlefieldBest`.`game` (`opponent_id` ASC);

CREATE INDEX `FK69kxn13hw2qili6x6em4ur6kd` ON `battlefieldBest`.`game` (`player_id` ASC);

CREATE INDEX `FKtge668d3u0bnim88f7ofse8x9` ON `battlefieldBest`.`game` (`start_player_id` ASC);

CREATE INDEX `FK24ki7pws8pvypieg3y9o3i0wp` ON `battlefieldBest`.`game` (`turn_player_id` ASC);

CREATE INDEX `FKi1lpejep2wvxl1c8mvjylxwmt` ON `battlefieldBest`.`game` (`won_player_id` ASC);


-- -----------------------------------------------------
-- Table `battlefieldBest`.`battlefield`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `battlefieldBest`.`battlefield` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `impact` BIT(1) NULL DEFAULT NULL,
  `ship` BIT(1) NULL DEFAULT NULL,
  `field_id` BIGINT(20) NULL DEFAULT NULL,
  `game_id` BIGINT(20) NULL DEFAULT NULL,
  `player_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK6ceah6amb20o1d03o9o4a8xr7`
    FOREIGN KEY (`field_id`)
    REFERENCES `battlefieldBest`.`field` (`id`),
  CONSTRAINT `FKeh06kt1kqlx27rt8546ahaf0h`
    FOREIGN KEY (`player_id`)
    REFERENCES `battlefieldBest`.`player` (`id`),
  CONSTRAINT `FKqhyudtefsiob4969xiaf479u2`
    FOREIGN KEY (`game_id`)
    REFERENCES `battlefieldBest`.`game` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1401
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `FK6ceah6amb20o1d03o9o4a8xr7` ON `battlefieldBest`.`battlefield` (`field_id` ASC);

CREATE INDEX `FKqhyudtefsiob4969xiaf479u2` ON `battlefieldBest`.`battlefield` (`game_id` ASC);

CREATE INDEX `FKeh06kt1kqlx27rt8546ahaf0h` ON `battlefieldBest`.`battlefield` (`player_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
