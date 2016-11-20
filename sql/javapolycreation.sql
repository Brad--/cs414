CREATE SCHEMA `javapoly` ;

CREATE TABLE `javapoly`.`token` (
  `tokenId` INT NOT NULL AUTO_INCREMENT,
  `gameId` VARCHAR(45) NULL,
  `playerName` VARCHAR(45) NULL,
  `gamePiece` VARCHAR(45) NULL,
  `money` INT NULL,
  `position` INT NULL,
  `ready` TINYINT(1) NULL,
  PRIMARY KEY (`tokenId`));

ALTER TABLE `javapoly`.`token`
CHANGE COLUMN `ready` `ready` TINYINT(1) NULL DEFAULT 0 ;


CREATE TABLE `javapoly`.`deed` (
  `deedId` INT NOT NULL AUTO_INCREMENT,
  `gameId` VARCHAR(45) NULL,
  `position` INT NULL,
  `playerName` VARCHAR(45) NULL,
  `housingCount` INT NULL,
  PRIMARY KEY (`deedId`));

CREATE TABLE `javapoly`.`card` (
  `cardId` INT NOT NULL,
  `type` INT NULL,
  `cardText` VARCHAR(70) NULL,
  `cardReward` INT NULL,
  PRIMARY KEY (`cardId`));

INSERT INTO card (cardId, type, cardText, cardReward) VALUES
(0, 3, "Advance to Go", 0),
(1, 1, "Bank error in your favor collect $75", 75),
(2, 2, "Doctor's fees lose $50", 50),
(3, 3, "Go to jail", 10),
(4, 1, "It is your birthday collect $10", 10),
(5, 1, "Income Tax refund collect $20", 20),
(6, 1, "Life Insurance Matures collect $100 ", 100),
(7, 2, "Pay Hospital Fees of $100", 100),
(8, 2, "Pay School Fees of $50", 50),
(9, 1, "Receive $25 Consultancy Fee", 25),
(10, 1, "You have won second prize in a beauty contest collect $10", 10),
(11, 1, "You inherit $100", 100),
(12, 1, "From sale of stock you get $50", 50),
(13, 1, "Holiday Fund matures Receive $100", 100),
(14, 3, "Advance to Laurel Max Station", 5),
(15, 1, "Bank pays you dividend of $50", 50),
(16, 2, "Pay poor tax of $15", 15),
(17, 3, "Advance token to Starbucks", 39),
(18, 1, "Your building loan matures collect $150", 150),
(19, 2, "You've been hacked lose $10", 10),
(20, 2, "You missed a midterm lose $50", 50);

ALTER TABLE `javapoly`.`token`
ADD COLUMN `inJail` TINYINT(1) NULL DEFAULT 0 AFTER `ready`;

ALTER TABLE `javapoly`.`token`
ADD COLUMN `speedCount` INT NULL DEFAULT 0 AFTER `inJail`;

ALTER TABLE `javapoly`.`token`
ADD COLUMN `playerTurn` TINYINT(1) NULL AFTER `speedCount`;

alter table deed add column deedName varchar(45) after gameId;

alter table token add column lastRollOne int(11), add column lastRollTwo int(11);

CREATE TABLE deedBid (
  deedBidId INT NOT NULL AUTO_INCREMENT,
  gameId VARCHAR(45) NULL,
  position INT NULL,
  playerName VARCHAR(45) NULL,
  playerBid INT NULL,
  PRIMARY KEY (deedBidId));

ALTER TABLE `javapoly`.`deed`
ADD COLUMN `isMortgaged` TINYINT(1) NULL AFTER `propertyGroup`;

CREATE TABLE trade (
  tradeId int(11) NOT NULL AUTO_INCREMENT,
  gameId varchar (45) NOT NULL,
  playerOneName varchar(45) DEFAULT NULL,
  playerOneMoneyOffered int(11) DEFAULT NULL,
  playerOneAccepted tinyint(1) DEFAULT NULL,
  playerTwoName varchar(45) DEFAULT NULL,
  playerTwoMoneyOffered int(11) DEFAULT NULL,
  playerTwoAccepted tinyint(1) DEFAULT NULL,
  isFinalized tinyint(1) DEFAULT NULL,
  PRIMARY KEY (tradeId)
);

CREATE TABLE tradeDeed (
  tradeDeedId int(11) NOT NULL AUTO_INCREMENT,
  tradeId int(11) NOT NULL,
  playerName varchar(45) DEFAULT NULL,
  deedName varchar(45) DEFAULT NULL,
  PRIMARY KEY (tradeDeedId)
);
