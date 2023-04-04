-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema catalog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema catalog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `catalog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `catalog` ;

-- -----------------------------------------------------
-- Table `catalog`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalog`.`author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `biography` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `catalog`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalog`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `pub_date` DATE NOT NULL,
  `pub_house` VARCHAR(100) NOT NULL,
  `available` TINYINT NOT NULL,
  `content_description` TEXT NOT NULL,
  `isbn` CHAR(10) NOT NULL,
  `dewey_decimal_code` VARCHAR(20) NOT NULL,
  `price` DECIMAL(10,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `catalog`.`book_author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalog`.`book_author` (
  `book_id` INT NOT NULL,
  `author_id` INT NOT NULL,
  `book_author_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`book_author_id`),
  INDEX `book_fk_idx` (`book_id` ASC) VISIBLE,
  INDEX `author_fk_idx` (`author_id` ASC) VISIBLE,
  CONSTRAINT `author_fk`
    FOREIGN KEY (`author_id`)
    REFERENCES `catalog`.`author` (`id`),
  CONSTRAINT `book_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `catalog`.`book` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `catalog`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalog`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `catalog`.`book_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalog`.`book_tag` (
  `id_tag` INT NOT NULL,
  `id_book` INT NOT NULL,
  PRIMARY KEY (`id_tag`, `id_book`),
  INDEX `id_book_fk_idx` (`id_book` ASC) VISIBLE,
  CONSTRAINT `id_book_tag_fk`
    FOREIGN KEY (`id_book`)
    REFERENCES `catalog`.`book` (`id`),
  CONSTRAINT `id_tag_fk`
    FOREIGN KEY (`id_tag`)
    REFERENCES `catalog`.`tag` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
