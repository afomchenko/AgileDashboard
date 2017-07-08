### Summary
This project provides an example of simple team tracker based on Apache Felix OSGi framework.
The application uses Grizzly as a web service, ormlite as an ORM.
The front-end based on ExtJS framework.

### Prerequisites
* This project requires Java 8 JDK
* Install maven from https://maven.apache.org/download.cgi

### Build

* Run install.bat from the project directory. The script will automatically download all the necessary dependencies to the Felix folder

### Deploy and run
* Edit agileboard-felix/conf/config.properties file. Set http port and MySql database properties
** The database dump is in the AgileBoardSQLDump.sql file
* Run install.bat from the project directory
* Execute init-all command from gogo console
* Go to http://localhost:<HTTP_PORT>/agileboard/index.html
