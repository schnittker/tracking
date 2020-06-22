CREATE DATABASE tracking;

CREATE TABLE tracking.projects (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   project_name VARCHAR(255) NOT NULL,
   PRIMARY KEY (id),
   INDEX idx_id(id),
   INDEX idx_project_name(project_name)
) ENGINE=InnoDB;


CREATE TABLE tracking.scheduler (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   projects_id INT UNSIGNED NOT NULL,
   start_time TIMESTAMP NOT NULL,
   stop_time TIMESTAMP NOT NULL,
   task TEXT,
   PRIMARY KEY (id),
   CONSTRAINT fk_project_id FOREIGN KEY (projects_id) REFERENCES tracking.projects (id),
   INDEX idx_start_stop_time (start_time, stop_time)
) ENGINE=InnoDB;

CREATE USER 'trackingUser'@'s%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON tracking.* TO 'trackingUser'@'%';