# tracking (v 0.1)
If you have a question, just write to 
schnittker@neozo.de

## Description
Tracking is a small multi lingual console application to track the time you are working on a project. 

## How to install
```bash
gradle build jar
java -jar ./build/lib/tracking.jar
```

## Build a container for MySql
The product directory contains a docker-compose.yml.
If you call it with 
```shell
docker-compose up
```
you can start your own docker container for mysql. 
the container automatically includes /resources/sql/create.sql, and creates the database and tables. 
also a trackingUser is created.

## Commands
### start [project_name]
Starts a time tracker for the specified project. 

### stop [project_name]
Stops the time tracker for the specified project.

### active
List all active time trackers.

### export [project_name] [number_of_month]
Exports all data of the specified projects for the selected month as cdv file and calculates the total number of hours for each project.
The export file can be found in the project folder under ./export.csv

application.yml :
```yaml
csv_output_path=./export.csv
```

### export all [number_of_month]
Exports all data of all projects for the selected month as cdv file and calculates the total number of hours for each project.
The export file can be found in the project folder under ./export.csv

### exit
Ends the tracker

### GUI
If you start the program with 
``shell
java -jar ./build/lib/tracking.jar -g
```
Or`

```shell
java -jar ./build/lib/tracking.jar gui
```

Appears with a gui. 
This is currently still under development.

## Error logging
The errors are output directly to the console in "debug_mode". 
If the "debug_mode" is off, then they are written to a log file. 
You can find the log file under ./log/errors.txt
You can set the "debug_mode" in the application.yml to true or false.

```yaml
debug_file_path=./logs/errors.txt
debug_mode=false
```

## SQL query builder
There is its own little query builder.
With this you can display sql statements in a simplified way. 

An example : 
```sql
INSERT INTO scheduler (project_name, start_time, stop_time) VALUES(?,?,?)
```
```java
new QueryBuilder().insert().table("scheduler")
                    .columns(Arrays.asList("project_name", "start_time", "stop_time"))
                    .values(Arrays.asList("?", "?", "?")).toSql();
```

## Multi lingual
You can add new translations to the folder :
./resources/i18n

Currently the program is completely translated into english and german. 

## Other stuff
### Frameworks and other technologies
* <https://projectlombok.org/>
* MySQL
* Docker
* Apache Commons
* Apache Commons Csv
* Apache Commons Cli
* Jackson Json
