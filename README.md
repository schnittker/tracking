# Time tracking for projects 
![screenshot](https://github.com/schnittker/tracking/blob/master/screenshots/screenshot.png?raw=true)

Tracking is a small multilingual application to track the time you are working on a project. 

If you have a question, just write to 
schnittker@neozo.de

## How to build
The first time, "docker-compose up" must be run once to create the container together with the database and tables. 
For this you have to change to the project directory and execute the following commands : 
```bash
docker-compose up
gradle clean && gradle jar
```

Now you can start the program with the following command :
```bash
java -jar ./build/libs/tracking-0.2.jar
```

## Database
You can set up the database in two ways.
Either you use mysql locally, in which case you must run the following script, or you start a docker container with docker-compose

### Use MySql local

```sql
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

CREATE USER 'trackingUser'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON tracking.* TO 'trackingUser'@'%';
```

### Build a container for MySql
The product directory contains a docker-compose.yml.
If you call it with 
```shell
docker-compose up
```
you can start your own docker container for mysql. 
The container automatically includes /resources/sql/tracking.sql, and creates the database and tables. 
Also a trackingUser is created.

The docker-compose file is located in the build folder under /libs together with the generated jar file

## Multilingual
You can add new translations to the folder :
./resources/i18n

Currently the program is completely translated into english and german. 

## Other stuff
### Frameworks and other technologies
* MySQL
* Docker
* Apache Commons
* Apache Commons Csv
* Apache 
* jFreechart
* sparkjava
