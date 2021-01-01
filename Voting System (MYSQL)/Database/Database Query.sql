DROP DATABASE IF EXISTS election;
CREATE DATABASE election;
USE election;

CREATE TABLE `admin` (
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (username)
);

CREATE TABLE `voter` (
	voter_id INT  NOT NULL AUTO_INCREMENT,	
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	address TEXT NOT NULL,
	gender VARCHAR(10) NOT NULL,
	dob DATE NOT NULL,	
	PRIMARY KEY (voter_id)
  
);

CREATE TABLE `candidate` (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	party VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE `result` (
	id INT NOT NULL AUTO_INCREMENT,
	votes INT,
	PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES candidate(id)
);

INSERT INTO `admin` (username,password) VALUES
('admin1','1'),
('admin2','2'),
('admin3','3');

INSERT INTO `candidate` (id,name,party) VALUES
(1,'Joe Biden    ','Democratic'),
(2,'Donald Trump ','Republican'),
(3,'Jo Jorgensen ','Libertarian'),
(4,'Howie Hawkins','Green'),	
(5,'Kanye West   ','Birthday'),	
(6,'Rocky Fuente ','Alliance'),
(7,'Brock Pierce ','Independent');

INSERT INTO `result` (id,votes) VALUES
(1,0),
(2,0),
(3,0),
(4,0),
(5,0),
(6,0),
(7,0);