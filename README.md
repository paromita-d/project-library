# project-library
This repo is the back end Spring Boot application that will expose the REST endpoints for Library management.
This exposes endpoints for admin and customer via a Spring boot application, connecting to Postgres DB.
The Front end will be a different application connecting to this BE.

## local setup
- ensure Postgres is installed locally and the table schema have been created
- schema can be created by executing the _db/create_tables.sql_ from this repo
- logs for the application are sent to sysout. If needed we can setup to write to file

## IDE (Intellij) setup
- this is a spring boot application, and Intellij can identify this based on the _pom.xml_

### running test cases
- test cases can be executed from intellij by selecting a single test case or all the test cases
- from CLI _cd_ into _library-be_. Executing _mvn clean test_ will run test cases inside that

### running the application
- from Intellij, select the _Main_ class and its _main_ method and execute it
- from command line, first execute _mvn clean package_ inside _library-be_
    - this will create _library-be-1.0-SNAPSHOT.jar_ inside the _target_ folder
    - execute this by running _java -jar library-be-1.0-SNAPSHOT.jar_

### accessing endpoints
- when running locally, the root context is _http://localhost:8080_
- endpoint documentation is exposed via swagger at _http://localhost:8080/swagger-ui.html_