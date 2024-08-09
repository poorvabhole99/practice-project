CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `email_id` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `author_name` varchar(20) DEFAULT NULL,
  `isbn` varchar(10) NOT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `rental_price` double NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn_UNIQUE` (`isbn`)
);

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `total_price` double NOT NULL,
  `order_date` datetime NOT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_1_idx` (`userId`),
  CONSTRAINT `fk_order_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
);

CREATE TABLE `root_practice_db`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `created_by` VARCHAR(45) NULL,
  `created_date_time` DATETIME NULL,
  `modified_by` VARCHAR(45) NULL,
  `modified_date_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_1_idx` (`book_id` ASC) VISIBLE,
  INDEX `fk_order_item_2_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_1`
    FOREIGN KEY (`book_id`)
    REFERENCES `root_practice_db`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_2`
    FOREIGN KEY (`order_id`)
    REFERENCES `root_practice_db`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `root_practice_db`.`rental` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `rental_date` DATE NOT NULL,
  `return_date` DATE NOT NULL,
  `created_by` VARCHAR(45) NULL,
  `created_date_time` DATETIME NULL,
  `modified_by` VARCHAR(45) NULL,
  `modified_date_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rental_1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_rental_2_idx` (`book_id` ASC) VISIBLE,
  CONSTRAINT `fk_rental_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `root_practice_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_2`
    FOREIGN KEY (`book_id`)
    REFERENCES `root_practice_db`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

