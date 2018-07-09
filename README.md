Demonstrates usage of docker-compose-maven-plugin with postgresql in integration tests. 
The idea is to start docker-compose with postgresql before integration tests and call docker-compose down after.
Usage:

~~~~
mvn clean verify
~~~~

Note:
PostgreSQL may not be able to start in time, make sure to specify loginTimeout in a connection 
