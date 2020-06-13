# tracking 
![screenshot](https://github.com/schnittker/tracking/blob/master/screenshots/screenshot.png?raw=true)

Tracking is a small multi lingual application to track the time you are working on a project. 

If you have a question, just write to 
schnittker@neozo.de

## How to build
```bash
gradle buildJar
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

## Error logging
The errors are output directly to the console in "debug_mode". 
If the "debug_mode" is off, then they are written to a log file. 
You can find the log file under ./log/errors.txt
You can set the "debug_mode" in the application.yml to true or false.

```yaml
debug_file_path=./logs/errors.txt
debug_mode=false
```

## Multi lingual
You can add new translations to the folder :
./resources/i18n

Currently the program is completely translated into english and german. 

## Other stuff
### Frameworks and other technologies
* MySQL
* Docker
* Apache Commons
* Apache Commons Csv
* jFreechart
