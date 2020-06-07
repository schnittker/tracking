CREATE DATABASE tracking;

CREATE TABLE tracking.scheduler (
   id INT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
   project_name VARCHAR(255) NOT NULL,
   start_time TIMESTAMP NOT NULL,
   stop_time TIMESTAMP NOT NULL
);

CREATE USER 'trackingUser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON tracking.* TO 'trackingUser'@'localhost';